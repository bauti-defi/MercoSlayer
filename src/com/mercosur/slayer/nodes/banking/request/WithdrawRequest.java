package com.mercosur.slayer.nodes.banking.request;

import com.mercosur.slayer.models.items.AbstractItem;
import com.mercosur.slayer.models.items.Item;

public class WithdrawRequest extends BankRequest {

	public WithdrawRequest(final AbstractItem item, final Urgency urgency, final int amount) {
		super(item, urgency, amount);
	}
}
