package pl.polsl.model;
import lombok.Getter;
import lombok.Setter;
/**
 *
 * Inherits from Offer
 * @see pl.polsl.model.Offer
 * @author Przemysław Korzec
 * @version 0.4
 */
public class LoanOffer extends Offer{
    /**
     * Required savings in [PLN]
     */
    @Getter
    @Setter
    private double requiredSavings;
    
    /** Calls the parent constructor and adds a requiredSavings field
     @param bankName Name of a Bank
     @param interestRate Interest Rate of the offer
     @param monthlyFee The monthly fee that has to be paid
     @param requiredSavings The amount of savings a customer is required to have to take the loan
     */    
    public LoanOffer(String bankName, double interestRate, double monthlyFee, double requiredSavings){
            super(bankName, interestRate,monthlyFee);
            this.requiredSavings = requiredSavings;
        }
    /** 
     * Calculates the score for provided Customer's data
     * @param clientSavings Client's savings
     * @param loanAmount Clients declared loan amount
     * @param months Months to pay the loan
     * @return Total cost of the loan
     * @throws pl.polsl.model.InvalidLoanParametersException InvalidParameters
     * @see pl.polsl.model.Offer#calculateTotalCost(double, int) 
     */
    public double score(double clientSavings, double loanAmount, int months) throws InvalidLoanParametersException {
        if (clientSavings < 0) {
            throw new InvalidLoanParametersException("Oszczędności klienta nie mogą być ujemne.");
        }
        if (loanAmount <= 0) {
            throw new InvalidLoanParametersException("Kwota pożyczki musi być większa od zera.");
        }
        if (months <= 0) {
            throw new InvalidLoanParametersException("Liczba miesięcy musi być większa od zera.");
        }

        if (clientSavings < requiredSavings) {
            throw new InvalidLoanParametersException(
                "Klient nie posiada wystarczających oszczędności. Wymagane minimum: " + requiredSavings
            );
        }
        return calculateTotalCost(loanAmount, months);
    }
    /**
     * Score method overridden - calls the score(s,d,m) function for the customer profile
     * @param profile Customer's data provided
     * @return loan score calculated for the customer's data
     * @throws InvalidLoanParametersException Invalid parameters provided by the customer
     */
    public double score(CustomerProfile profile) throws InvalidLoanParametersException {
        return score(profile.savings(), profile.desiredLoan(), profile.termMonths());
    }

}
    
