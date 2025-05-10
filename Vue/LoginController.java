package Vue;

import Controleur.Main;
import Controleur.UtilisateurControleur;
import Modele.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private UtilisateurControleur utilisateurControleur = new UtilisateurControleur();

    @FXML
    void handleConnexion(ActionEvent event) {
        String email = emailField.getText().trim();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de connexion", "Veuillez remplir tous les champs.");
            return;
        }

        Utilisateur utilisateur = utilisateurControleur.connecter(email, password);

        if (utilisateur != null) {
            // Connexion réussie
            if ("admin".equals(utilisateur.getRole())) {
                // Si c'est un administrateur, ouvrir le panneau d'administration
                Main.showAdminPanel();
            } else {
                // Sinon, ouvrir l'écran d'accueil
                Main.showMainView();
            }
        } else {
            // Connexion échouée
            showAlert(Alert.AlertType.ERROR, "Erreur de connexion", "Email ou mot de passe incorrect.");
        }
    }

    @FXML
    void handleInscription(ActionEvent event) {
        Main.showRegistrationView();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}