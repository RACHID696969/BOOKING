package Vue;

import Controleur.HebergementControleur;
import Controleur.Main;
import Modele.Hebergement;
import Modele.Option;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;

public class DetailHebergementController {

    @FXML
    private Label nomLabel;

    @FXML
    private Label typeLabel;

    @FXML
    private Label etoilesLabel;

    @FXML
    private Label distanceLabel;

    @FXML
    private Label prixLabel;

    @FXML
    private Label activitesLabel;

    @FXML
    private ListView<String> optionsListView;

    private HebergementControleur hebergementControleur = new HebergementControleur();
    private int hebergementId;

    public void initData(int hebergementId) {
        this.hebergementId = hebergementId;
        loadHebergementDetails();
    }

    private void loadHebergementDetails() {
        Hebergement hebergement = hebergementControleur.getHebergementById(hebergementId);

        if (hebergement != null) {
            // Remplir les informations de l'hébergement
            nomLabel.setText(hebergement.getNom());
            typeLabel.setText(hebergement.getType());
            etoilesLabel.setText(String.valueOf(hebergement.getNbEtoiles()));
            distanceLabel.setText(String.format("%.2f km du centre", hebergement.getDistanceCentre()));
            prixLabel.setText(String.format("%.2f € / nuit", hebergement.getPrixNuit()));
            activitesLabel.setText(hebergement.getActivites());

            // Charger les options de l'hébergement
            List<Option> options = hebergementControleur.getOptionsHebergement(hebergementId);
            if (options != null && !options.isEmpty()) {
                optionsListView.setItems(FXCollections.observableArrayList(
                        options.stream().map(Option::getNom).toList()
                ));
            }
        }
    }

    @FXML
    void handleReserver(ActionEvent event) {
        Main.showReservationView(hebergementId);
    }

    @FXML
    void handleRetour(ActionEvent event) {
        Main.showSearchView();
    }
}
