// Controleur/ReservationControleur.java
package Controleur;

import DAO.ReservationDAO;
import DAO.ReservationDAOImpl;
import DAO.HebergementDAO;
import DAO.HebergementDAOImpl;
import DAO.ReductionDAO;
import DAO.ReductionDAOImpl;
import Modele.Reservation;
import Modele.Hebergement;
import Modele.Reduction;
import Modele.Utilisateur;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ArrayList;

public class ReservationControleur {
    private ReservationDAO reservationDAO;
    private HebergementDAO hebergementDAO;
    private ReductionDAO reductionDAO;

    public ReservationControleur() {
        this.reservationDAO = new ReservationDAOImpl();
        this.hebergementDAO = new HebergementDAOImpl();
        this.reductionDAO = new ReductionDAOImpl();
    }

    public boolean creerReservation(int clientId, int hebergementId, LocalDate dateArrivee, LocalDate dateDepart,
                                    int nbAdultes, int nbEnfants, int nbChambres) {
        try {
            // Calculer le prix total
            Hebergement hebergement = hebergementDAO.trouverParId(hebergementId);
            if (hebergement == null) {
                return false; // Hébergement non trouvé
            }

            // Calculer le nombre de nuits
            long nbNuits = ChronoUnit.DAYS.between(dateArrivee, dateDepart);
            if (nbNuits <= 0) {
                return false; // Dates invalides
            }

            // Calculer le prix total
            BigDecimal prixTotal = BigDecimal.valueOf(hebergement.getPrixNuit() * nbNuits * nbChambres);

            // Vérifier si l'utilisateur a droit à une réduction
            Utilisateur utilisateur = UtilisateurControleur.getUtilisateurConnecte();
            BigDecimal reduction = BigDecimal.ZERO;

            if (utilisateur != null) {
                List<Reduction> reductions = reductionDAO.trouverParUtilisateur(utilisateur.getId());
                if (reductions != null && !reductions.isEmpty()) {
                    // Prendre la première réduction disponible (ou on pourrait prendre la plus avantageuse)
                    Reduction reductionAppliquee = reductions.get(0);
                    reduction = prixTotal.multiply(BigDecimal.valueOf(reductionAppliquee.getPourcentage() / 100.0));
                }
            }

            // Créer la réservation
            Reservation reservation = new Reservation(
                    clientId,
                    hebergementId,
                    Date.valueOf(dateArrivee),
                    Date.valueOf(dateDepart),
                    nbAdultes,
                    nbEnfants,
                    nbChambres,
                    prixTotal,
                    reduction
            );

            reservationDAO.creer(reservation);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reservation> getReservationsClient(int clientId) {
        try {
            List<Reservation> toutesReservations = reservationDAO.trouverTout();
            List<Reservation> reservationsClient = new ArrayList<>();

            for (Reservation r : toutesReservations) {
                if (r.getClientId() == clientId) {
                    reservationsClient.add(r);
                }
            }

            return reservationsClient;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Reservation getReservationById(int id) {
        try {
            return reservationDAO.trouverParId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean annulerReservation(int reservationId) {
        try {
            reservationDAO.supprimer(reservationId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reservation> getAllReservations() {
        try {
            return reservationDAO.trouverTout();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}