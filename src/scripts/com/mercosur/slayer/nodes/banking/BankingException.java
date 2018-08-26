package scripts.com.mercosur.slayer.nodes.banking;

import scripts.com.mercosur.slayer.nodes.banking.request.BankRequest;
import scripts.com.mercosur.slayer.nodes.banking.request.DepositRequest;

public class BankingException extends RuntimeException {

	public BankingException(final BankRequest request) {
		super("Failed to " + ((request instanceof DepositRequest) ? "deposit " : "withdraw ") + request.getAmount() + " " + request.getItem().getName());
	}

	public BankingException(final String message) {
		super(message);
	}
}
