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
import java.util.Optional;

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
        System.out.println("Bouton retour cliqué - AdminPanel");
        try {
            Main.showMainView();
            System.out.println("showMainView() appelé avec succès");
        } catch (Exception e) {
            System.err.println("Erreur lors du retour: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de retourner à l'accueil: " + e.getMessage());
        }
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

            // Vérifications
            if (nom.isEmpty() || type.isEmpty() || activites.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs texte");
                return;
            }

            // Générer un nouvel ID
            int newId = generateNewHebergementId();

            Hebergement hebergement = new Hebergement(newId, nom, type, etoiles, distance, activites, prix);

            if (hebergementControleur.ajouterHebergement(hebergement)) {
                clearHebergementFields();
                loadData();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Hébergement ajouté avec succès");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter l'hébergement");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez vérifier les données saisies (nombres uniquement pour étoiles, distance et prix)");
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
            // Remplir les champs avec les données actuelles
            nomHebergementField.setText(selected.getNom());
            typeHebergementField.setText(selected.getType());
            etoilesHebergementField.setText(String.valueOf(selected.getNbEtoiles()));
            distanceHebergementField.setText(String.valueOf(selected.getDistanceCentre()));
            activitesHebergementField.setText(selected.getActivites());
            prixHebergementField.setText(String.valueOf(selected.getPrixNuit()));

            showAlert(Alert.AlertType.INFORMATION, "Info", "Les champs ont été remplis. Modifiez et ajoutez pour enregistrer.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un hébergement à modifier");
        }
    }

    @FXML
    void handleSupprimerHebergement(ActionEvent event) {
        Hebergement selected = hebergementsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Suppression d'hébergement");
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cet hébergement ?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (hebergementControleur.supprimerHebergement(selected.getId())) {
                    hebergementsTable.getItems().remove(selected);
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Hébergement supprimé avec succès");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer l'hébergement");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner un hébergement à supprimer");
        }
    }

    @FXML
    void handleSupprimerReservation(ActionEvent event) {
        Reservation selected = reservationsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Suppression de réservation");
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cette réservation ?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (reservationControleur.annulerReservation(selected.getId())) {
                    reservationsTable.getItems().remove(selected);
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Réservation supprimée avec succès");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer la réservation");
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner une réservation à supprimer");
        }
    }

    @FXML
    void handleAjouterReduction(ActionEvent event) {
        try {
            String description = descriptionReductionField.getText();
            String pourcentageStr = pourcentageReductionField.getText();

            if (description.isEmpty() || pourcentageStr.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs");
                return;
            }

            double pourcentage = Double.parseDouble(pourcentageStr);

            if (pourcentage <= 0 || pourcentage > 100) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Le pourcentage doit être entre 1 et 100");
                return;
            }

            // Générer un nouvel ID
            int newId = generateNewReductionId();

            Reduction reduction = new Reduction(newId, description, pourcentage);

            if (reductionDAO.ajouter(reduction)) {
                clearReductionFields();
                loadData();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Réduction ajoutée avec succès");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'ajouter la réduction");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de base de données: " + e.getMessage());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le pourcentage doit être un nombre");
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
            // Remplir les champs avec les données actuelles
            descriptionReductionField.setText(selected.getDescription());
            pourcentageReductionField.setText(String.valueOf(selected.getPourcentage()));

            showAlert(Alert.AlertType.INFORMATION, "Info", "Les champs ont été remplis. Modifiez et ajoutez pour enregistrer.");
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner une réduction à modifier");
        }
    }

    @FXML
    void handleSupprimerReduction(ActionEvent event) {
        Reduction selected = reductionsTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Suppression de réduction");
            confirmation.setContentText("Êtes-vous sûr de vouloir supprimer cette réduction ?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    if (reductionDAO.supprimer(selected.getId())) {
                        reductionsTable.getItems().remove(selected);
                        showAlert(Alert.AlertType.INFORMATION, "Succès", "Réduction supprimée avec succès");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible de supprimer la réduction");
                    }
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur de base de données: " + e.getMessage());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner une réduction à supprimer");
        }
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