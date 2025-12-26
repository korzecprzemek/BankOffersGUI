package pl.polsl.model;

/**
 * Immutable data record representing a customer's profile.
 * @author Przemysław Korzec
 * @param savings Customer's savings
 * @param desiredLoan Loan to be taken
 * @param termMonths Months to pay the loan in
 * @version 0.4
 */
public record CustomerProfile(double savings, double desiredLoan, int termMonths) {
    
    /**
     * Canonical constructor with validation.
     * @param savings Customer's savings
     * @param desiredLoan Customer's desired Loan
     * @param termMonths Customer's months to pay
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public CustomerProfile {
        if (savings < 0) {
            throw new IllegalArgumentException("Oszczędności nie mogą być ujemne.");
        }
        if (desiredLoan <= 0) {
            throw new IllegalArgumentException("Kwota pożyczki musi być większa od zera.");
        }
        if (termMonths <= 0) {
            throw new IllegalArgumentException("Liczba miesięcy musi być większa od zera.");
        }
    }
}