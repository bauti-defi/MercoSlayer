package scripts.com.mercosur.dax_api.api_lib;

import org.tribot.api.interfaces.Positionable;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSTile;
import scripts.com.mercosur.dax_api.api_lib.models.*;
import scripts.com.mercosur.dax_api.teleport_logic.TeleportManager;
import scripts.com.mercosur.dax_api.walker_engine.WalkerEngine;
import scripts.com.mercosur.dax_api.walker_engine.WalkingCondition;

import java.util.ArrayList;

public class DaxWalker {

    private static DaxWalker daxWalker;
    private static DaxWalker getInstance() {
        return daxWalker != null ? daxWalker : (daxWalker = new DaxWalker());
    }

    private WalkingCondition globalWalkingCondition;

    private DaxWalker() {
        globalWalkingCondition = () -> WalkingCondition.State.CONTINUE_WALKER;
    }

    public static WalkingCondition getGlobalWalkingCondition() {
        return getInstance().globalWalkingCondition;
    }

    public static void setGlobalWalkingCondition(WalkingCondition walkingCondition) {
        getInstance().globalWalkingCondition = walkingCondition;
    }

    public static void setCredentials(DaxCredentialsProvider daxCredentialsProvider) {
        WebWalkerServerApi.getInstance().setDaxCredentialsProvider(daxCredentialsProvider);
    }

    public static boolean walkTo(Positionable positionable) {
        return walkTo(positionable, null);
    }

    public static boolean walkTo(Positionable destination, WalkingCondition walkingCondition) {
        Positionable start = Player.getPosition();
        if (start.equals(destination)) {
            return true;
        }

        PathResult pathResult = WebWalkerServerApi.getInstance().getPath(Point3D.fromPositionable(start), Point3D.fromPositionable(destination), PlayerDetails.generate());

	    ArrayList<RSTile> path = TeleportManager.teleportPath(pathResult.getPathStatus() == PathStatus.SUCCESS ? pathResult.getCost() : Integer.MAX_VALUE, destination.getPosition());
        if (path != null) {
            return WalkerEngine.getInstance().walkPath(path, walkingCondition);
        }

        return WalkerEngine.getInstance().walkPath(pathResult.toRSTilePath(), walkingCondition);
    }

    public static boolean walkToBank() {
        return walkToBank(null);
    }

    public static boolean walkToBank(Bank bank) {
        return walkToBank(bank, null);
    }

    public static boolean walkToBank(Bank bank, WalkingCondition walkingCondition) {
        PathResult pathResult = WebWalkerServerApi.getInstance().getBankPath(Point3D.fromPositionable(Player.getPosition()), bank, PlayerDetails.generate());

	    ArrayList<RSTile> path = TeleportManager.teleportBankPath(pathResult.getPathStatus() == PathStatus.SUCCESS ? pathResult.getCost() : Integer.MAX_VALUE);
        if (path != null) {
            return WalkerEngine.getInstance().walkPath(path, walkingCondition);
        }

        return WalkerEngine.getInstance().walkPath(pathResult.toRSTilePath(), walkingCondition);
    }

}
