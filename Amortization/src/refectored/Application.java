package refectored;

import java.io.IOException;

/**
 * Main Class to start the application
 * @author kewewang
 *
 */
public class Application {
	private static final UserPrompt[] USER_PROMPTS = {
		UserPrompt.AMOUNT, UserPrompt.APR, UserPrompt.TERM_YEARS
	};
	
	private static final IOHandler MESSAGEHANDLER = ConsoleHandler.getInstance();
	// amount input by user
	private double amount;
	// apr input by user
	private double apr;
	// years input by user
	private int years;
	
	/**
	 * handle input from user
	 */
	private void InputHandler() {
		
		for (int i = 0; i < USER_PROMPTS.length;) {
			UserPrompt userPrompt = USER_PROMPTS[i];
			String line = "";
			try {
				line = MESSAGEHANDLER.readLine(userPrompt.message());
			} catch (IOException e) {
				MESSAGEHANDLER.print("An IOException was encountered. Terminating program.\n");
				return;
			}
			boolean isValidValue = true;
			try {
				switch (userPrompt) {
				case AMOUNT:
					amount = Double.parseDouble(line);
					if (AmortizationSchedule.isValidBorrowAmount(amount) == false)
					{
						isValidValue = false;
						MESSAGEHANDLER.print("Please enter a positive value between "
								+ AmortizationSchedule.BORROW_AMOUNT_RANGE[0] + " and " + AmortizationSchedule.BORROW_AMOUNT_RANGE[1] + ". ");

					}
					break;
				case APR:
					apr = Double.parseDouble(line);
					if (AmortizationSchedule.isValidAPRValue(apr) == false)
					{
						isValidValue = false;
						MESSAGEHANDLER.print("Please enter a positive value between "
								+ AmortizationSchedule.APR_RANGE[0] + " and " + AmortizationSchedule.APR_RANGE[1] + ". ");

					}
					break;
				case TERM_YEARS:
					years = Integer.parseInt(line);
					if (AmortizationSchedule.isValidTerm(years) == false)
					{
						isValidValue = false;
						MESSAGEHANDLER.print("Please enter a positive integer value between "
								+ AmortizationSchedule.TERM_RANGE[0] + " and " + AmortizationSchedule.TERM_RANGE[1] + ". ");

					}
					break;
				}
			} catch (NumberFormatException e) {
				isValidValue = false;
			} 

			if (isValidValue) {
				i++;
			} else {
				MESSAGEHANDLER.print("An invalid value was entered.\n");
			}
		}
		
	}
	
	/**
	 * Method of excute the application. 
	 */
	public void execute() {
		InputHandler();
		try {
			AmortizationSchedule as = new AmortizationSchedule(amount, apr, years);
			as.generatedPaymentPlan();
			as.outputAmortizationSchedule();
		} catch (IllegalArgumentException e) {
			MESSAGEHANDLER.print("Unable to process the values entered. Terminating program.\n");
		}
	}
	
	public static void main(String argv[]) {
		Application app = new Application();
		app.execute();
	}
}
