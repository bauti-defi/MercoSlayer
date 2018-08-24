package com.mercosur.slayer.models.items.consumable;

import com.mercosur.slayer.models.items.Item;
import com.mercosur.slayer.models.items.ItemProperty;

public class Potion extends Item {

	private final Type type;

	enum Type {
		BUFF, RESTORATION
	}

	public Potion(final String name, Type type, final ItemProperty... properties) {
		super(name, false, properties);
		this.type = type;
	}

	public Type getType() {
		return type;
	}
}
