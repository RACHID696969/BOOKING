// Controleur/ReportingControleur.java
package Controleur;

import DAO.ReservationDAO;
import DAO.ReservationDAOImpl;
import DAO.HebergementDAO;
import DAO.HebergementDAOImpl;
import Modele.Reservation;
import Modele.Hebergement;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.math.BigDecimal;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ReportingControleur {
    private ReservationDAO reservationDAO;
    private HebergementDAO hebergementDAO;

    public ReportingControleur() {
        this.reservationDAO = new ReservationDAOImpl();
        this.hebergementDAO = new HebergementDAOImpl();
    }

    /**
     * Crée un dataset pour un graphique en camembert des réservations par type d'hébergement
     */
    public DefaultPieDataset createReservationParTypeDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        try {
            // Récupérer toutes les réservations
            List<Reservation> reservations = reservationDAO.trouverTout();
            Map<String, Integer> countByType = new HashMap<>();

            // Compter les réservations par type d'hébergement
            for (Reservation r : reservations) {
                Hebergement h = hebergementDAO.trouverParId(r.getHebergementId());
                if (h != null) {
                    String type = h.getType();
                    countByType.put(type, countByType.getOrDefault(type, 0) + 1);
                }
            }

            // Ajouter les données au dataset
            for (Map.Entry<String, Integer> entry : countByType.entrySet()) {
                dataset.setValue(entry.getKey(), entry.getValue());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    /**
     * Crée un dataset pour un graphique en histogramme des revenus mensuels
     */
    public DefaultCategoryDataset createRevenusMensuelsDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            // Récupérer toutes les réservations
            List<Reservation> reservations = reservationDAO.trouverTout();

            // Mappé par année-mois (202401, 202402, etc.)
            Map<String, BigDecimal> revenusByMonth = new TreeMap<>();

            for (Reservation r : reservations) {
                // Convertir SQL Date en LocalDate
                LocalDate dateArrivee = r.getDateArrivee().toLocalDate();

                // Format année-mois comme clé (e.g., "2024-01")
                String yearMonth = dateArrivee.getYear() + "-" +
                        (dateArrivee.getMonthValue() < 10 ? "0" : "") +
                        dateArrivee.getMonthValue();

                // Ajouter le montant total moins la réduction
                BigDecimal montantNet = r.getTotal().subtract(r.getReduction() != null ? r.getReduction() : BigDecimal.ZERO);

                if (revenusByMonth.containsKey(yearMonth)) {
                    revenusByMonth.put(yearMonth, revenusByMonth.get(yearMonth).add(montantNet));
                } else {
                    revenusByMonth.put(yearMonth, montantNet);
                }
            }

            // Ajouter les données au dataset
            for (Map.Entry<String, BigDecimal> entry : revenusByMonth.entrySet()) {
                dataset.addValue(entry.getValue(), "Revenus", entry.getKey());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }

    /**
     * Crée un dataset pour un graphique en histogramme du nombre d'étoiles moyen par type d'hébergement
     */
    public DefaultCategoryDataset createEtoilesParTypeDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try {
            // Récupérer tous les hébergements
            List<Hebergement> hebergements = hebergementDAO.trouverTout();

            // Mappé par type
            Map<String, List<Integer>> etoilesByType = new HashMap<>();

            for (Hebergement h : hebergements) {
                String type = h.getType();
                if (!etoilesByType.containsKey(type)) {
                    etoilesByType.put(type, new ArrayList<>());
                }
                etoilesByType.get(type).add(h.getNbEtoiles());
            }

            // Calculer la moyenne d'étoiles par type
            for (Map.Entry<String, List<Integer>> entry : etoilesByType.entrySet()) {
                double moyenne = entry.getValue().stream().mapToInt(Integer::intValue).average().orElse(0);
                dataset.addValue(moyenne, "Étoiles moyennes", entry.getKey());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dataset;
    }
}