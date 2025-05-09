package DAO;

import Modele.Reservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class ReservationDAOImpl implements ReservationDAO {

    @Override
    public Reservation trouverParId(int id) throws SQLException {
        Reservation reservation = null;
        String query = "SELECT * FROM Reservation WHERE id = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                reservation = new Reservation();
                reservation.setId(rs.getInt("id"));
                reservation.setClientId(rs.getInt("clientId"));
                reservation.setHebergementId(rs.getInt("hebergementId"));
                reservation.setDateArrivee(rs.getDate("dateArrivee"));
                reservation.setDateDepart(rs.getDate("dateDepart"));
                reservation.setNbAdultes(rs.getInt("nbAdultes"));
                reservation.setNbEnfants(rs.getInt("nbEnfants"));
                reservation.setNbChambres(rs.getInt("nbChambres"));
                reservation.setTotal(rs.getBigDecimal("total"));
                reservation.setReduction(rs.getBigDecimal("reduction"));
            }
        }

        return reservation;
    }

    @Override
    public List<Reservation> trouverTout() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM Reservation";

        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(rs.getInt("id"));
                reservation.setClientId(rs.getInt("clientId"));
                reservation.setHebergementId(rs.getInt("hebergementId"));
                reservation.setDateArrivee(rs.getDate("dateArrivee"));
                reservation.setDateDepart(rs.getDate("dateDepart"));
                reservation.setNbAdultes(rs.getInt("nbAdultes"));
                reservation.setNbEnfants(rs.getInt("nbEnfants"));
                reservation.setNbChambres(rs.getInt("nbChambres"));
                reservation.setTotal(rs.getBigDecimal("total"));
                reservation.setReduction(rs.getBigDecimal("reduction"));

                reservations.add(reservation);
            }
        }

        return reservations;
    }

    @Override
    public void creer(Reservation r) throws SQLException {
        String query = "INSERT INTO Reservation (clientId, hebergementId, dateArrivee, dateDepart, "
                + "nbAdultes, nbEnfants, nbChambres, total, reduction) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, r.getClientId());
            ps.setInt(2, r.getHebergementId());
            ps.setDate(3, r.getDateArrivee());
            ps.setDate(4, r.getDateDepart());
            ps.setInt(5, r.getNbAdultes());
            ps.setInt(6, r.getNbEnfants());
            ps.setInt(7, r.getNbChambres());
            ps.setBigDecimal(8, r.getTotal());
            ps.setBigDecimal(9, r.getReduction());

            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                r.setId(generatedKeys.getInt(1));
            }
        }
    }

    @Override
    public void mettreAJour(Reservation r) throws SQLException {
        String query = "UPDATE Reservation SET clientId = ?, hebergementId = ?, dateArrivee = ?, "
                + "dateDepart = ?, nbAdultes = ?, nbEnfants = ?, nbChambres = ?, total = ?, "
                + "reduction = ? WHERE id = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, r.getClientId());
            ps.setInt(2, r.getHebergementId());
            ps.setDate(3, r.getDateArrivee());
            ps.setDate(4, r.getDateDepart());
            ps.setInt(5, r.getNbAdultes());
            ps.setInt(6, r.getNbEnfants());
            ps.setInt(7, r.getNbChambres());
            ps.setBigDecimal(8, r.getTotal());
            ps.setBigDecimal(9, r.getReduction());
            ps.setInt(10, r.getId());

            ps.executeUpdate();
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {
        String query = "DELETE FROM Reservation WHERE id = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }
}