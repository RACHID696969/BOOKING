package DAO;

import Modele.Option;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OptionDAOImpl implements OptionDAO {

    @Override
    public Option trouverParId(int id) throws SQLException {
        Option option = null;
        String query = "SELECT * FROM Option WHERE id = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                option = new Option(
                        rs.getInt("id"),
                        rs.getString("nom")
                );
            }
        }

        return option;
    }

    @Override
    public List<Option> trouverTout() throws SQLException {
        List<Option> options = new ArrayList<>();
        String query = "SELECT * FROM Option";

        try (Connection conn = ConnexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Option option = new Option(
                        rs.getInt("id"),
                        rs.getString("nom")
                );
                options.add(option);
            }
        }

        return options;
    }

    @Override
    public List<Option> trouverParHebergement(int hebergementId) throws SQLException {
        List<Option> options = new ArrayList<>();
        String query = "SELECT o.* FROM Option o "
                + "JOIN Options-hebergement oh ON o.id = oh.id_option "
                + "WHERE oh.id_hebergement = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, hebergementId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Option option = new Option(
                        rs.getInt("id"),
                        rs.getString("nom")
                );
                options.add(option);
            }
        }

        return options;
    }

    @Override
    public boolean ajouter(Option option) throws SQLException {
        String query = "INSERT INTO Option (id, nom) VALUES (?, ?)";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, option.getId());
            ps.setString(2, option.getNom());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean mettreAJour(Option option) throws SQLException {
        String query = "UPDATE Option SET nom = ? WHERE id = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, option.getNom());
            ps.setInt(2, option.getId());

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean supprimer(int id) throws SQLException {
        String query = "DELETE FROM Option WHERE id = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean ajouterOptionHebergement(int optionId, int hebergementId) throws SQLException {
        String query = "INSERT INTO Options-hebergement (id_option, id_hebergement) VALUES (?, ?)";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, optionId);
            ps.setInt(2, hebergementId);

            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean supprimerOptionHebergement(int optionId, int hebergementId) throws SQLException {
        String query = "DELETE FROM Options-hebergement WHERE id_option = ? AND id_hebergement = ?";

        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, optionId);
            ps.setInt(2, hebergementId);

            return ps.executeUpdate() > 0;
        }
    }
}