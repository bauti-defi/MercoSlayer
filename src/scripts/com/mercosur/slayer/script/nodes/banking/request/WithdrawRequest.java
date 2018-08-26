package scripts.com.mercosur.slayer.script.nodes.banking.request;

import scripts.com.mercosur.slayer.models.items.AbstractItem;

public class WithdrawRequest extends BankRequest {

	public WithdrawRequest(final AbstractItem item, final Urgency urgency, final int amount) {
		super(item, urgency, amount);
	}
}
