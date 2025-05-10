package Vue;

import Controleur.HebergementControleur;
import Controleur.Main;
import Controleur.ReservationControleur;
import Controleur.UtilisateurControleur;
import DAO.ReductionDAO;
import DAO.ReductionDAOImpl;
import Modele.Hebergement;
import Modele.Reservation;
import Modele.Utilisateur;
import Modele.Reduction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.List;

import java.math.BigDecimal;
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

    @FXML
    private Label reductionInfoLabel;

    private HebergementControleur hebergementControleur = new HebergementControleur();
    private ReservationControleur reservationControleur = new ReservationControleur();
    private ReductionDAO reductionDAO = new ReductionDAOImpl();
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

            double prixBase = hebergement.getPrixNuit() * nbNuits * nbChambres;
            BigDecimal totalBigDecimal = BigDecimal.valueOf(prixBase);

            // Calculer les réductions automatiques
            Utilisateur utilisateur = UtilisateurControleur.getUtilisateurConnecte();
            BigDecimal reductionTotale = BigDecimal.ZERO;
            StringBuilder detailsReduction = new StringBuilder();

            // Afficher le prix de base
            detailsReduction.append("Prix de base : ").append(String.format("%.2f€", prixBase)).append("\n\n");
            detailsReduction.append("Réductions appliquées :\n");

            if (utilisateur != null) {
                // 1. Réduction anciens clients
                if (estAncienClient(utilisateur.getId())) {
                    BigDecimal reductionAncienClient = totalBigDecimal.multiply(BigDecimal.valueOf(0.10));
                    reductionTotale = reductionTotale.add(reductionAncienClient);
                    detailsReduction.append("• Ancien client (10%) : -")
                            .append(String.format("%.2f€", reductionAncienClient.doubleValue()))
                            .append("\n");
                }

                // 2. Réduction sur montant élevé
                if (totalBigDecimal.compareTo(BigDecimal.valueOf(500)) >= 0) {
                    BigDecimal reductionMontant = totalBigDecimal.multiply(BigDecimal.valueOf(0.05));
                    reductionTotale = reductionTotale.add(reductionMontant);
                    detailsReduction.append("• Réservation > 500€ (5%) : -")
                            .append(String.format("%.2f€", reductionMontant.doubleValue()))
                            .append("\n");
                }

                // 3. Réductions spécifiques
                try {
                    List<Reduction> reductions = reductionDAO.trouverParUtilisateur(utilisateur.getId());
                    for (Reduction r : reductions) {
                        BigDecimal reductionSpecifique = totalBigDecimal.multiply(BigDecimal.valueOf(r.getPourcentage() / 100.0));
                        reductionTotale = reductionTotale.add(reductionSpecifique);
                        detailsReduction.append("• ").append(r.getDescription())
                                .append(" (").append(r.getPourcentage()).append("%) : -")
                                .append(String.format("%.2f€", reductionSpecifique.doubleValue()))
                                .append("\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Limiter à 30% maximum
                BigDecimal maxReduction = totalBigDecimal.multiply(BigDecimal.valueOf(0.30));
                if (reductionTotale.compareTo(maxReduction) > 0) {
                    BigDecimal difference = reductionTotale.subtract(maxReduction);
                    reductionTotale = maxReduction;
                    detailsReduction.append("\n(Réduction limitée à 30% maximum : +")
                            .append(String.format("%.2f€", difference.doubleValue()))
                            .append(" non applicable)");
                }
            }

            // Calculer le prix final
            BigDecimal prixFinal = totalBigDecimal.subtract(reductionTotale);

            // Mettre à jour les labels
            if (reductionTotale.compareTo(BigDecimal.ZERO) > 0) {
                detailsReduction.append("\n").append("-".repeat(30)).append("\n");
                detailsReduction.append("Total des réductions : -")
                        .append(String.format("%.2f€", reductionTotale.doubleValue()))
                        .append("\n");
                detailsReduction.append("Prix final : ")
                        .append(String.format("%.2f€", prixFinal.doubleValue()));

                prixTotalLabel.setText(String.format("%.2f € (réduction de %.2f €)",
                        prixFinal.doubleValue(), reductionTotale.doubleValue()));

                if (reductionInfoLabel != null) {
                    reductionInfoLabel.setText(detailsReduction.toString());
                }
            } else {
                // Pas de réduction
                detailsReduction.append("Aucune réduction applicable\n");
                detailsReduction.append("Prix final : ").append(String.format("%.2f€", prixBase));

                prixTotalLabel.setText(String.format("%.2f €", prixBase));
                if (reductionInfoLabel != null) {
                    reductionInfoLabel.setText(detailsReduction.toString());
                }
            }
        }
    }

    private boolean estAncienClient(int userId) {
        List<Reservation> reservations = reservationControleur.getReservationsClient(userId);
        return !reservations.isEmpty();
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

        // Créer la réservation avec réductions automatiques
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

            // Récupérer la réservation pour l'écran de paiement
            List<Reservation> reservations = reservationControleur.getReservationsClient(utilisateur.getId());
            if (reservations != null && !reservations.isEmpty()) {
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