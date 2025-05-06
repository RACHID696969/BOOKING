package DAO;

import Modele.Utilisateur;
import java.util.*;
import java.sql.*;

public class UtilisateurDAOImpl implements UtilisateurDAO {
    @Override
    public List<Utilisateur> findAll() {
        List<Utilisateur> list = new ArrayList<>();
        try (Connection conn = ConnexionBD.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM Utilisateur")) {
            while (rs.next()) {
                list.add(new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("motDePasse"),
                        rs.getString("role")));
            }
        } catch(SQLException e){ e.printStackTrace(); }
        return list;
    }

    @Override
    public Utilisateur findById(int id) {
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Utilisateur WHERE id=?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return new Utilisateur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("email"), rs.getString("motDePasse"),rs.getString("role"));
        } catch(SQLException e){ e.printStackTrace(); }
        return null;
    }

    @Override
    public Utilisateur findByEmailAndPassword(String email, String password) {
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Utilisateur WHERE email=? AND motDePasse=?")) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return new Utilisateur(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"),
                        rs.getString("email"), rs.getString("motDePasse"), rs.getString("role"));
        } catch(SQLException e){ e.printStackTrace(); }
        return null;
    }

    @Override
    public boolean insert(Utilisateur u) {
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO Utilisateur (id, nom, prenom, email, motDePasse, role) VALUES (?, ?, ?, ?, ?, ?)")) {
            ps.setInt(1, u.getId());
            ps.setString(2, u.getNom());
            ps.setString(3, u.getPrenom());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getMotDePasse());
            ps.setString(6, u.getRole());
            return ps.executeUpdate() > 0;
        } catch(SQLException e){ e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean update(Utilisateur u) {
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE Utilisateur SET nom=?, prenom=?, email=?, motDePasse=?, role=? WHERE id=?")) {
            ps.setString(1, u.getNom());
            ps.setString(2, u.getPrenom());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getMotDePasse());
            ps.setString(5, u.getRole());
            ps.setInt(6, u.getId());
            return ps.executeUpdate() > 0;
        } catch(SQLException e){ e.printStackTrace(); }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try (Connection conn = ConnexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM Utilisateur WHERE id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch(SQLException e){ e.printStackTrace(); }
        return false;
    }
}