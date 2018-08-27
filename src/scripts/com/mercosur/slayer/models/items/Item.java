package scripts.com.mercosur.slayer.models.items;

import java.util.stream.Stream;

public class Item extends AbstractItem {

	private ItemProperty[] properties;

	private boolean canEquip;

	public Item(final String name, final boolean stackable, final boolean canEquip, final ItemProperty... properties) {
		super(name, stackable);
		this.canEquip = canEquip;
		this.properties = properties;
	}

	public boolean isCanEquip() {
		return canEquip;
	}

	public boolean hasProperty(ItemProperty property) {
		return Stream.of(properties).anyMatch(prop -> prop == property);
	}

	public ItemProperty[] getProperties() {
		return properties;
	}

}
