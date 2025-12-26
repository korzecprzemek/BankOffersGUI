package pl.polsl.controller;

import pl.polsl.model.*;
import pl.polsl.view.IView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * This is where we combine the logic with our user interface.
 * @author Przemysław Korzec
 * @version 0.4
 */
public class Controller {
    private final IView view;
    private final LoanOfferList offers = new LoanOfferList();

    /**
     * Controller constructor
     * @param view Reference to a view object
     */
    public Controller(IView view){
        this.view = view;
        loadOffersFromCsv("/loan_offers.csv");
        view.showOffers(offers.getAllOffers());

        bind();
    }
    /**
     * Loads offers from a CSV file (bankName,interestRate,monthlyFee,requiredSavings)
     * @param resourcePath Path to the CSV file
     */
    private void loadOffersFromCsv(String resourcePath) {
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new IOException("Nie znaleziono pliku CSV: " + resourcePath);
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
                String line;
                boolean headerSkipped = false;
                while ((line = br.readLine()) != null) {
                    if (!headerSkipped) { headerSkipped = true; continue; }
                    if (line.isBlank()) continue;

                    // bankName,interestRate,monthlyFee,requiredSavings
                    String[] parts = line.split("\\s*,\\s*");
                    if (parts.length < 4) continue;

                    String bankName     = parts[0];
                    double interestRate = Double.parseDouble(parts[1]);
                    double monthlyFee   = Double.parseDouble(parts[2]);
                    double requiredSav  = Double.parseDouble(parts[3]);

                    offers.addOffer(new LoanOffer(bankName, interestRate, monthlyFee, requiredSav));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            view.showError("Błąd wczytywania CSV: " + e.getMessage());
        }
    }
    /** Binds the controller with the view, adding a view listener */
    private void bind() {
        view.addFindListener(e -> {
            try {
                findAndShowBest(view.getSavings(), view.getAmount(), view.getMonths());
            } catch (NumberFormatException ex) {
                view.showError("Podaj poprawne liczby (np. 5000, 20000, 36).");
            }
        });
    }
    /**
     * Calculates and displays score for the best offer
     */
    private void findAndShowBest(double savings, double amount, int months) {
        if (!validateInput(savings, amount, months)) return;

        try {
            CustomerProfile p = new CustomerProfile(savings, amount, months);

            LoanOffer best = offers.findBestOffer(p);

            
            double cost = best.score(p);
            //double cost = best.score(p.savings(), p.desiredLoan(), p.termMonths());

            view.setOutput("Najlepsza: " + best.getBankName() +
                    " | koszt: " + String.format("%.2f", cost) + " zł");
        } catch (InvalidLoanParametersException ex) {
            view.showError("Nieprawidłowe parametry: " + ex.getMessage());
        } catch (Exception ex) {
            view.showError("Wystąpił błąd: " + ex.getMessage());
        }
    }
    /** Input validation */
    private boolean validateInput(double savings, double amount, int months) {
        if (savings < 0) {
            view.showError("Oszczędności nie mogą być ujemne.");
            return false;
        }
        if (amount <= 0) {
            view.showError("Kwota kredytu musi być > 0.");
            return false;
        }
        if (months <= 0) {
            view.showError("Liczba miesięcy musi być > 0.");
            return false;
        }
        return true;
    }
}
