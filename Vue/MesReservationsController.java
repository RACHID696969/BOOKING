package Vue;

import Controleur.Main;
import Controleur.ReservationControleur;
import Controleur.UtilisateurControleur;
import Controleur.HebergementControleur;
import Modele.Reservation;
import Modele.Utilisateur;
import Modele.Hebergement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class MesReservationsController implements Initializable {

    @FXML
    private TableView<ReservationTableItem> reservationsTable;
    @FXML
    private TableColumn<ReservationTableItem, Integer> idColumn;
    @FXML
    private TableColumn<ReservationTableItem, String> hebergementColumn;
    @FXML
    private TableColumn<ReservationTableItem, Date> arriveeColumn;
    @FXML
    private TableColumn<ReservationTableItem, Date> departColumn;
    @FXML
    private TableColumn<ReservationTableItem, Integer> adultesColumn;
    @FXML
    private TableColumn<ReservationTableItem, Integer> enfantsColumn;
    @FXML
    private TableColumn<ReservationTableItem, Integer> chambresColumn;
    @FXML
    private TableColumn<ReservationTableItem, BigDecimal> totalColumn;
    @FXML
    private TableColumn<ReservationTableItem, String> statusColumn;
    @FXML
    private Label messageLabel;

    private ReservationControleur reservationControleur = new ReservationControleur();
    private HebergementControleur hebergementControleur = new HebergementControleur();
    private ObservableList<ReservationTableItem> reservationsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
        loadReservations();
    }

    private void initializeTable() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        hebergementColumn.setCellValueFactory(new PropertyValueFactory<>("hebergementNom"));
        arriveeColumn.setCellValueFactory(new PropertyValueFactory<>("dateArrivee"));
        departColumn.setCellValueFactory(new PropertyValueFactory<>("dateDepart"));
        adultesColumn.setCellValueFactory(new PropertyValueFactory<>("nbAdultes"));
        enfantsColumn.setCellValueFactory(new PropertyValueFactory<>("nbEnfants"));
        chambresColumn.setCellValueFactory(new PropertyValueFactory<>("nbChambres"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        reservationsTable.setItems(reservationsList);
    }

    private void loadReservations() {
        Utilisateur utilisateur = UtilisateurControleur.getUtilisateurConnecte();

        if (utilisateur == null) {
            Main.showLoginView();
            return;
        }

        reservationsList.clear();
        var reservations = reservationControleur.getReservationsClient(utilisateur.getId());

        if (reservations.isEmpty()) {
            messageLabel.setText("Vous n'avez aucune réservation.");
        } else {
            messageLabel.setText("");
            for (Reservation reservation : reservations) {
                // Récupérer le nom de l'hébergement
                Hebergement hebergement = hebergementControleur.getHebergementById(reservation.getHebergementId());
                String hebergementNom = hebergement != null ? hebergement.getNom() : "N/A";

                // Déterminer le statut
                String status = "Active"; // Pour simplifier, on met toutes les réservations comme actives

                ReservationTableItem item = new ReservationTableItem(
                        reservation.getId(),
                        hebergementNom,
                        reservation.getDateArrivee(),
                        reservation.getDateDepart(),
                        reservation.getNbAdultes(),
                        reservation.getNbEnfants(),
                        reservation.getNbChambres(),
                        reservation.getTotal(),
                        status
                );

                reservationsList.add(item);
            }
        }
    }

    @FXML
    void handleRetour(ActionEvent event) {
        Main.showMainView();
    }

    @FXML
    void handleAnnulerReservation(ActionEvent event) {
        ReservationTableItem selectedItem = reservationsTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner une réservation à annuler.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation");
        confirmation.setHeaderText("Annuler la réservation");
        confirmation.setContentText("Êtes-vous sûr de vouloir annuler cette réservation ?");

        if (confirmation.showAndWait().isPresent() && confirmation.getResult() == ButtonType.OK) {
            boolean success = reservationControleur.annulerReservation(selectedItem.getId());

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Succès", "La réservation a été annulée avec succès.");
                loadReservations(); // Recharger la liste
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Impossible d'annuler la réservation.");
            }
        }
    }

    @FXML
    void handleModifierReservation(ActionEvent event) {
        ReservationTableItem selectedItem = reservationsTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection requise", "Veuillez sélectionner une réservation à modifier.");
            return;
        }

        // Pour l'instant, on affiche juste un message
        showAlert(Alert.AlertType.INFORMATION, "En cours de développement",
                "La modification des réservations sera disponible prochainement.");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Classe pour représenter une ligne dans la table
    public static class ReservationTableItem {
        private final int id;
        private final String hebergementNom;
        private final Date dateArrivee;
        private final Date dateDepart;
        private final int nbAdultes;
        private final int nbEnfants;
        private final int nbChambres;
        private final BigDecimal total;
        private final String status;

        public ReservationTableItem(int id, String hebergementNom, Date dateArrivee, Date dateDepart,
                                    int nbAdultes, int nbEnfants, int nbChambres, BigDecimal total, String status) {
            this.id = id;
            this.hebergementNom = hebergementNom;
            this.dateArrivee = dateArrivee;
            this.dateDepart = dateDepart;
            this.nbAdultes = nbAdultes;
            this.nbEnfants = nbEnfants;
            this.nbChambres = nbChambres;
            this.total = total;
            this.status = status;
        }

        // Getters
        public int getId() { return id; }
        public String getHebergementNom() { return hebergementNom; }
        public Date getDateArrivee() { return dateArrivee; }
        public Date getDateDepart() { return dateDepart; }
        public int getNbAdultes() { return nbAdultes; }
        public int getNbEnfants() { return nbEnfants; }
        public int getNbChambres() { return nbChambres; }
        public BigDecimal getTotal() { return total; }
        public String getStatus() { return status; }
    }
}