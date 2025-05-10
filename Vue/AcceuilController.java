// Vue/AccueilController.java
package Vue;

import Controleur.Main;
import Controleur.UtilisateurControleur;
import Modele.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class AccueilController implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button adminButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utilisateur utilisateur = UtilisateurControleur.getUtilisateurConnecte();

        if (utilisateur != null) {
            welcomeLabel.setText("Bienvenue, " + utilisateur.getPrenom() + " " + utilisateur.getNom() + " !");

            // Afficher le bouton admin seulement pour les administrateurs
            if ("admin".equals(utilisateur.getRole())) {
                adminButton.setVisible(true);
            } else {
                adminButton.setVisible(false);
            }
        } else {
            // Rediriger vers la page de connexion si aucun utilisateur n'est connecté
            Main.showLoginView();
        }
    }

    @FXML
    void handleRechercheHebergement(ActionEvent event) {
        Main.showSearchView();
    }

    @FXML
    void handleAdminPanel(ActionEvent event) {
        Main.showAdminPanel();
    }

    @FXML
    void handleDeconnexion(ActionEvent event) {
        // Déconnecter l'utilisateur
        UtilisateurControleur utilisateurControleur = new UtilisateurControleur();
        utilisateurControleur.deconnecter();

        // Rediriger vers la page de connexion
        Main.showLoginView();
    }
}