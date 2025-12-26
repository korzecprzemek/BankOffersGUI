/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package pl.polsl.model;

/**
 * Interface being a strategy for scoring an offer
 * @author Przemysław Korzec
 * @version 0.4
 */
@FunctionalInterface
public interface OfferScoringStrategy {

    /**
     *
     * @param offer Loan Offer to be calculated
     * @param profile Customer's profile
     * @return Offer's score
     */
    double calculate(LoanOffer offer, CustomerProfile profile);
}
