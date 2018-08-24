package scripts.com.mercosur.dax_api.teleport_logic;

import org.tribot.api.General;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.com.mercosur.dax_api.WebPath;
import scripts.com.mercosur.dax_api.api_lib.WebWalkerServerApi;
import scripts.com.mercosur.dax_api.api_lib.models.PathResult;
import scripts.com.mercosur.dax_api.api_lib.models.PathStatus;
import scripts.com.mercosur.dax_api.api_lib.models.PlayerDetails;
import scripts.com.mercosur.dax_api.api_lib.models.Point3D;
import scripts.com.mercosur.dax_api.walker_engine.Loggable;
import scripts.com.mercosur.dax_api.walker_engine.WaitFor;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


public class TeleportManager implements Loggable {

    public static TeleportAction previousAction;

    private int offset;
    private HashSet<TeleportMethod> blacklistTeleportMethods;
    private HashSet<TeleportLocation> blacklistTeleportLocations;
    private ExecutorService executorService;

    private static TeleportManager teleportManager;

    private static TeleportManager getInstance() {
        return teleportManager != null ? teleportManager : (teleportManager = new TeleportManager());
    }

    public TeleportManager() {
        offset = 25;
        blacklistTeleportMethods = new HashSet<>();
        blacklistTeleportLocations = new HashSet<>();
        executorService = Executors.newFixedThreadPool(15);
        ;

    }

    /**
     * Blacklist teleport methods. This method is NOT threadsafe.
     *
     * @param teleportMethods
     */
    public static void ignoreTeleportMethods(TeleportMethod... teleportMethods) {
        getInstance().blacklistTeleportMethods.addAll(Arrays.asList(teleportMethods));
    }

    public static void ignoreTeleportLocations(TeleportLocation... teleportLocations) {
        getInstance().blacklistTeleportLocations.addAll(Arrays.asList(teleportLocations));
    }

    public static void clearTeleportMethodBlackList() {
        getInstance().blacklistTeleportMethods.clear();
    }

    public static void clearTeleportLocationBlackList() {
        getInstance().blacklistTeleportLocations.clear();
    }

    /**
     * Sets the threshold of tile difference that will trigger teleport action.
     *
     * @param offset distance in tiles
     */
    public static void setOffset(int offset) {
        getInstance().offset = offset;
    }

    public static ArrayList<RSTile> teleportBankPath(int originalMoveCost) {
        List<TeleportWrapper> teleport = new CopyOnWriteArrayList<>();

        Arrays.stream(TeleportMethod.values()).parallel().forEach(teleportMethod -> {
            if (!teleportMethod.canUse()) {
                return;
            }
            for (TeleportLocation teleportLocation : teleportMethod.getDestinations()) {
                PathResult pathResult = WebWalkerServerApi.getInstance().getBankPath(
                        Point3D.fromPositionable(teleportLocation.getRSTile()),
                        null,
                        PlayerDetails.generate()
                );
                teleport.add(new TeleportWrapper(pathResult, teleportMethod, teleportLocation));
            }
        });


        TeleportWrapper closest = teleport.stream()
                .filter(pathResult -> pathResult.getPathResult().getPathStatus() == PathStatus.SUCCESS && pathResult.getPathResult().getCost() < originalMoveCost - 25)
                .min(Comparator.comparingInt(value -> value.getPathResult().getCost()))
                .orElse(null);

        if (closest != null && !closest.getTeleportMethod().use(closest.getTeleportLocation())) {
            getInstance().log("Failed to teleport");
        }

        return closest != null ? closest.getPathResult().toRSTilePath() : null;
    }

    public static ArrayList<RSTile> teleportPath(int originalMoveCost, RSTile destination) {
        List<TeleportWrapper> teleport = new CopyOnWriteArrayList<>();

        Arrays.stream(TeleportMethod.values()).parallel().forEach(teleportMethod -> {
            if (!teleportMethod.canUse()) {
                return;
            }
            for (TeleportLocation teleportLocation : teleportMethod.getDestinations()) {
                PathResult pathResult = WebWalkerServerApi.getInstance().getPath(
                        Point3D.fromPositionable(teleportLocation.getRSTile()),
                        Point3D.fromPositionable(destination),
                        PlayerDetails.generate()
                );
                teleport.add(new TeleportWrapper(pathResult, teleportMethod, teleportLocation));
            }
        });


        TeleportWrapper closest = teleport.stream()
                .filter(pathResult -> pathResult.getPathResult().getPathStatus() == PathStatus.SUCCESS && pathResult.getPathResult().getCost() < originalMoveCost - 25)
                .min(Comparator.comparingInt(value -> value.getPathResult().getCost()))
                .orElse(null);

        if (closest != null && !closest.getTeleportMethod().use(closest.getTeleportLocation())) {
            getInstance().log("Failed to teleport");
        }

        return closest != null ? closest.getPathResult().toRSTilePath() : null;
    }


    public static ArrayList<RSTile> teleport(int originalPathLength, RSTile destination) {
        if (originalPathLength < getInstance().offset) {
            return null;
        }

        TeleportAction teleportAction = Arrays.stream(TeleportMethod.values())
                .filter(TeleportMethod::canUse)
                .filter(teleportMethod -> !getInstance().blacklistTeleportMethods.contains(teleportMethod))
                .map(teleportMethod -> Arrays.stream(teleportMethod.getDestinations())
                        .filter(teleportLocation -> !getInstance().blacklistTeleportLocations.contains(teleportLocation)) //map to destinations
                        .map(teleportLocation -> getInstance().executorService.submit(new PathComputer(teleportMethod, teleportLocation, destination)))) //map to future
                .flatMap(futureStream -> futureStream).collect(Collectors.toList()).stream().map(teleportActionFuture -> { //flatten out futures
                    try {
                        return teleportActionFuture.get();
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                        return null;
                    }
                }).filter(teleportAction1 -> teleportAction1 != null && teleportAction1.path.size() > 0).min(Comparator.comparingInt(o -> o.path.size())).orElse(null);

        if (teleportAction == null || teleportAction.path.size() >= originalPathLength || teleportAction.path.size() == 0) {
            return null;
        }

        previousAction = teleportAction;

        if (originalPathLength - teleportAction.path.size() < getInstance().offset) {
            getInstance().log("No efficient teleports!");
            return null;
        }

        getInstance().log("We will be using " + teleportAction.teleportMethod + " to " + teleportAction.teleportLocation);
        if (!teleportAction.teleportMethod.use(teleportAction.teleportLocation)) {
            getInstance().log("Failed to teleport");
        }
        WaitFor.condition(General.random(3000, 54000), () -> teleportAction.teleportLocation.getRSTile().distanceTo(Player.getPosition()) < 10 ? WaitFor.Return.SUCCESS : WaitFor.Return.IGNORE);
        return teleportAction.path;

    }

    private static class PathComputer implements Callable<TeleportAction> {

        private TeleportMethod teleportMethod;
        private TeleportLocation teleportLocation;
        private RSTile destination;

        private PathComputer(TeleportMethod teleportMethod, TeleportLocation teleportLocation, RSTile destination) {
            this.teleportMethod = teleportMethod;
            this.teleportLocation = teleportLocation;
            this.destination = destination;
        }

        @Override
        public TeleportAction call() throws Exception {
            getInstance().log("Checking path... [" + teleportMethod + "] -> [" + teleportLocation + "]");
            return new TeleportAction(WebPath.getPath(teleportLocation.getRSTile(), destination), teleportMethod, teleportLocation);
        }

    }

    public static class TeleportWrapper {
        private PathResult pathResult;
        private TeleportMethod teleportMethod;
        private TeleportLocation teleportLocation;

        public TeleportWrapper(PathResult pathResult, TeleportMethod teleportMethod, TeleportLocation teleportLocation) {
            this.pathResult = pathResult;
            this.teleportMethod = teleportMethod;
            this.teleportLocation = teleportLocation;
        }

        public PathResult getPathResult() {
            return pathResult;
        }

        public TeleportMethod getTeleportMethod() {
            return teleportMethod;
        }

        public TeleportLocation getTeleportLocation() {
            return teleportLocation;
        }
    }

    public static class TeleportAction {
        private ArrayList<RSTile> path;
        private TeleportMethod teleportMethod;
        private TeleportLocation teleportLocation;

        TeleportAction(ArrayList<RSTile> path, TeleportMethod teleportMethod, TeleportLocation teleportLocation) {
            this.path = path;
            this.teleportMethod = teleportMethod;
            this.teleportLocation = teleportLocation;
        }

        public ArrayList<RSTile> getPath() {
            return path;
        }

        public TeleportMethod getTeleportMethod() {
            return teleportMethod;
        }

        public TeleportLocation getTeleportLocation() {
            return teleportLocation;
        }
    }

    @Override
    public String getName() {
        return "Teleport Manager";
    }
}
