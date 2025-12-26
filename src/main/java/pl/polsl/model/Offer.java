/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.model;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * This class represents a Bank Offer.
 * @author Przemysław Korzec
 * @version 0.4
 */
public class Offer {
    /**
     * Name of a bank
     */
    @Getter
    @Setter
    protected String bankName;
    /**
     * Interest rate
     */
    @Getter
    @Setter
    protected double interestRate;
    /**
     * Fee for each month
     */
    @Getter
    @Setter
    protected double monthlyFee;
    
    /**
     * Offer constructor
     * @param bankName Bank Name
     * @param interestRate Interest Rate
     * @param monthlyFee Fee for each month
     */
    public Offer(String bankName, double interestRate, double monthlyFee){
        this.bankName = bankName;
        this.interestRate = interestRate;
        this.monthlyFee = monthlyFee;
    }
    /** 
     * Calculates total cost
     @param amount Loan Amount
     @param months Months to pay the loan
     @return The total loan cost
     */
    public double calculateTotalCost(double amount, int months){
        double interest = amount * (interestRate / 100) * (months / 12.0);
        return amount + interest + monthlyFee *months;
    }
}
