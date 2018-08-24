package scripts.com.mercosur.slayer.models.items;

public abstract class AbstractItem {

	private String name;

	private boolean stackable;

	public AbstractItem(final String name, final boolean stackable) {
		this.name = name;
		this.stackable = stackable;
	}

	public String getName() {
		return name;
	}

	public boolean isStackable() {
		return stackable;
	}
}
