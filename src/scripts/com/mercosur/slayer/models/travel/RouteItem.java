package scripts.com.mercosur.slayer.models.travel;

import scripts.com.mercosur.slayer.models.items.AbstractItem;

public class RouteItem extends AbstractItem {

	private final int requiredAmount;

	public RouteItem(final String name, final boolean mutatableName, final boolean stackable, final int requiredAmount) {
		super(name, mutatableName, stackable);
		this.requiredAmount = requiredAmount;
	}

	public int getRequiredAmount() {
		return requiredAmount;
	}
}
