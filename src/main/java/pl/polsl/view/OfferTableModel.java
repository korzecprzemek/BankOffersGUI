/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.view;

import pl.polsl.model.LoanOffer;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
/**
 * Class handling our table model
 * @author pkorzec
 * @version 0.4
 */
public class OfferTableModel extends AbstractTableModel {
    /**
     * Default constructor is used
     */
    public OfferTableModel(){
        
    }
    /**
     * List of columns
     */
    private final String[] columns = {
            "Bank", "Oprocentowanie (%)", "Okres (mies.)", "Wymagane Oszczędności (zł)"
    };
    /**
     * Available offers list as the table's data
     */
    private final List<LoanOffer> data = new ArrayList<>();

    /**
     * Clears the table and fills it with the provided offers list. Signifies all listeners after updating the table.
     * @param offers Available offers list
     */
    public void setOffers(List<LoanOffer> offers) {
        data.clear();
        if (offers != null) data.addAll(offers);
        fireTableDataChanged();
    }

    /**
     * Row Count getter
     * @return Size of our data
     */
    @Override
    public int getRowCount() {
        return data.size();
    }

    /**
     * Column Count getter
     * @return Length of our columns list
     */
    @Override
    public int getColumnCount() {
        return columns.length;
    }

    /**
     * Column name getter
     * @param col Column index
     * @return Name of a column
     */
    @Override
    public String getColumnName(int col) {
        return columns[col];
    }

    /**
     * 
     * @param row Row Index
     * @param col Column Index
     * @return Value in our table
     */
    @Override public Object getValueAt(int row, int col) {
        LoanOffer o = data.get(row);
        return switch (col) {
            case 0 -> o.getBankName();
            case 1 -> o.getInterestRate();
            case 2 -> o.getMonthlyFee();
            case 3 -> o.getRequiredSavings();
            default -> "";
        };
    }
/**
 * Offer getter
 * @param row Row representing our offer
 * @return An offer as a row
 */
    public LoanOffer getOfferAt(int row) {
        return data.get(row);
    }
}
