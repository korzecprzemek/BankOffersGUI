package pl.polsl.model;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * LoanOffer testing class
 * @author Przemysław Korzec
 * @version 0.5
 */
public class LoanOfferTest {
    /**
     * Bank loan offer
     */
    private LoanOffer loanOffer;

    @BeforeEach
    public void setUp() {
        // bankName = "B1", interestRate = 8.5, monthlyFee = 20, requiredSavings = 5000
        loanOffer = new LoanOffer("B1", 8.5, 20, 5000.0);
    }

    /**
     * Scoring method test
     * @param clientSavings Customer's savings
     * @param loanAmount Desired loan amount
     * @param months Months to pay the loan in 
     * @throws InvalidLoanParametersException Invalid parameters exception
     */
    @ParameterizedTest
    @CsvSource({
        // savings, loanAmount, months
        "6000.0, 15000.0, 12",
        "7000.0, 20000.0, 24"
    })
    public void testScore_ValidParameters_ReturnsTotalCost(double clientSavings,
                                                           double loanAmount,
                                                           int months)
            throws InvalidLoanParametersException {

        double result = loanOffer.score(clientSavings, loanAmount, months);

        double expected = loanOffer.calculateTotalCost(loanAmount, months);
        assertEquals(expected, result, 0.0001);
    }
/**
 * Testing invalid parameters score method
 * @param savings Customer's savings
 * @param loanAmount Desired loan amount
 * @param months Months to pay the loan in
 * @param expectedMessage Expected message of the exception
 */
    @ParameterizedTest
    @CsvSource({
        "-10,  10000, 12, 'Oszczędności klienta nie mogą być ujemne.'",
        "5000, 0,     12, 'Kwota pożyczki musi być większa od zera.'",
        "5000, 10000, 0,  'Liczba miesięcy musi być większa od zera.'"
    })
    void testScore_InvalidParameters(double savings, double loanAmount, int months, String expectedMessage) {

        LoanOffer offer = new LoanOffer("B1", 8.5, 20, 1000);

        InvalidLoanParametersException ex = assertThrows(
                InvalidLoanParametersException.class,
                () -> offer.score(savings, loanAmount, months)
        );

        assertEquals(expectedMessage, ex.getMessage());
    }

    /**
     * Testing the exception when customer's savings are not sufficient
     * @param clientSavings Client's savings
     * @param loanAmount Desired loan amount
     * @param months Months to pay the loan in 
     * @param requiredSavings Required savings
     */
    @ParameterizedTest
    @CsvSource({
        // clientSavings, loanAmount, months, requiredSavings
        "3000.0, 15000.0, 12, 5000.0",
        "1000.0,  8000.0,  6, 5000.0",
        "0.0,     5000.0,  3, 5000.0"
    })
    public void testScore_NotEnoughSavings_ThrowsException(double clientSavings,
                                                           double loanAmount,
                                                           int months,
                                                           double requiredSavings) {

        LoanOffer offer = new LoanOffer("B1", 8.5, 20, requiredSavings);

        InvalidLoanParametersException ex = assertThrows(
                InvalidLoanParametersException.class,
                () -> offer.score(clientSavings, loanAmount, months)
        );

        assertTrue(ex.getMessage().contains("Klient nie posiada wystarczających oszczędności."));
        assertTrue(ex.getMessage().contains(String.valueOf(requiredSavings)));
    }
}
