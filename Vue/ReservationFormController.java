package Vue;

import Controleur.HebergementControleur;
import Controleur.Main;
import Controleur.ReservationControleur;
import Controleur.UtilisateurControleur;
import Modele.Hebergement;
import Modele.Reservation;
import Modele.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class ReservationFormController {

    @FXML
    private Label nomHebergementLabel;

    @FXML
    private DatePicker dateArriveePicker;

    @FXML
    private DatePicker dateDepartPicker;

    @FXML
    private Spinner<Integer> adulteSpinner;

    @FXML
    private Spinner<Integer> enfantSpinner;

    @FXML
    private Spinner<Integer> chambreSpinner;

    @FXML
    private Label prixTotalLabel;

    private HebergementControleur hebergementControleur = new HebergementControleur();
    private ReservationControleur reservationControleur = new ReservationControleur();
    private int hebergementId;
    private Hebergement hebergement;

    public void initData(int hebergementId) {
        this.hebergementId = hebergementId;
        this.hebergement = hebergementControleur.getHebergementById(hebergementId);

        if (hebergement != null) {
            nomHebergementLabel.setText(hebergement.getNom());
        }

        // Initialiser les spinners
        SpinnerValueFactory<Integer> adulteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        adulteSpinner.setValueFactory(adulteValueFactory);

        SpinnerValueFactory<Integer> enfantValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 0);
        enfantSpinner.setValueFactory(enfantValueFactory);

        SpinnerValueFactory<Integer> chambreValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 1);
        chambreSpinner.setValueFactory(chambreValueFactory);

        // Initialiser les date pickers
        dateArriveePicker.setValue(LocalDate.now());
        dateDepartPicker.setValue(LocalDate.now().plusDays(1));

        // Ajouter des écouteurs pour mettre à jour le prix total
        dateArriveePicker.valueProperty().addListener((obs, oldVal, newVal) -> updatePrixTotal());
        dateDepartPicker.valueProperty().addListener((obs, oldVal, newVal) -> updatePrixTotal());
        adulteSpinner.valueProperty().addListener((obs, oldVal, newVal) -> updatePrixTotal());
        enfantSpinner.valueProperty().addListener((obs, oldVal, newVal) -> updatePrixTotal());
        chambreSpinner.valueProperty().addListener((obs, oldVal, newVal) -> updatePrixTotal());

        // Calculer le prix initial
        updatePrixTotal();
    }

    private void updatePrixTotal() {
        if (hebergement == null || dateArriveePicker.getValue() == null || dateDepartPicker.getValue() == null) {
            return;
        }

        LocalDate dateArrivee = dateArriveePicker.getValue();
        LocalDate dateDepart = dateDepartPicker.getValue();

        if (dateDepart.isAfter(dateArrivee)) {
            long nbNuits = java.time.temporal.ChronoUnit.DAYS.between(dateArrivee, dateDepart);
            int nbChambres = chambreSpinner.getValue();

            double prixTotal = hebergement.getPrixNuit() * nbNuits * nbChambres;

            // Vérifier si l'utilisateur a droit à une réduction
            Utilisateur utilisateur = UtilisateurControleur.getUtilisateurConnecte();
            if (utilisateur != null) {
                UtilisateurControleur utilisateurControleur = new UtilisateurControleur();
                if (utilisateurControleur.estAncienClient(utilisateur.getId())) {
                    // Appliquer une réduction de 10% pour les anciens clients (exemple)
                    prixTotal = prixTotal * 0.9;
                    prixTotalLabel.setText(String.format("%.2f € (réduction de 10%% appliquée)", prixTotal));
                } else {
                    prixTotalLabel.setText(String.format("%.2f €", prixTotal));
                }
            } else {
                prixTotalLabel.setText(String.format("%.2f €", prixTotal));
            }
        }
    }

    @FXML
    void handleReserver(ActionEvent event) {
        Utilisateur utilisateur = UtilisateurControleur.getUtilisateurConnecte();

        if (utilisateur == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur de réservation", "Vous devez être connecté pour effectuer une réservation.");
            Main.showLoginView();
            return;
        }

        LocalDate dateArrivee = dateArriveePicker.getValue();
        LocalDate dateDepart = dateDepartPicker.getValue();

        if (dateDepart.isBefore(dateArrivee) || dateDepart.isEqual(dateArrivee)) {
            showAlert(Alert.AlertType.ERROR, "Erreur de réservation", "La date de départ doit être après la date d'arrivée.");
            return;
        }

        int nbAdultes = adulteSpinner.getValue();
        int nbEnfants = enfantSpinner.getValue();
        int nbChambres = chambreSpinner.getValue();

        // Créer la réservation
        boolean success = reservationControleur.creerReservation(
                utilisateur.getId(),
                hebergementId,
                dateArrivee,
                dateDepart,
                nbAdultes,
                nbEnfants,
                nbChambres
        );

        if (success) {
            showAlert(Alert.AlertType.INFORMATION, "Réservation réussie", "Votre réservation a été enregistrée avec succès.");

            // Récupérer la réservation qui vient d'être créée pour l'écran de paiement
            List<Reservation> reservations = reservationControleur.getReservationsClient(utilisateur.getId());
            if (reservations != null && !reservations.isEmpty()) {
                // On suppose que la dernière réservation est celle qu'on vient de créer
                Reservation reservation = reservations.get(reservations.size() - 1);
                Main.showPaiementView(reservation);
            } else {
                Main.showMainView();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur de réservation", "Une erreur s'est produite lors de la réservation. Veuillez réessayer.");
        }
    }

    @FXML
    void handleAnnuler(ActionEvent event) {
        Main.showHebergementDetailView(hebergementId);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}