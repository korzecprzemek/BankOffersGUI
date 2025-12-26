package pl.polsl.bankoffersgui;
import javax.swing.SwingUtilities;
import pl.polsl.controller.Controller;
import pl.polsl.view.SwingView;

/**
 * Main class, we call an instance of the controller here.
 * @author Przemysław Korzec
 * @version 0.4
 */
public class BankOffersGUI {

    /**
     * Default constructor is used
     */
    BankOffersGUI(){
        
    }
    /**
     * main method
     * @param args Arguments list: Savings, Desired Loan, Months to pay in
     */
   public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SwingView view = new SwingView();
            Controller controller = new Controller(view);
            view.setVisible(true);
        });
    }
}
