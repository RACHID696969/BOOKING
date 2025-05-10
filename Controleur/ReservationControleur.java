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

            // Appliquer automatiquement les réductions
            BigDecimal reduction = calculerReductionAutomatique(clientId, prixTotal);

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

    private BigDecimal calculerReductionAutomatique(int clientId, BigDecimal prixTotal) {
        BigDecimal totalReduction = BigDecimal.ZERO;

        try {
            // 1. Vérifier si c'est un ancien client
            if (estAncienClient(clientId)) {
                // 10% de réduction pour les anciens clients
                totalReduction = prixTotal.multiply(BigDecimal.valueOf(0.10));
            }

            // 2. Réduction basée sur le montant de la réservation
            if (prixTotal.compareTo(BigDecimal.valueOf(500)) >= 0) {
                // 5% de réduction supplémentaire pour les réservations de plus de 500€
                totalReduction = totalReduction.add(prixTotal.multiply(BigDecimal.valueOf(0.05)));
            }

            // 3. Vérifier les réductions spécifiques assignées à l'utilisateur
            List<Reduction> reductionsUtilisateur = reductionDAO.trouverParUtilisateur(clientId);
            for (Reduction r : reductionsUtilisateur) {
                totalReduction = totalReduction.add(
                        prixTotal.multiply(BigDecimal.valueOf(r.getPourcentage() / 100.0))
                );
            }

            // Limiter la réduction maximum à 30% du prix total
            BigDecimal maxReduction = prixTotal.multiply(BigDecimal.valueOf(0.30));
            if (totalReduction.compareTo(maxReduction) > 0) {
                totalReduction = maxReduction;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalReduction;
    }

    private boolean estAncienClient(int clientId) {
        try {
            // Un client est considéré comme ancien s'il a déjà fait au moins une réservation
            List<Reservation> reservations = getReservationsClient(clientId);
            return !reservations.isEmpty();
        } catch (Exception e) {
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