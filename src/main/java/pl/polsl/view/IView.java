package pl.polsl.view;

import java.awt.event.ActionListener;
import java.util.List;
import pl.polsl.model.LoanOffer;
/**
 * View Interface
 * @author Przemysław Korzec
 * @version 0.4
 */
public interface IView {
    /**
     * Savings getter
     * @return Savings as a double type
     */
    double getSavings();
    /**
     * Amount getter
     * @return Loan amount as a double type
     */
    double getAmount();
    /**
     * Months getter
     * @return Months to pay in as integer type
     */
    int getMonths();

    /**
     * Output setter
     * @param msg Message to be set
     */
    void setOutput(String msg);
    /**
     * Prints an error
     * @param msg Error message
     */
    void showError(String msg);

    /**
     * Adds a listener to a control
     * @param l Listener to be added
     */
    void addFindListener(ActionListener l); // Controller się podłącza
    /**
     * Shows our offers list
     * @param offers Available offers list
     */
    void showOffers(List<LoanOffer> offers);

}
