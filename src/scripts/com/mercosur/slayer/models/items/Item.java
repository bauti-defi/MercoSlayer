package scripts.com.mercosur.slayer.models.items;

import java.util.stream.Stream;

public class Item extends AbstractItem {

	private ItemProperty[] properties;

	public Item(final String name, final boolean stackable, final ItemProperty... properties) {
		super(name, stackable);
		this.properties = properties;
	}

	public boolean hasProperty(ItemProperty property) {
		return Stream.of(properties).anyMatch(prop -> prop == property);
	}

	public ItemProperty[] getProperties() {
		return properties;
	}

}
