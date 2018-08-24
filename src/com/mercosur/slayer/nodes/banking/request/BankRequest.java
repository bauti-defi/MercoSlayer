package com.mercosur.slayer.nodes.banking.request;

import com.mercosur.slayer.models.items.AbstractItem;
import com.mercosur.slayer.models.items.Item;

public abstract class BankRequest {

	private final AbstractItem item;

	private final Urgency urgency;

	private final int amount;

	public BankRequest(final AbstractItem item, final Urgency urgency, final int amount) {
		this.item = item;
		this.urgency = urgency;
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public AbstractItem getItem() {
		return item;
	}

	public Urgency getUrgency() {
		return urgency;
	}
}
