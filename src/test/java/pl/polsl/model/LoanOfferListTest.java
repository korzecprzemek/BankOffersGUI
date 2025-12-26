package pl.polsl.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * LoanOfferList testing class
 * @author Przemysław Korzec
 * @version 0.5
 */
public class LoanOfferListTest {

    private LoanOfferList offerList;
    private LoanOffer offer1;
    private LoanOffer offer2;
    private LoanOffer offer3;

    @BeforeEach
    void setUp() {
        offerList = new LoanOfferList(
                (offer, profile) -> offer.getInterestRate()
        );
        offer1 = new LoanOffer("BankA", 5.0, 10.0, 1000.0);
        offer2 = new LoanOffer("BankB", 10.0, 20.0, 2000.0);
        offer3 = new LoanOffer("BankC", 3.0, 15.0, 3000.0);
    }

    /**
     * Stub LoanOffer – checks:
     *  - does score throw an exception,
     *  - what's the value score returns.
     */
    static class LoanOfferStub extends LoanOffer {

        /**
         * Fixed score for the stub
         */
        private final Double fixedScore;
        /**
         * Flag informing whether the offer throws an exception
         */
        private final boolean shouldThrow;

        /**
         * Stub constructor
         * @param bankName Bank name
         * @param requiredSavings Required savings
         * @param fixedScore Offer's fixed score
         * @param shouldThrow Whether it throws an exception
         */
        public LoanOfferStub(String bankName,
                             double requiredSavings,
                             Double fixedScore,
                             boolean shouldThrow) {
            super(bankName, 0.0, 0.0, requiredSavings);
            this.fixedScore = fixedScore;
            this.shouldThrow = shouldThrow;
        }

        /**
         * Score method overloaded throws an exception based on the shouldThrow flag
         * @param profile Customer's profile
         * @return Fixed offer score
         * @throws InvalidLoanParametersException Invalid parameters exception
         */
        @Override
        public double score(CustomerProfile profile) throws InvalidLoanParametersException {
            if (shouldThrow) {
                throw new InvalidLoanParametersException("Stub: invalid parameters");
            }
            return fixedScore;
        }
    }

    /**
     * Adding and getting all offers from the list
     * @param offersToAdd Offers to add
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void testAddOfferAndGetAllOffers(int offersToAdd) {
        if (offersToAdd >= 1) {
            offerList.addOffer(offer1);
        }
        if (offersToAdd >= 2) {
            offerList.addOffer(offer2);
        }

        List<LoanOffer> all = offerList.getAllOffers();

        assertEquals(offersToAdd, all.size());
        if (offersToAdd >= 1) {
            assertTrue(all.contains(offer1));
        }
        if (offersToAdd >= 2) {
            assertTrue(all.contains(offer2));
        }

        all.add(offer3);
        assertEquals(offersToAdd, offerList.getAllOffers().size());
    }

    /**
     * Test adding an empty offer to the list
     * @param offer Offer to be added
     */
    @ParameterizedTest
    @NullSource
    void testAddOffer_NullThrowsException(LoanOffer offer) {
        assertThrows(IllegalArgumentException.class,
                () -> offerList.addOffer(offer));
    }

    /**
     * Find bank by name method
     * @param searchName The bank to be found name
     * @param shouldFind Should it be found
     */
    @ParameterizedTest
    @CsvSource({
        "BankA, true",
        "banka, true",
        "NotExist, false",
    })
    void testFindByBankName(String searchName, boolean shouldFind) {
        LoanOfferList list = new LoanOfferList();
        list.addOffer(new LoanOffer("BankA", 5, 10, 1000));

        LoanOffer result = list.findByBankName(searchName);

        assertEquals(shouldFind, result != null);
    }

    /**
     * Testing deletion of elements from our list
     * @param offersToAdd Offers to be added
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 3})
    void testClear_RemovesAllOffers(int offersToAdd) {
        if (offersToAdd >= 1) {
            offerList.addOffer(offer1);
        }
        if (offersToAdd >= 2) {
            offerList.addOffer(offer2);
        }
        if (offersToAdd >= 3) {
            offerList.addOffer(offer3);
        }

        offerList.clear();

        assertTrue(offerList.getAllOffers().isEmpty());
    }

    /**
     * Finding the best offer method
     * @param savings Customer's savings
     * @param desiredLoan Desired loan amount
     * @param months Months to pay the loan in
     * @throws NoMatchingOffersException No Matching offers exception
     */
    @ParameterizedTest
    @CsvSource({
            "10000.0, 15000.0, 12",
            "5000.0, 20000.0, 24"
    })
    void testFindBestOffer_ReturnsCheapestAccordingToStrategy(
            double savings,
            double desiredLoan,
            int months
    ) throws NoMatchingOffersException {

        offerList.addOffer(offer1);
        offerList.addOffer(offer2);
        offerList.addOffer(offer3);

        CustomerProfile profile = new CustomerProfile(savings, desiredLoan, months);

        LoanOffer best = offerList.findBestOffer(profile);

        // Najniższe oprocentowanie ma BankC -> 3.0
        assertSame(offer3, best);
    }

    /**
     * Finding the best loan offer in the system, filtering by required savings
     * @param savings Customer's savings
     * @param expectedBestBank Best bank name
     */
    @ParameterizedTest
    @CsvSource({
            "1500.0, BankA",
            "2500.0, BankA",
            "3500.0, BankC"
    })
    void testFindBestOffer_FiltersByRequiredSavings(double savings, String expectedBestBank) {
        offerList.addOffer(offer1);
        offerList.addOffer(offer2);
        offerList.addOffer(offer3);

        CustomerProfile profile = new CustomerProfile(savings, 15_000.0, 12);

        LoanOffer best = assertDoesNotThrow(() -> offerList.findBestOffer(profile));

        assertEquals(expectedBestBank, best.getBankName());
    }

    /**
     * Testing the event when no offers in our system meet the requirements
     * @param savings Customer's savings
     */
    @ParameterizedTest
    @ValueSource(doubles = {0.0, 500.0, 999.99})
    void testFindBestOffer_NoEligibleOffersThrows(double savings) {
        offerList.addOffer(offer1); // requiredSavings = 1000
        offerList.addOffer(offer2); // requiredSavings = 2000

        CustomerProfile poor = new CustomerProfile(savings, 10_000.0, 12);

        NoMatchingOffersException ex = assertThrows(
                NoMatchingOffersException.class,
                () -> offerList.findBestOffer(poor)
        );

        assertEquals("Brak ofert spełniających podane oczekiwania", ex.getMessage());
    }

    /**
     * Constructor test
     * @param goodScore Good score
     * @throws NoMatchingOffersException No matching offers in our system
     */
    @ParameterizedTest
    @ValueSource(doubles = {1.0, 123.0, 9999.0})
    void testDefaultConstructor_IgnoreInvalidLoanParameters(double goodScore) throws NoMatchingOffersException {
        LoanOfferList defaultList = new LoanOfferList();

        LoanOffer badOffer = new LoanOfferStub(
                "BadBank",
                0.0,
                null,
                true
        );

        LoanOffer goodOffer = new LoanOfferStub(
                "GoodBank",
                0.0,
                goodScore,
                false
        );

        defaultList.addOffer(badOffer);
        defaultList.addOffer(goodOffer);

        CustomerProfile profile = new CustomerProfile(5000.0, 10_000.0, 12);

        LoanOffer best = defaultList.findBestOffer(profile);
        assertSame(goodOffer, best);
    }
}
