package com.mercosur.slayer.nodes.banking;

import com.mercosur.slayer.nodes.banking.request.BankRequest;
import com.mercosur.slayer.nodes.banking.request.DepositRequest;

public class BankingException extends RuntimeException {

	public BankingException(final BankRequest request) {
		super("Failed to " + ((request instanceof DepositRequest) ? "deposit " : "withdraw ") + request.getAmount() + " " + request.getItem().getName());
	}

	public BankingException(final String message) {
		super(message);
	}
}
