package Vue;

import Controleur.HebergementControleur;
import Controleur.Main;
import Modele.Hebergement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RechercheHebergementController implements Initializable {

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private Slider etoilesSlider;

    @FXML
    private TextField prixMaxField;

    @FXML
    private TextField distanceMaxField;

    @FXML
    private TableView<Hebergement> hebergementTable;

    @FXML
    private TableColumn<Hebergement, String> nomColumn;

    @FXML
    private TableColumn<Hebergement, String> typeColumn;

    @FXML
    private TableColumn<Hebergement, Integer> etoilesColumn;

    @FXML
    private TableColumn<Hebergement, Double> distanceColumn;

    @FXML
    private TableColumn<Hebergement, Double> prixColumn;

    private HebergementControleur hebergementControleur = new HebergementControleur();
    private ObservableList<Hebergement> hebergementsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialiser les colonnes de la table
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        etoilesColumn.setCellValueFactory(new PropertyValueFactory<>("nbEtoiles"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distanceCentre"));
        prixColumn.setCellValueFactory(new PropertyValueFactory<>("prixNuit"));

        // Charger tous les hébergements
        hebergementsList.addAll(hebergementControleur.getAllHebergements());
        hebergementTable.setItems(hebergementsList);

        // Initialiser le ComboBox des types d'hébergement
        List<String> typesHebergement = hebergementControleur.getTypesHebergement();
        typesHebergement.add(0, "Tous types");
        typeComboBox.setItems(FXCollections.observableArrayList(typesHebergement));
        typeComboBox.getSelectionModel().selectFirst();

        // Initialiser le slider des étoiles
        etoilesSlider.setMin(0);
        etoilesSlider.setMax(5);
        etoilesSlider.setValue(0);
        etoilesSlider.setShowTickLabels(true);
        etoilesSlider.setShowTickMarks(true);
        etoilesSlider.setMajorTickUnit(1);
        etoilesSlider.setMinorTickCount(0);
        etoilesSlider.setSnapToTicks(true);

        // Ajouter un gestionnaire d'événements pour la sélection d'un hébergement
        hebergementTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-clic
                Hebergement selectedHebergement = hebergementTable.getSelectionModel().getSelectedItem();
                if (selectedHebergement != null) {
                    Main.showHebergementDetailView(selectedHebergement.getId());
                }
            }
        });
    }

    @FXML
    void handleRecherche(ActionEvent event) {
        String type = typeComboBox.getValue();
        if ("Tous types".equals(type)) {
            type = null; // Aucun filtre de type
        }

        int etoilesMin = (int) etoilesSlider.getValue();

        double prixMax = 0;
        if (!prixMaxField.getText().isEmpty()) {
            try {
                prixMax = Double.parseDouble(prixMaxField.getText());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le prix maximum doit être un nombre.");
                return;
            }
        }

        double distanceMax = 0;
        if (!distanceMaxField.getText().isEmpty()) {
            try {
                distanceMax = Double.parseDouble(distanceMaxField.getText());
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "La distance maximale doit être un nombre.");
                return;
            }
        }

        // Effectuer la recherche
        List<Hebergement> resultats = hebergementControleur.rechercherHebergements(type, etoilesMin, prixMax, distanceMax);

        // Mettre à jour la table
        hebergementsList.clear();
        hebergementsList.addAll(resultats);
    }

    @FXML
    void handleRetour(ActionEvent event) {
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