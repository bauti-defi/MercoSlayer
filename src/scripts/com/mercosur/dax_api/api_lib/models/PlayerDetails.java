package scripts.com.mercosur.dax_api.api_lib.models;

import org.tribot.api2007.*;
import scripts.com.mercosur.dax_api.api_lib.json.JsonObject;
import scripts.com.mercosur.dax_api.shared.helpers.WorldHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerDetails {

    public static PlayerDetails generate() {

        List<IntPair> inventory = Arrays.stream(Inventory.getAll())
                .map(rsItem -> new IntPair(rsItem.getID(), rsItem.getStack())).collect(Collectors.toList());

        List<IntPair> equipment = Arrays.stream(Equipment.getItems())
                .map(rsItem -> new IntPair(rsItem.getID(), rsItem.getStack())).collect(Collectors.toList());

        Set<IntPair> settings = Arrays.stream(Game.getSettingsArray())
                .mapToObj(value -> new IntPair(value, Game.getSetting(value))).collect(Collectors.toSet());


        Set<IntPair> varbit = Arrays.stream(new int[]{5087, 5088, 5089, 5090})
                .mapToObj(value -> new IntPair(value, Game.getSetting(value))).collect(Collectors.toSet());

        return new PlayerDetails(
                Skills.getActualLevel(Skills.SKILLS.ATTACK),
                Skills.getActualLevel(Skills.SKILLS.DEFENCE),
                Skills.getActualLevel(Skills.SKILLS.STRENGTH),
                Skills.getActualLevel(Skills.SKILLS.HITPOINTS),
                Skills.getActualLevel(Skills.SKILLS.RANGED),
                Skills.getActualLevel(Skills.SKILLS.PRAYER),
                Skills.getActualLevel(Skills.SKILLS.MAGIC),
                Skills.getActualLevel(Skills.SKILLS.COOKING),
                Skills.getActualLevel(Skills.SKILLS.WOODCUTTING),
                Skills.getActualLevel(Skills.SKILLS.FLETCHING),
                Skills.getActualLevel(Skills.SKILLS.FISHING),
                Skills.getActualLevel(Skills.SKILLS.FIREMAKING),
                Skills.getActualLevel(Skills.SKILLS.CRAFTING),
                Skills.getActualLevel(Skills.SKILLS.SMITHING),
                Skills.getActualLevel(Skills.SKILLS.MINING),
                Skills.getActualLevel(Skills.SKILLS.HERBLORE),
                Skills.getActualLevel(Skills.SKILLS.AGILITY),
                Skills.getActualLevel(Skills.SKILLS.THIEVING),
                Skills.getActualLevel(Skills.SKILLS.SLAYER),
                Skills.getActualLevel(Skills.SKILLS.FARMING),
                Skills.getActualLevel(Skills.SKILLS.RUNECRAFTING),
                Skills.getActualLevel(Skills.SKILLS.HUNTER),
                Skills.getActualLevel(Skills.SKILLS.CONSTRUCTION),
                settings,
                varbit,
                WorldHelper.isMember(WorldHopper.getWorld()),
                equipment,
                inventory
        );
    }

    private int attack;
    private int defence;
    private int strength;
    private int hitpoints;
    private int ranged;
    private int prayer;
    private int magic;
    private int cooking;
    private int woodcutting;
    private int fletching;
    private int fishing;
    private int firemaking;
    private int crafting;
    private int smithing;
    private int mining;
    private int herblore;
    private int agility;
    private int thieving;
    private int slayer;
    private int farming;
    private int runecrafting;
    private int hunter;
    private int construction;
    private Set<IntPair> setting;
    private Set<IntPair> varbit;
    private boolean member;
    private List<IntPair> equipment;
    private List<IntPair> inventory;

    public PlayerDetails(int attack, int defence, int strength, int hitpoints, int ranged, int prayer, int magic, int cooking, int woodcutting, int fletching, int fishing, int firemaking, int crafting, int smithing, int mining, int herblore, int agility, int thieving, int slayer, int farming, int runecrafting, int hunter, int construction, Set<IntPair> setting, Set<IntPair> varbit, boolean member, List<IntPair> equipment, List<IntPair> inventory) {
        this.attack = attack;
        this.defence = defence;
        this.strength = strength;
        this.hitpoints = hitpoints;
        this.ranged = ranged;
        this.prayer = prayer;
        this.magic = magic;
        this.cooking = cooking;
        this.woodcutting = woodcutting;
        this.fletching = fletching;
        this.fishing = fishing;
        this.firemaking = firemaking;
        this.crafting = crafting;
        this.smithing = smithing;
        this.mining = mining;
        this.herblore = herblore;
        this.agility = agility;
        this.thieving = thieving;
        this.slayer = slayer;
        this.farming = farming;
        this.runecrafting = runecrafting;
        this.hunter = hunter;
        this.construction = construction;
        this.setting = setting;
        this.varbit = varbit;
        this.member = member;
        this.equipment = equipment;
        this.inventory = inventory;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public int getStrength() {
        return strength;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public int getRanged() {
        return ranged;
    }

    public int getPrayer() {
        return prayer;
    }

    public int getMagic() {
        return magic;
    }

    public int getCooking() {
        return cooking;
    }

    public int getWoodcutting() {
        return woodcutting;
    }

    public int getFletching() {
        return fletching;
    }

    public int getFishing() {
        return fishing;
    }

    public int getFiremaking() {
        return firemaking;
    }

    public int getCrafting() {
        return crafting;
    }

    public int getSmithing() {
        return smithing;
    }

    public int getMining() {
        return mining;
    }

    public int getHerblore() {
        return herblore;
    }

    public int getAgility() {
        return agility;
    }

    public int getThieving() {
        return thieving;
    }

    public int getSlayer() {
        return slayer;
    }

    public int getFarming() {
        return farming;
    }

    public int getRunecrafting() {
        return runecrafting;
    }

    public int getHunter() {
        return hunter;
    }

    public int getConstruction() {
        return construction;
    }

    public Set<IntPair> getSetting() {
        return setting;
    }

    public Set<IntPair> getVarbit() {
        return varbit;
    }

    public boolean isMember() {
        return member;
    }

    public List<IntPair> getEquipment() {
        return equipment;
    }

    public List<IntPair> getInventory() {
        return inventory;
    }

    public JsonObject toJson() {
        return new JsonObject()
                .add("attack", attack)
                .add("defence", defence)
                .add("strength", strength)
                .add("hitpoints", hitpoints)
                .add("ranged", ranged)
                .add("prayer", prayer)
                .add("magic", magic)
                .add("cooking", cooking)
                .add("woodcutting", woodcutting)
                .add("fletching", fletching)
                .add("fishing", fishing)
                .add("firemaking", firemaking)
                .add("crafting", crafting)
                .add("smithing", smithing)
                .add("mining", mining)
                .add("herblore", herblore)
                .add("agility", agility)
                .add("thieving", thieving)
                .add("slayer", slayer)
                .add("farming", farming)
                .add("runecrafting", runecrafting)
                .add("hunter", hunter)
                .add("construction", construction);
    }

}
