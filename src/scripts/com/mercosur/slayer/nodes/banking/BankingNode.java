package scripts.com.mercosur.slayer.nodes.banking;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import scripts.com.mercosur.dax_api.api_lib.DaxWalker;
import scripts.com.mercosur.framework.Node;
import scripts.com.mercosur.framework.NodePriority;
import scripts.com.mercosur.slayer.data.RunTimeVariables;
import scripts.com.mercosur.slayer.models.items.AbstractItem;
import scripts.com.mercosur.slayer.nodes.banking.request.BankRequest;
import scripts.com.mercosur.slayer.nodes.banking.request.DepositRequest;
import scripts.com.mercosur.slayer.nodes.banking.request.Urgency;
import scripts.com.mercosur.slayer.nodes.banking.request.WithdrawRequest;
import scripts.com.mercosur.slayer.util.Sleep;

import java.util.Stack;

public class BankingNode extends Node {

	private final static Stack<BankRequest> requests = new Stack<>();

	public BankingNode() {
		super(NodePriority.VERY_HIGH);
		insertGlobalPassiveRequest();
	}

	@Override
	public boolean condition() {
		return Banking.isBankScreenOpen() || requiresCriticalBanking() || requiresPassiveBanking() && Banking.isInBank();
	}

	@Override
	public Node.Response execute() {
		if (Banking.isInBank()) {
			if (Banking.isBankScreenOpen() || Banking.openBankBanker()) { //Bank is open
				if (shouldDepositAll()) {
					if (Banking.depositAll() == 0 && !requests.removeIf(request -> request instanceof DepositRequest)) {
						throw new BankingException("Failed to deposit all.");
					}
					return Response.CONTINUE;
				}
				sortRequest();
				BankRequest request;
				while ((request = requests.pop()) != null) { //execute banking requests
					final boolean successfulExecution;
					if (request instanceof DepositRequest) {
						if (Inventory.getCount(request.getItem().getName()) == 0) {
							continue;
						}
						successfulExecution = Banking.deposit(request.getAmount(), request.getItem().getName());
					} else {
						successfulExecution = Banking.withdraw(request.getAmount(), request.getItem().getName());
					}
					if (!Sleep.conditionalSleep(new Condition() {
						@Override
						public boolean active() {
							return successfulExecution;//break if successfull
						}
					}, 1500, 2000)) {
						throw new BankingException(request);
					}
				}
				insertGlobalPassiveRequest();
			}
		} else {
			General.println("Walking to bank...");
			DaxWalker.walkToBank();
		}
		return Response.CONTINUE;
	}

	private void insertGlobalPassiveRequest() {
		requestPassiveItemWithdraw(RunTimeVariables.SCRIPT_SETTINGS.getFood(), BankRequest.ALL);
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
		upsertWithdrawRequest(item, Urgency.NOW, amount);
	}

	public static void requestPassiveItemWithdraw(AbstractItem item, int amount) {
		upsertWithdrawRequest(item, Urgency.NEXT_BANK_VISIT, amount);
	}

	private static void upsertWithdrawRequest(AbstractItem item, Urgency urgency, int amount) {
		requests.removeIf(request -> request instanceof WithdrawRequest && request.getItem().getName().equalsIgnoreCase(item.getName()));
		requests.push(new WithdrawRequest(item, urgency, amount));
	}

	public static void requestCriticalItemDeposit(AbstractItem item, int amount) {
		upsertDepositRequest(item, Urgency.NOW, amount);
	}

	public static void requestPassiveItemDeposit(AbstractItem item, int amount) {
		upsertDepositRequest(item, Urgency.NEXT_BANK_VISIT, amount);
	}

	private static void upsertDepositRequest(AbstractItem item, Urgency urgency, int amount) {
		requests.removeIf(request -> request instanceof DepositRequest && request.getItem().getName().equalsIgnoreCase(item.getName()));
		requests.push(new DepositRequest(item, urgency, amount));
	}

	private boolean requiresCriticalBanking() {
		return requests.stream().anyMatch(request -> request.getUrgency() == Urgency.NOW);
	}

	private boolean requiresPassiveBanking() {
		return !requests.isEmpty();
	}

	/*
	Sorting order is:
		deposit > withdraw
				|
			 urgency
				|
			  amount
			|        |
		 bigger   smaller
	 */
	private void sortRequest() {
		requests.sort((request1, request2) -> {
			if (request1 instanceof DepositRequest) {
				if (request2 instanceof DepositRequest) {
					if (request1.getUrgency().getTier() > request2.getUrgency().getTier()) {
						return 1;
					} else if (request1.getUrgency().getTier() < request2.getUrgency().getTier()) {
						return -1;
					} else if (request1.getAmount() == -1) {
						return 1;
					} else if (request2.getAmount() == -1) {
						return -1;
					}
					return (request1.getAmount() - request2.getAmount()) >= 0 ? 1 : -1;
				}
				return 1;
			} else if (request2 instanceof DepositRequest) {
				return -1;
			} else if (request1.getUrgency().getTier() > request2.getUrgency().getTier()) {
				return 1;
			} else if (request1.getUrgency().getTier() < request2.getUrgency().getTier()) {
				return -1;
			} else if (request1.getAmount() == -1) {
				return -1;
			} else if (request2.getAmount() == -1) {
				return 1;
			}
			return (request1.getAmount() - request2.getAmount()) <= 0 ? 1 : -1;
		});
	}
}
