// Vue/AccueilController.java
package Vue;

import Controleur.Main;
import Controleur.UtilisateurControleur;
import Modele.Utilisateur;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AccueilController implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button adminButton;

    @FXML
    private VBox adminCard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Utilisateur utilisateur = UtilisateurControleur.getUtilisateurConnecte();

        if (utilisateur != null) {
            welcomeLabel.setText("Bonjour, " + utilisateur.getPrenom() + " " + utilisateur.getNom());

            // Afficher la carte admin seulement pour les administrateurs
            if ("admin".equals(utilisateur.getRole())) {
                adminCard.setVisible(true);
                adminCard.setManaged(true);
            } else {
                adminCard.setVisible(false);
                adminCard.setManaged(false);
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
    void handleMesReservations(ActionEvent event) {
        Main.showMesReservationsView();
    }

    @FXML
    void handleDeconnexion(ActionEvent event) {
        // Déconnecter l'utilisateur
        UtilisateurControleur utilisateurControleur = new UtilisateurControleur();
        utilisateurControleur.deconnecter();

        // Rediriger vers la page de connexion
        Main.showLoginView();
    }

    // Effet d'animation au survol des cartes
    @FXML
    void onCardHover(MouseEvent event) {
        Node card = (Node) event.getSource();
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), card);
        scaleTransition.setToX(1.05);
        scaleTransition.setToY(1.05);
        scaleTransition.play();

        // Changer l'ombre au survol
        card.setStyle(card.getStyle() + "; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);");
    }

    @FXML
    void onCardExit(MouseEvent event) {
        Node card = (Node) event.getSource();
        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), card);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);
        scaleTransition.play();

        // Restaurer l'ombre normale
        card.setStyle(card.getStyle().replace("; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0, 0, 5);", ""));
    }
}