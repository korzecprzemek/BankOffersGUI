/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;

import lombok.Getter;
import lombok.Setter;

import java.util.*;
/**
 * Represents a list of Loan Offers available in our system. Provides methods to operate on a list of offers
 * @author Przemysław Korzec
 * @version 0.4
 */
@Getter
@Setter
public class LoanOfferList {
    /**
     * List of offers available in our system
     */
    private List<LoanOffer> offers = new ArrayList<>();
    /**
     * Offer scoring strategy
     */
    private OfferScoringStrategy scoringStrategy;
    /**
     * Constructor with no parameters - chooses offer.score by default as the strategy
     */
    public LoanOfferList() {
        this.scoringStrategy = (offer,profile) -> {
            try {
                return offer.score(profile);
            } catch (InvalidLoanParametersException e) {
                return Double.POSITIVE_INFINITY;
            }
        };
    }
    /**
     * Constructor assigning a strategy on creation
     * @param scoringStrategy Scoring strategy to be used on this instance
     */
    public LoanOfferList(OfferScoringStrategy scoringStrategy)
    {
        this.scoringStrategy = scoringStrategy;
    }
      /**
     * Adds an offer to our list
     * @param offer Offer to be added
     */
    public void addOffer(LoanOffer offer) {
    if (offer == null) {
        throw new IllegalArgumentException("Offer cannot be null");
    }
    offers.add(offer);
}
    /**
     * Returns all stored offers.
     * @return a list of loan offers
     */
    public List<LoanOffer> getAllOffers() {
        return new ArrayList<>(offers);
    }

    /**
     * Finds a loan offer by bank name.
     * @param bankName the name of the bank
     * @return the found LoanOffer or null if not found
     */
    public LoanOffer findByBankName(String bankName) {
        for (LoanOffer offer : offers) {
            if (offer.getBankName().equalsIgnoreCase(bankName)) {
                return offer;
            }
        }
        return null;
    }
    /**
     * Clears all offers from the list.
     */
    public void clear() {
        offers.clear();
    }
    /**
     * Finds the best offer for the customer's data provided
     * @param profile Customer's data
     * @return Best loan offer
     * @throws NoMatchingOffersException No matching offers in our system
     */
public LoanOffer findBestOffer(CustomerProfile profile) throws NoMatchingOffersException {
        var eligible = offers.stream()
            .filter(o -> profile.savings() >= o.getRequiredSavings())
            .toList();

        if (eligible.isEmpty()) {
            throw new NoMatchingOffersException("Brak ofert spełniających podane oczekiwania");
        }
        return eligible.stream()
            .min(Comparator.comparingDouble(o -> scoringStrategy.calculate(o,profile)))
            .orElseThrow();
    }

    private double safeScore(LoanOffer offer, CustomerProfile profile) {
        try {
            return offer.score(profile);
        } catch (InvalidLoanParametersException e) {
            return Double.POSITIVE_INFINITY;
        }
    }
    
}
