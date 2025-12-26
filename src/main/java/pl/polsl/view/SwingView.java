package pl.polsl.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import pl.polsl.model.LoanOffer;
/**
 * Handles Graphical User Interface
 * @author Przemysław Korzec
 * @version 0.4
 */

public class SwingView extends JFrame implements IView {
    /**
     * Savings TextField
     */
    private final JTextField tfSavings = new JTextField(5);
    /**
     * Loan Amount TextField
     */
    private final JTextField tfAmount  = new JTextField(5);
    /**
     * Months TextField
     */
    private final JTextField tfMonths  = new JTextField(5);
    /**
     * 'Find Best' Button
     */
    private final JButton btnFind = new JButton("Znajdź najlepszą");
    /**
     * Output TextArea
     */
    private final JTextArea output = new JTextArea(5, 30);
    
    /**
     * Available offers table
     */
    private JTable offersTable;
    /**
     * Offers Model
     */
    private OfferTableModel offersModel;

    /**
     * SwingView constructor. This is where the application's controls and GUI is assembled.
     */
    public SwingView() {
        super("Kalkulator kredytu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel main = new JPanel(new BorderLayout(8,8));
        JPanel form = new JPanel(new GridLayout(0, 2, 6, 6));
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        //JPanel form = new JPanel(new BorderLayout());
        

        JLabel lblSavings = new JLabel("Oszczędności (zł):");
        lblSavings.setDisplayedMnemonic('O');
        lblSavings.setLabelFor(tfSavings);
        tfSavings.setToolTipText("Wpisz kwotę swoich oszczędności (np. 5000 zł)");
        tfSavings.getAccessibleContext().setAccessibleDescription(
            "Pole tekstowe umożliwiające wpisanie aktualnych oszczędności klienta");

        JLabel lblAmount = new JLabel("Kwota kredytu (zł):");
        lblAmount.setDisplayedMnemonic('K');
        lblAmount.setLabelFor(tfAmount);
        tfAmount.setToolTipText("Podaj żądaną kwotę kredytu (np. 20000 zł)");
        tfAmount.getAccessibleContext().setAccessibleDescription(
            "Pole tekstowe umożliwiające wpisanie kwoty kredytu, o którą wnioskuje klient");

        JLabel lblMonths = new JLabel("Okres spłaty (mies.):");
        lblMonths.setDisplayedMnemonic('M');
        lblMonths.setLabelFor(tfMonths);
        tfMonths.setToolTipText("Podaj liczbę miesięcy spłaty (np. 36)");
        tfMonths.getAccessibleContext().setAccessibleDescription(
            "Pole tekstowe umożliwiające wpisanie liczby miesięcy spłaty kredytu");

        btnFind.setMnemonic('Z');
        btnFind.setToolTipText("Kliknij lub naciśnij Alt+Z, aby znaleźć najlepszą ofertę kredytu");
        btnFind.getAccessibleContext().setAccessibleDescription(
            "Przycisk uruchamiający wyszukiwanie optymalnej oferty kredytowej na podstawie podanych danych");

        output.setEditable(false);
        output.setToolTipText("Tutaj pojawi się wynik wyszukiwania najlepszej oferty");
        output.getAccessibleContext().setAccessibleDescription(
            "Obszar tekstowy, w którym wyświetlany jest wynik wyszukiwania oferty kredytowej");

        JLabel lbloffersTable = new JLabel("Dostępne oferty");
        lbloffersTable.setLabelFor(offersTable);
        offersModel = new OfferTableModel();
        offersTable = new JTable(offersModel);
        offersTable.setAutoCreateRowSorter(true);      // sortowanie po nagłówkach
        offersTable.setFillsViewportHeight(true);

        JScrollPane scroll = new JScrollPane(offersTable);
        scroll.setPreferredSize(new Dimension(200, 80));
        
        offersTable.setToolTipText("Lista dostępnych ofert z pliku CSV");
        offersTable.getTableHeader().setReorderingAllowed(false);
        
        form.add(lblSavings);
        form.add(tfSavings);
        form.add(lblAmount);
        form.add(tfAmount);
        form.add(lblMonths);
        form.add(tfMonths);
        form.add(new JLabel());

        
        actions.add(btnFind);
        
        main.add(form, BorderLayout.NORTH);
        main.add(scroll, BorderLayout.SOUTH);
        main.add(actions, BorderLayout.CENTER);
        
        add(main, BorderLayout.CENTER);
        add(new JScrollPane(output), BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null); //
    }

    /**
     * TextField Savings getter. Parses the text into a double type value
     * @return tfSavings as double type
     */
    @Override
    public double getSavings() {
        return Double.parseDouble(tfSavings.getText().trim());
    }

    /**
     * TextField Amount getter. Parses the text into a double type value
     * @return tfAmount as double type
     */
    @Override
    public double getAmount() {
        return Double.parseDouble(tfAmount.getText().trim());
    }

    /**
     * TextField Months getter. Parses the text into an integer type value
     * @return tfMonths as integer type
     */
    @Override
    public int getMonths() {
        return Integer.parseInt(tfMonths.getText().trim());
    }
    /**
     * Output setter
     * @param msg message
     */
    @Override
    public void setOutput(String msg) {
        output.setText(msg + "\n");
    }

    /**
     * Shows an error pane
     * @param msg Error message
     */
    @Override
    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Błąd", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Adds a listener to a the 'Find best' button
     * @param l The listener
     */
    @Override
    public void addFindListener(ActionListener l) {
        btnFind.addActionListener(l);
    }
    
    /**
     * Sets the data to a a TableModel that is connected to our Table
     * @param offers Available offers list
     */
    @Override
    public void showOffers(List<LoanOffer> offers) {
        offersModel.setOffers(offers);
    }
}

