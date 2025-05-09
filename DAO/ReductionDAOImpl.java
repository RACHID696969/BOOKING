package DAO;

import Modele.Reduction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReductionDAOImpl implements ReductionDAO {

    @Override
    public Reduction trouverParId(int id) throws SQLException {
        Reduction reduction = null;
        String query = "SELECT * FROM Reduction WHERE id = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                reduction = new Reduction(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getDouble("pourcentage")
                );
            }
        }

        return reduction;
    }

    @Override
    public List<Reduction> trouverTout() throws SQLException {
        List<Reduction> reductions = new ArrayList<>();
        String query = "SELECT * FROM Reduction";

        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Reduction reduction = new Reduction(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getDouble("pourcentage")
                );
                reductions.add(reduction);
            }
        }

        return reductions;
    }

    @Override
    public List<Reduction> trouverParUtilisateur(int utilisateurId) throws SQLException {
        List<Reduction> reductions = new ArrayList<>();
        String query = "SELECT r.* FROM Reduction r "
                + "JOIN Reduction-Utilisateur ru ON r.id = ru.id_reduction "
                + "WHERE ru.id_utilisteur = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, utilisateurId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Reduction reduction = new Reduction(
                        rs.getInt("id"),
                        rs.getString("description"),
                        rs.getDouble("pourcentage")
                );
                reductions.add(reduction);
            }
        }

        return reductions;
    }

    @Override
    public boolean ajouter(Reduction reduction) throws SQLException {
        String query = "INSERT INTO Reduction (id, description, pourcentage) VALUES (?, ?, ?)";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, reduction.getId());
            ps.setString(2, reduction.getDescription());
            ps.setDouble(3, reduction.getPourcentage());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean mettreAJour(Reduction reduction) throws SQLException {
        String query = "UPDATE Reduction SET description = ?, pourcentage = ? WHERE id = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, reduction.getDescription());
            ps.setDouble(2, reduction.getPourcentage());
            ps.setInt(3, reduction.getId());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean supprimer(int id) throws SQLException {
        String query = "DELETE FROM Reduction WHERE id = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean ajouterReductionUtilisateur(int reductionId, int utilisateurId) throws SQLException {
        String query = "INSERT INTO Reduction-Utilisateur (id_reduction, id_utilisteur) VALUES (?, ?)";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, reductionId);
            ps.setInt(2, utilisateurId);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean supprimerReductionUtilisateur(int reductionId, int utilisateurId) throws SQLException {
        String query = "DELETE FROM Reduction-Utilisateur WHERE id_reduction = ? AND id_utilisteur = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, reductionId);
            ps.setInt(2, utilisateurId);

            return ps.executeUpdate() > 0;
        }
    }
}