package Vue;

import Controleur.Main;
import Modele.Reservation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.math.BigDecimal;

public class PaiementController {

    @FXML
    private Label reservationIdLabel;

    @FXML
    private Label montantTotalLabel;

    @FXML
    private TextField numeroCarteField;

    @FXML
    private TextField dateExpirationField;

    @FXML
    private TextField cvcField;

    private Reservation reservation;

    public void initData(Reservation reservation) {
        this.reservation = reservation;

        if (reservation != null) {
            reservationIdLabel.setText(String.valueOf(reservation.getId()));

            // Calculer le montant total (prix total - réduction)
            BigDecimal montantTotal = reservation.getTotal();
            if (reservation.getReduction() != null) {
                montantTotal = montantTotal.subtract(reservation.getReduction());
            }

            montantTotalLabel.setText(String.format("%.2f €", montantTotal));
        }
    }

    @FXML
    void handlePayer(ActionEvent event) {
        String numeroCarte = numeroCarteField.getText();
        String dateExpiration = dateExpirationField.getText();
        String cvc = cvcField.getText();

        if (numeroCarte.isEmpty() || dateExpiration.isEmpty() || cvc.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de paiement", "Veuillez remplir tous les champs de paiement.");
            return;
        }

        // Vérifier le format du numéro de carte (exemple très simple)
        if (!numeroCarte.matches("\\d{16}")) {
            showAlert(Alert.AlertType.ERROR, "Erreur de paiement", "Le numéro de carte doit contenir 16 chiffres.");
            return;
        }

        // Vérifier le format de la date d'expiration (MM/YY)
        if (!dateExpiration.matches("\\d{2}/\\d{2}")) {
            showAlert(Alert.AlertType.ERROR, "Erreur de paiement", "La date d'expiration doit être au format MM/YY.");
            return;
        }

        // Vérifier le format du CVC
        if (!cvc.matches("\\d{3}")) {
            showAlert(Alert.AlertType.ERROR, "Erreur de paiement", "Le CVC doit contenir 3 chiffres.");
            return;
        }

        // Simuler un paiement réussi
        showAlert(Alert.AlertType.INFORMATION, "Paiement réussi", "Votre paiement a été traité avec succès. Un email de confirmation vous a été envoyé.");

        // Rediriger vers l'écran d'accueil
        Main.showMainView();
    }

    @FXML
    void handleAnnuler(ActionEvent event) {
        // Note: Dans une application réelle, on devrait annuler la réservation ici
        Main.showMainView();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}