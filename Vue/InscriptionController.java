package Vue;

import Controleur.Main;
import Controleur.UtilisateurControleur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class InscriptionController {

    @FXML
    private TextField nomField;

    @FXML
    private TextField prenomField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    private UtilisateurControleur utilisateurControleur = new UtilisateurControleur();

    @FXML
    void handleInscription(ActionEvent event) {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Vérifier que tous les champs sont remplis
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur d'inscription", "Veuillez remplir tous les champs.");
            return;
        }

        // Vérifier que les mots de passe correspondent
        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Erreur d'inscription", "Les mots de passe ne correspondent pas.");
            return;
        }

        // Tenter d'inscrire l'utilisateur
        boolean success = utilisateurControleur.inscrire(nom, prenom, email, password);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Inscription réussie", "Votre compte a été créé avec succès. Vous pouvez maintenant vous connecter.");
            Main.showLoginView();
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur d'inscription", "Une erreur s'est produite lors de l'inscription. Veuillez réessayer.");
        }
    }

    @FXML
    void handleRetour(ActionEvent event) {
        Main.showLoginView();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}