/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.polsl.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Offer testing class
 * @author Przemysław Korzec
 * @version 0.5
 */
public class OfferTest {

    private Offer offer;

    public OfferTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        offer = new Offer("B1", 8.5, 20);
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Calculating total cost test
     * @param amount Desired loan amount
     * @param months Months to pay the loan in
     */
    @ParameterizedTest
    @CsvSource({
        "15000, 12",
        "12000, 6",
        "10000, 0"
    })
    public void testCalculateTotalCost(double amount, int months) {
        Offer offer = new Offer("B1", 8.5, 20);

        double result = offer.calculateTotalCost(amount, months);

        double expectedInterest = amount * (8.5 / 100.0) * (months / 12.0);
        double expected = amount + expectedInterest + 20 * months;

        assertEquals(expected, result, 0.0001);
    }

    /**
     * Testing getters and constructor
     * @param bankName BankName
     * @param interestRate Interest Rate
     * @param monthlyFee Monthly Fee
     */
    @ParameterizedTest
    @CsvSource({
        "B1,     8.5, 20.0",
        "BankX,  0.0,  0.0",
        "TestBK, 3.5, 10.0"
    })
    public void testConstructorAndGetters(String bankName,
                                          double interestRate,
                                          double monthlyFee) {

        Offer localOffer = new Offer(bankName, interestRate, monthlyFee);

        assertEquals(bankName, localOffer.getBankName());
        assertEquals(interestRate, localOffer.getInterestRate(), 0.0001);
        assertEquals(monthlyFee, localOffer.getMonthlyFee(), 0.0001);
    }

    /**
     * Testing setters
     * @param newName New Bank Name
     * @param newInterestRate New Interest Rate
     * @param newMonthlyFee New Monthly Fee
     */
    @ParameterizedTest
    @CsvSource({
        "NowyBank, 10.0, 30.0",
        "InnyBank,  5.5,  0.0",
        "XBank,     0.0, 15.0"
    })
    public void testSetters(String newName,
                            double newInterestRate,
                            double newMonthlyFee) {

        offer.setBankName(newName);
        offer.setInterestRate(newInterestRate);
        offer.setMonthlyFee(newMonthlyFee);

        assertEquals(newName, offer.getBankName());
        assertEquals(newInterestRate, offer.getInterestRate(), 0.0001);
        assertEquals(newMonthlyFee, offer.getMonthlyFee(), 0.0001);
    }
}
