package scripts.com.mercosur.slayer.nodes.banking.request;

import scripts.com.mercosur.slayer.models.items.AbstractItem;

public class DepositRequest extends BankRequest {

	public DepositRequest(final AbstractItem item, final Urgency urgency, final int amount) {
		super(item, urgency,amount);
	}
}
