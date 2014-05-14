package refectored;

import java.util.ArrayList;

public class AmortizationSchedule {
	public static final double[] BORROW_AMOUNT_RANGE = new double[] { 0.01d,
			1000000000000d };
	public static final double[] APR_RANGE = new double[] { 0.000001d, 100d };
	public static final int[] TERM_RANGE = new int[] { 1, 1000000 };
	private static final double monthlyInterestDivisor = 12d * 100d;
	private static final String STRING_FORMAT_TITLE = "%1$-20s%2$-20s%3$-20s%4$-20s%5$-20s%6$-20s\n";
	private static final String STRING_FORMAT_VALUE = "%1$-20d%2$-20.2f%3$-20.2f%4$-20.2f%5$-20.2f%6$-20.2f\n";

	private static final String[] REUSLT_TAB_NAME = { "PaymentNumber",
			"PaymentAmount", "PaymentInterest", "CurrentBalance",
			"TotalPayments", "TotalInterestPaid" };

	private static final ConsoleHandler MESSAGEHANDLER = ConsoleHandler
			.getInstance();
	private ArrayList<MonthlyPaymentPlan> plan;

	private long amountBorrowed = 0; // in cents
	private double apr = 0d;
	private int initialTermMonths = 0;

	private double monthlyInterest = 0d;
	private long monthlyPaymentAmount = 0; // in cents

	public AmortizationSchedule(double amount, double interestRate, int years)
			throws IllegalArgumentException {
		if ((isValidBorrowAmount(amount) == false)
				|| (isValidAPRValue(interestRate) == false)
				|| (isValidTerm(years) == false)) {
			throw new IllegalArgumentException("");
		}
		amountBorrowed = Math.round(amount * 100);
		apr = interestRate;
		initialTermMonths = years * 12;
		monthlyInterest = apr / monthlyInterestDivisor;
		monthlyPaymentAmount = calculateMonthlyPayment();
		/*
		 * the following shouldn't happen with the available valid ranges for
		 * borrow amount, apr, and term; however, without range validation,
		 * monthlyPaymentAmount as calculated by calculateMonthlyPayment() may
		 * yield incorrect values with extreme input values
		 */
		if (monthlyPaymentAmount > amountBorrowed) {
			throw new IllegalArgumentException();
		}
		plan = new ArrayList<MonthlyPaymentPlan>();
	}

	public static boolean isValidBorrowAmount(double amount) {
		return ((BORROW_AMOUNT_RANGE[0] <= amount) && (amount <= BORROW_AMOUNT_RANGE[1]));
	}

	public static boolean isValidAPRValue(double rate) {
		return ((APR_RANGE[0] <= rate) && (rate <= APR_RANGE[1]));
	}

	public static boolean isValidTerm(int years) {
		return ((TERM_RANGE[0] <= years) && (years <= TERM_RANGE[1]));
	}

	private long calculateMonthlyPayment() {
		/*
		 * M = P * (J / (1 - (Math.pow(1/(1 + J), N)))); Where: P = Principal I
		 * = Interest J = Monthly Interest in decimal form: I / (12 * 100) N =
		 * Number of months of loan M = Monthly Payment Amount calculate J
		 * monthlyInterest = apr / monthlyInterestDivisor; this is 1 / (1 + J)
		 */
		double tmp = Math.pow(1d + monthlyInterest, -1);

		// this is Math.pow(1/(1 + J), N)
		tmp = Math.pow(tmp, initialTermMonths);

		// this is 1 / (1 - (Math.pow(1/(1 + J), N))))
		tmp = Math.pow(1d - tmp, -1);

		// M = P * (J / (1 - (Math.pow(1/(1 + J), N))));
		double rc = amountBorrowed * monthlyInterest * tmp;

		return Math.round(rc);
	}
	
	/**
	 * Method for generated PaymentPlan
	 */
	public void generatedPaymentPlan() {
		long balance = amountBorrowed;
		int paymentNumber = 0;
		long totalPayments = 0;
		long totalInterestPaid = 0;
		
		MonthlyPaymentPlan mp = new MonthlyPaymentPlan(paymentNumber++, 0, 0, amountBorrowed, totalPayments, totalInterestPaid);
		plan.add(mp);
		
		final int maxNumberOfPayments = initialTermMonths + 1;
		while ((balance > 0) && (paymentNumber <= maxNumberOfPayments)) {

			// Calculate H = P x J, this is your current monthly interest
			long curMonthlyInterest = Math.round(((double) balance)
					* monthlyInterest);
			// the amount required to payoff the loan
			long curPayoffAmount = balance + curMonthlyInterest;

			// the amount to payoff the remaining balance may be less than the
			// calculated monthlyPaymentAmount
			long curMonthlyPaymentAmount = Math.min(monthlyPaymentAmount,
					curPayoffAmount);

			// it's possible that the calculated monthlyPaymentAmount is 0,

			// or the monthly payment only covers the interest payment - i.e. no
			// principal

			// so the last payment needs to payoff the loan
			if ((paymentNumber == maxNumberOfPayments)
					&& ((curMonthlyPaymentAmount == 0) || (curMonthlyPaymentAmount == curMonthlyInterest))) {
				curMonthlyPaymentAmount = curPayoffAmount;
			}

			// Calculate C = M - H, this is your monthly payment minus your
			// monthly interest,

			// so it is the amount of principal you pay for that month

			long curMonthlyPrincipalPaid = curMonthlyPaymentAmount
					- curMonthlyInterest;

			// Calculate Q = P - C, this is the new balance of your principal of
			// your loan.
			long curBalance = balance - curMonthlyPrincipalPaid;
			totalPayments += curMonthlyPaymentAmount;
			totalInterestPaid += curMonthlyInterest;

			// output is in dollars
			
			mp = new MonthlyPaymentPlan(paymentNumber++, curMonthlyPaymentAmount, curMonthlyInterest, curBalance, totalPayments, totalInterestPaid);
			plan.add(mp);

			// Set P equal to Q and go back to Step 1: You thusly loop around
			// until the value Q (and hence P) goes to zero.
			balance = curBalance;
		}
	}

	/**
	 * The output should include: The first column identifies the payment
	 * number. The second column contains the amount of the payment. The third
	 * column shows the amount paid to interest. The fourth column has the
	 * current balance. The total payment amount and the interest paid fields.
	 */
	public void outputAmortizationSchedule() {
		/*
		 * To create the amortization table, create a loop in your program and
		 * follow these steps: 1. Calculate H = P x J, this is your current
		 * monthly interest 2. Calculate C = M - H, this is your monthly payment
		 * minus your monthly interest, so it is the amount of principal you pay
		 * for that month
		 * 
		 * 3. Calculate Q = P - C, this is the new balance of your principal of
		 * your loan. 4. Set P equal to Q and go back to Step 1: You thusly loop
		 * around until the value Q (and hence P) goes to zero.
		 */
		MESSAGEHANDLER.printf(STRING_FORMAT_TITLE, REUSLT_TAB_NAME[0],
				REUSLT_TAB_NAME[1], REUSLT_TAB_NAME[2], REUSLT_TAB_NAME[3],
				REUSLT_TAB_NAME[4], REUSLT_TAB_NAME[5]);

		// output is in dollars
		for(MonthlyPaymentPlan mp : plan) {
			MESSAGEHANDLER.printf(STRING_FORMAT_VALUE, mp.paymentNo,
					((double) mp.curMonthlyPaymentAmount) / 100d,
					((double) mp.curMonthlyInterest) / 100d,
					((double) mp.curBalance) / 100d,
					((double) mp.totalPayments) / 100d,
					((double) mp.totalInterestPaid) / 100d);
		}

	}
}
