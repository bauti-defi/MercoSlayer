package scripts.com.mercosur.slayer.models.travel;

public class Route {

	private final RouteItem[] requiredItems;

	public Route(final RouteItem... requiredItems) {
		this.requiredItems = requiredItems;
	}

	public RouteItem[] getRequiredItems() {
		return requiredItems;
	}
}
