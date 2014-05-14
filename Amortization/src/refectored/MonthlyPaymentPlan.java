package refectored;

public class MonthlyPaymentPlan {
	public int paymentNo;
	public long curMonthlyPaymentAmount;
	public long curMonthlyInterest;
	public long curBalance;
	public long totalPayments;
	public long totalInterestPaid;
	
	public MonthlyPaymentPlan(int paymentNo, long curMonthlyPaymentAmount,
			long curMonthlyInterest, long curBalance, long totalPayments,
			long totalInterestPaid) {
		this.paymentNo = paymentNo;
		this.curMonthlyPaymentAmount = curMonthlyPaymentAmount;
		this.curMonthlyInterest = curMonthlyInterest;
		this.curBalance = curBalance;
		this.totalPayments = totalPayments;
		this.totalInterestPaid = totalInterestPaid;
	}
}
