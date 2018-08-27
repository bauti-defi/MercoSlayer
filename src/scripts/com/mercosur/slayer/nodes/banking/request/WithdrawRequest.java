package scripts.com.mercosur.slayer.nodes.banking.request;

import scripts.com.mercosur.slayer.models.items.AbstractItem;

public class WithdrawRequest extends BankRequest {

	public static final int FILL_INVENTORY = -1;

	public WithdrawRequest(final AbstractItem item, final Urgency urgency, final int amount) {
		super(item, urgency, amount);
	}
}
