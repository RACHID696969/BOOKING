package DAO;

import Modele.Hebergement;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HebergementDAOImpl implements HebergementDAO {

    @Override
    public Hebergement trouverParId(int id) throws SQLException {
        Hebergement hebergement = null;
        String query = "SELECT * FROM Hebergement WHERE id = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                hebergement = new Hebergement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("type"),
                        rs.getInt("nbEtoiles"),
                        rs.getDouble("distanceCentre"),
                        rs.getString("activites"),
                        rs.getDouble("prixNuit")
                );
            }
        }

        return hebergement;
    }

    @Override
    public List<Hebergement> trouverTout() throws SQLException {
        List<Hebergement> hebergements = new ArrayList<>();
        String query = "SELECT * FROM Hebergement";

        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Hebergement hebergement = new Hebergement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("type"),
                        rs.getInt("nbEtoiles"),
                        rs.getDouble("distanceCentre"),
                        rs.getString("activites"),
                        rs.getDouble("prixNuit")
                );
                hebergements.add(hebergement);
            }
        }

        return hebergements;
    }

    @Override
    public List<Hebergement> filtrerParCriteres(String type, int etoilesMin, double prixMax, double distanceMax) throws SQLException {
        List<Hebergement> hebergements = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Hebergement WHERE 1=1");

        if (type != null && !type.isEmpty()) {
            query.append(" AND type = ?");
        }

        if (etoilesMin > 0) {
            query.append(" AND nbEtoiles >= ?");
        }

        if (prixMax > 0) {
            query.append(" AND prixNuit <= ?");
        }

        if (distanceMax > 0) {
            query.append(" AND distanceCentre <= ?");
        }

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query.toString())) {

            int paramIndex = 1;

            if (type != null && !type.isEmpty()) {
                ps.setString(paramIndex++, type);
            }

            if (etoilesMin > 0) {
                ps.setInt(paramIndex++, etoilesMin);
            }

            if (prixMax > 0) {
                ps.setDouble(paramIndex++, prixMax);
            }

            if (distanceMax > 0) {
                ps.setDouble(paramIndex++, distanceMax);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Hebergement hebergement = new Hebergement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("type"),
                        rs.getInt("nbEtoiles"),
                        rs.getDouble("distanceCentre"),
                        rs.getString("activites"),
                        rs.getDouble("prixNuit")
                );
                hebergements.add(hebergement);
            }
        }

        return hebergements;
    }

    @Override
    public boolean ajouter(Hebergement h) throws SQLException {
        String query = "INSERT INTO Hebergement (id, nom, type, nbEtoiles, distanceCentre, activites, prixNuit) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, h.getId());
            ps.setString(2, h.getNom());
            ps.setString(3, h.getType());
            ps.setInt(4, h.getNbEtoiles());
            ps.setDouble(5, h.getDistanceCentre());
            ps.setString(6, h.getActivites());
            ps.setDouble(7, h.getPrixNuit());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean mettreAJour(Hebergement h) throws SQLException {
        String query = "UPDATE Hebergement SET nom = ?, type = ?, nbEtoiles = ?, distanceCentre = ?, "
                + "activites = ?, prixNuit = ? WHERE id = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, h.getNom());
            ps.setString(2, h.getType());
            ps.setInt(3, h.getNbEtoiles());
            ps.setDouble(4, h.getDistanceCentre());
            ps.setString(5, h.getActivites());
            ps.setDouble(6, h.getPrixNuit());
            ps.setInt(7, h.getId());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean supprimer(int id) throws SQLException {
        String query = "DELETE FROM Hebergement WHERE id = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<String> getTypesDisponibles() throws SQLException {
        Set<String> types = new HashSet<>();
        String query = "SELECT DISTINCT type FROM Hebergement";

        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                types.add(rs.getString("type"));
            }
        }

        return new ArrayList<>(types);
    }
}