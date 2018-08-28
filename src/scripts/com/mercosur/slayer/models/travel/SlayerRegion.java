package scripts.com.mercosur.slayer.models.travel;

import scripts.com.mercosur.slayer.util.Constants;

public enum SlayerRegion {

	CANIFIS(new Route(Constants.ECTOPHIAL)), DESERT, RELLEKKA_DUNGEON, LUMBRIDGE_SWAMP, GNOME_STRONGHOLD;

	private final Route[] routeOptions;

	SlayerRegion(final Route... routeOptions) {
		this.routeOptions = routeOptions;
	}

	public Route[] getRouteOptions() {
		return routeOptions;
	}
}
