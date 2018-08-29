package scripts.com.mercosur.slayer.models.travel;

public enum SlayerRegion {

	CANIFIS(new Route(RouteItem.ECTOPHIAL)),
	/*
	1- Mounted Glory in house
	2- Edge telly
	 */
	EDGEVILLE_DUNGEON(new Route(RouteItem.HOUSE_TELEPORT_TAB), new Route(RouteItem.AMULET_OF_GLORY), new Route(RouteItem.VARROCK_TELEPORT_TAB)),
	DESERT,
	RELLEKKA_DUNGEON,
	LUMBRIDGE_SWAMP,
	GNOME_STRONGHOLD;

	private final Route[] routeOptions;

	SlayerRegion(final Route... routeOptions) {
		this.routeOptions = routeOptions;
	}

	public Route[] getRouteOptions() {
		return routeOptions;
	}
}
