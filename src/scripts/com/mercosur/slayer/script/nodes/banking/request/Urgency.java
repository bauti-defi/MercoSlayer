package scripts.com.mercosur.slayer.script.nodes.banking.request;

public enum Urgency {

	NOW(0), NEXT_BANK_VISIT(1);

	private int tier;

	Urgency(final int tier) {
		this.tier = tier;
	}

	public int getTier(){
		return tier;
	}
}
