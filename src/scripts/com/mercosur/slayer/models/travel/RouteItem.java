package scripts.com.mercosur.slayer.models.travel;

import scripts.com.mercosur.slayer.models.items.AbstractItem;

public class RouteItem extends AbstractItem {

	public static final RouteItem AMULET_OF_GLORY = new RouteItem("Amulet of Glory", true, false, 1);
	public static final RouteItem COINS = new RouteItem("Coins", false, true, 2000);
	public static final RouteItem ECTOPHIAL = new RouteItem("Ectophial", false, false, 1);
	public static final RouteItem HOUSE_TELEPORT_TAB = new RouteItem("Teleport to House", false, true, 4); //Any house teleport action with this
	public static final RouteItem VARROCK_TELEPORT_TAB = new RouteItem("Teleport to Varrock", false, true, 4);

	private final int requiredAmount;

	public RouteItem(final String name, final boolean mutatableName, final boolean stackable, final int requiredAmount) {
		super(name, mutatableName, stackable);
		this.requiredAmount = requiredAmount;
	}

	public int getRequiredAmount() {
		return requiredAmount;
	}
}
