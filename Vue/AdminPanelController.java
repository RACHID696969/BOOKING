// Vue/AdminPanelController.java
package Vue;

import Controleur.HebergementControleur;
import Controleur.Main;
import DAO.ReductionDAO;
import DAO.ReductionDAOImpl;
import Controleur.ReservationControleur;
import Controleur.UtilisateurControleur;
import Modele.Hebergement;
import Modele.Reduction;
import Modele.Reservation;
import Modele.Utilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.List;

public class AdminPanelController implements Initializable {

    // Onglet Utilisateurs
    @FXML
    private TableView<Utilisateur> utilisateursTable;
    @FXML
    private TableColumn<Utilisateur, Integer> idUtilisateurColumn;
    @FXML
    private TableColumn<Utilisateur, String> nomUtilisateurColumn;
    @FXML
    private TableColumn<Utilisateur, String> prenomUtilisateurColumn;
    @FXML
    private TableColumn<Utilisateur, String> emailUtilisateurColumn;
    @FXML
    private TableColumn<Utilisateur, String> roleUtilisateurColumn;

    // Onglet Hébergements
    @FXML
    private TableView<Hebergement> hebergementsTable;
    @FXML
    private TableColumn<Hebergement, Integer> idHebergementColumn;
    @FXML
    private TableColumn<Hebergement, String> nomHebergementColumn;
    @FXML
    private TableColumn<Hebergement, String> typeHebergementColumn;
    @FXML
    private TableColumn<Hebergement, Integer> etoilesHebergementColumn;
    @FXML
    private TableColumn<Hebergement, Double> distanceHebergementColumn;
    @FXML
    private TableColumn<Hebergement, Double> prixHebergementColumn;

    @FXML
    private TextField nomHebergementField;
    @FXML
    private TextField typeHebergementField;
    @FXML
    private TextField etoilesHebergementField;
    @FXML
    private TextField distanceHebergementField;
    @FXML
    private TextField activitesHebergementField;
    @FXML
    private TextField prixHebergementField;

    // Onglet Réservations
    @FXML
    private TableView<Reservation> reservationsTable;
    @FXML
    private TableColumn<Reservation, Integer> idReservationColumn;
    @FXML
    private TableColumn<Reservation, Integer> clientReservationColumn;
    @FXML
    private TableColumn<Reservation, Integer> hebergementReservationColumn;
    @FXML
    private TableColumn<Reservation, java.sql.Date> arriveeReservationColumn;
    @FXML
    private TableColumn<Reservation, java.sql.Date> departReservationColumn;
    @FXML
    private TableColumn<Reservation, Integer> adultesReservationColumn;
    @FXML
    private TableColumn<Reservation, Integer> enfantsReservationColumn;
    @FXML
    private TableColumn<Reservation, Integer> chambresReservationColumn;
    @FXML
    private TableColumn<Reservation, java.math.BigDecimal> totalReservationColumn;

    // Onglet Réductions
    @FXML
    private TableView<Reduction> reductionsTable;
    @FXML
    private TableColumn<Reduction, Integer> idReductionColumn;
    @FXML
    private TableColumn<Reduction, String> descriptionReductionColumn;
    @FXML
    private TableColumn<Reduction, Double> pourcentageReductionColumn;

    @FXML
    private TextField descriptionReductionField;
    @FXML
    private TextField pourcentageReductionField;

    private UtilisateurControleur utilisateurControleur = new UtilisateurControleur();
    private HebergementControleur hebergementControleur = new HebergementControleur();
    private ReservationControleur reservationControleur = new ReservationControleur();
    private ReductionDAO reductionDAO = new ReductionDAOImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeUserTable();
        initializeHebergementTable();
        initializeReservationTable();
        initializeReductionTable();

        loadData();
    }

    private void initializeUserTable() {
        idUtilisateurColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomUtilisateurColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomUtilisateurColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailUtilisateurColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleUtilisateurColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    }

    private void initializeHebergementTable() {
        idHebergementColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomHebergementColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeHebergementColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        etoilesHebergementColumn.setCellValueFactory(new PropertyValueFactory<>("nbEtoiles"));
        distanceHebergementColumn.setCellValueFactory(new PropertyValueFactory<>("distanceCentre"));
        prixHebergementColumn.setCellValueFactory(new PropertyValueFactory<>("prixNuit"));
    }

    private void initializeReservationTable() {
        idReservationColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        clientReservationColumn.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        hebergementReservationColumn.setCellValueFactory(new PropertyValueFactory<>("hebergementId"));
        arriveeReservationColumn.setCellValueFactory(new PropertyValueFactory<>("dateArrivee"));
        departReservationColumn.setCellValueFactory(new PropertyValueFactory<>("dateDepart"));
        adultesReservationColumn.setCellValueFactory(new PropertyValueFactory<>("nbAdultes"));
        enfantsReservationColumn.setCellValueFactory(new PropertyValueFactory<>("nbEnfants"));
        chambresReservationColumn.setCellValueFactory(new PropertyValueFactory<>("nbChambres"));
        totalReservationColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private void initializeReductionTable() {
        idReductionColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        descriptionReductionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        pourcentageReductionColumn.setCellValueFactory(new PropertyValueFactory<>("pourcentage"));
    }

    private void loadData() {
        // Charger les utilisateurs
        utilisateursTable.setItems(FXCollections.observableArrayList(utilisateurControleur.getAllUtilisateurs()));

        // Charger les hébergements
        hebergementsTable.setItems(FXCollections.observableArrayList(hebergementControleur.getAllHebergements()));

        // Charger les réservations
        reservationsTable.setItems(FXCollections.observableArrayList(reservationControleur.getAllReservations()));

        // Charger les réductions
        try {
            reductionsTable.setItems(FXCollections.observableArrayList(reductionDAO.trouverTout()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleRetour(ActionEvent event) {
        Main.showMainView();
    }

    @FXML
    void handleModifierUtilisateur(ActionEvent event) {
        Utilisateur selected = utilisateursTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Implémenter la modification d'utilisateur
            showAlert(Alert.AlertType.INFORMATION, "Info", "Modification d'utilisateur - À implémenter");
        }
    }

    @FXML
    void handleSupprimerUtilisateur(ActionEvent event) {
        Utilisateur selected = utilisateursTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (utilisateurControleur.deleteUtilisateur(selected.getId())) {
                utilisateursTable.getItems().remove(selected);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Utilisateur supprimé avec succès");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer l'utilisateur");
            }
        }
    }

    @FXML
    void handleAjouterHebergement(ActionEvent event) {
        try {
            String nom = nomHebergementField.getText();
            String type = typeHebergementField.getText();
            int etoiles = Integer.parseInt(etoilesHebergementField.getText());
            double distance = Double.parseDouble(distanceHebergementField.getText());
            String activites = activitesHebergementField.getText();
            double prix = Double.parseDouble(prixHebergementField.getText());

            // Générer un nouvel ID
            int newId = generateNewHebergementId();

            Hebergement hebergement = new Hebergement();
            hebergement.setId(newId);  // <-- ID généré automatiquement
            hebergement.setNom(nom);
            hebergement.setType(type);
            hebergement.setNbEtoiles(etoiles);
            hebergement.setDistanceCentre(distance);
            hebergement.setActivites(activites);
            hebergement.setPrixNuit(prix);

            if (hebergementControleur.ajouterHebergement(hebergement)) {
                clearHebergementFields();
                loadData();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Hébergement ajouté avec succès");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter l'hébergement");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez vérifier les données saisies");
        }
    }
    private int generateNewHebergementId() {
        List<Hebergement> hebergements = hebergementControleur.getAllHebergements();
        int maxId = 0;
        for (Hebergement h : hebergements) {
            if (h.getId() > maxId) {
                maxId = h.getId();
            }
        }
        return maxId + 1;
    }


    @FXML
    void handleModifierHebergement(ActionEvent event) {
        Hebergement selected = hebergementsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Implémenter la modification d'hébergement
            showAlert(Alert.AlertType.INFORMATION, "Info", "Modification d'hébergement - À implémenter");
        }
    }

    @FXML
    void handleSupprimerHebergement(ActionEvent event) {
        Hebergement selected = hebergementsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (hebergementControleur.supprimerHebergement(selected.getId())) {
                hebergementsTable.getItems().remove(selected);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Hébergement supprimé avec succès");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer l'hébergement");
            }
        }
    }

    @FXML
    void handleSupprimerReservation(ActionEvent event) {
        Reservation selected = reservationsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            if (reservationControleur.annulerReservation(selected.getId())) {
                reservationsTable.getItems().remove(selected);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Réservation supprimée avec succès");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer la réservation");
            }
        }
    }

    @FXML
    void handleAjouterReduction(ActionEvent event) {
        try {
            String description = descriptionReductionField.getText();
            double pourcentage = Double.parseDouble(pourcentageReductionField.getText());

            // Générer un nouvel ID
            int newId = generateNewReductionId();

            Reduction reduction = new Reduction(newId, description, pourcentage);  // <-- ID généré automatiquement

            if (reductionDAO.ajouter(reduction)) {
                clearReductionFields();
                loadData();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Réduction ajoutée avec succès");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter la réduction");
            }
        } catch (SQLException | NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout de la réduction");
        }
    }
    private int generateNewReductionId() {
        try {
            List<Reduction> reductions = reductionDAO.trouverTout();
            int maxId = 0;
            for (Reduction r : reductions) {
                if (r.getId() > maxId) {
                    maxId = r.getId();
                }
            }
            return maxId + 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 1; // ID par défaut en cas d'erreur
        }
    }

    @FXML
    void handleModifierReduction(ActionEvent event) {
        Reduction selected = reductionsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Implémenter la modification de réduction
            showAlert(Alert.AlertType.INFORMATION, "Info", "Modification de réduction - À implémenter");
        }
    }

    @FXML
    void handleSupprimerReduction(ActionEvent event) {
        Reduction selected = reductionsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                if (reductionDAO.supprimer(selected.getId())) {
                    reductionsTable.getItems().remove(selected);
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Réduction supprimée avec succès");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer la réduction");
                }
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression");
            }
        }
    }

    @FXML
    void handleReporting(ActionEvent event) {
        Main.showReportingView();
    }

    private void clearHebergementFields() {
        nomHebergementField.clear();
        typeHebergementField.clear();
        etoilesHebergementField.clear();
        distanceHebergementField.clear();
        activitesHebergementField.clear();
        prixHebergementField.clear();
    }

    private void clearReductionFields() {
        descriptionReductionField.clear();
        pourcentageReductionField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}