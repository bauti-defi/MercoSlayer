package com.mercosur.slayer.nodes.banking.request;

import com.mercosur.slayer.models.items.AbstractItem;
import com.mercosur.slayer.models.items.Item;

public class DepositRequest extends BankRequest {

	public DepositRequest(final AbstractItem item, final Urgency urgency, final int amount) {
		super(item, urgency,amount);
	}
}
