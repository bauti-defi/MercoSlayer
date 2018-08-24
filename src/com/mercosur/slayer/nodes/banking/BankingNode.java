package com.mercosur.slayer.nodes.banking;

import com.mercosur.dax_api.api_lib.DaxWalker;
import com.mercosur.framework.Node;
import com.mercosur.framework.NodePriority;
import com.mercosur.slayer.models.items.AbstractItem;
import com.mercosur.slayer.models.items.Item;
import com.mercosur.slayer.nodes.banking.request.BankRequest;
import com.mercosur.slayer.nodes.banking.request.DepositRequest;
import com.mercosur.slayer.nodes.banking.request.Urgency;
import com.mercosur.slayer.nodes.banking.request.WithdrawRequest;
import com.mercosur.slayer.util.Sleep;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;

import java.util.Stack;

public class BankingNode extends Node {

	private final static Stack<BankRequest> requests = new Stack<>();

	public BankingNode() {
		super(NodePriority.VERY_HIGH);
	}

	@Override
	public boolean condition() {
		return requiresCriticalBanking() || requiresPassiveBanking();
	}

	@Override
	public int execute() {
		if (Banking.isInBank()) {
			if (Banking.isBankScreenOpen() || Banking.openBankBanker()) { //Bank is open
				if (shouldDepositAll()) {
					if (Banking.depositAll() > 0 && requests.removeIf(request -> request instanceof DepositRequest)) {
						throw new BankingException("Failed to deposit all.");
					}
					return 300;
				}
				BankRequest request;
				while ((request = requests.pop()) != null) { //execute banking requests
					final boolean sucessfullExecution;
					if (request instanceof DepositRequest) {
						sucessfullExecution = Banking.deposit(request.getAmount(), request.getItem().getName());
					} else {
						sucessfullExecution = Banking.withdraw(request.getAmount(), request.getItem().getName());
					}
					if (!Sleep.conditionalSleep(new Condition() {
						@Override
						public boolean active() {
							return sucessfullExecution;//break if successfull
						}
					}, 1500, 2000)) {
						throw new BankingException(request);
					}
				}
			}
		} else {
			DaxWalker.walkToBank();
		}
		return 100;
	}

	private boolean shouldDepositAll() {
		String[] items = (String[]) requests.stream().filter(request -> request instanceof DepositRequest)
				.map(request -> request.getItem().getName())
				.toArray();
		final int inventoryCount = Inventory.find(items).length;
		return requests.stream().map(request -> {
			if (request.getItem().isStackable()) {
				return 1;
			}
			return request.getAmount();
		}).mapToInt(Integer::intValue).sum() == inventoryCount && inventoryCount > 0;
	}

	public static void requestCriticalItemWithdraw(AbstractItem item, int amount) {
		saveWithdrawRequest(item, Urgency.NOW, amount);
	}

	public static void requestPassiveItemWithdraw(AbstractItem item, int amount) {
		saveWithdrawRequest(item, Urgency.NEXT_BANK_VISIT, amount);
	}

	private static void saveWithdrawRequest(AbstractItem item, Urgency urgency, int amount) {
		requests.push(new WithdrawRequest(item, urgency, amount));
		sortRequest();
	}

	public static void requestCriticalItemDeposit(AbstractItem item, int amount) {
		saveDepositRequest(item, Urgency.NOW, amount);
	}

	public static void requestPassiveItemDeposit(AbstractItem item, int amount) {
		requests.push(new DepositRequest(item, Urgency.NEXT_BANK_VISIT, amount));
	}

	private static void saveDepositRequest(AbstractItem item, Urgency urgency, int amount) {
		requests.push(new DepositRequest(item, urgency, amount));
		sortRequest();
	}

	private boolean requiresCriticalBanking() {
		return requests.stream().anyMatch(request -> request.getUrgency() == Urgency.NOW);
	}

	private boolean requiresPassiveBanking() {
		return !requests.isEmpty();
	}

	private static void sortRequest() {
		requests.sort((request1, request2) -> {
			if (request1 instanceof DepositRequest) {
				return 1;
			} else if (request2 instanceof DepositRequest) {
				return -1;
			} else if (request1.getUrgency().getTier() > request2.getUrgency().getTier()) {
				return 1;
			} else if (request1.getUrgency().getTier() < request2.getUrgency().getTier()) {
				return -1;
			}
			return 0;
		});
	}
}
