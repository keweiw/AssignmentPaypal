package refectored;

public enum UserPrompt {
	AMOUNT("Please enter the amount you would like to borrow: "),
	APR("Please enter the annual percentage rate used to repay the loan: "),
	TERM_YEARS("Please enter the term, in years, over which the loan is repaid: ");
	
	private String prompt;
	
	private UserPrompt(String prompt) {
		this.prompt = prompt;
	}
	
	/**
	 * 
	 * @return
	 */
	public String message() {
		return this.prompt;
	}
}
