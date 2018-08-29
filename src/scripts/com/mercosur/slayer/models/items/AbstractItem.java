package scripts.com.mercosur.slayer.models.items;

public abstract class AbstractItem {

	private final String name;

	private final boolean mutatableName;

	private final boolean stackable;

	public AbstractItem(final String name, final boolean mutatableName, final boolean stackable) {
		this.name = name;
		this.mutatableName = mutatableName;
		this.stackable = stackable;
	}

	public String getName() {
		return name;
	}

	public boolean isStackable() {
		return stackable;
	}

	public boolean isMutatableName() {
		return mutatableName;
	}

	@Override
	public String toString() {
		return name + ":" + (mutatableName ? "Mutatable" : "Non-Mutatable") + ":" + (stackable ? "Stackable" : "Non-Stackable");
	}
}
