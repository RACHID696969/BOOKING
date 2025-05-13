// Controleur/UtilisateurControleur.java
package Controleur;

import DAO.UtilisateurDAO;
import DAO.UtilisateurDAOImpl;
import DAO.ReductionDAO;
import DAO.ReductionDAOImpl;
import Modele.Utilisateur;
import Modele.Reduction;

import java.sql.SQLException;
import java.util.List;

public class UtilisateurControleur {
    private UtilisateurDAO utilisateurDAO;
    private ReductionDAO reductionDAO;
    private static Utilisateur utilisateurConnecte = null;

    public UtilisateurControleur() {
        this.utilisateurDAO = new UtilisateurDAOImpl();
        this.reductionDAO = new ReductionDAOImpl();
    }

    public boolean inscrire(String nom, String prenom, String email, String motDePasse) {
        // Générer un nouvel ID (dans une application réelle, cela serait géré par la base de données)
        int newId = generateId();

        Utilisateur nouvelUtilisateur = new Utilisateur(newId, nom, prenom, email, motDePasse, "client");
        return utilisateurDAO.insert(nouvelUtilisateur);
    }

    public Utilisateur connecter(String email, String motDePasse) {
        Utilisateur utilisateur = utilisateurDAO.findByEmailAndPassword(email, motDePasse);
        if (utilisateur != null) {
            utilisateurConnecte = utilisateur;
        }
        return utilisateur;
    }

    public void deconnecter() {
        utilisateurConnecte = null;
    }

    public static Utilisateur getUtilisateurConnecte() {
        return utilisateurConnecte;
    }

    public boolean estAncienClient(int utilisateurId) {
        try {
            List<Reduction> reductions = reductionDAO.trouverParUtilisateur(utilisateurId);
            return reductions != null && !reductions.isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Reduction> getReductionsUtilisateur(int utilisateurId) {
        try {
            return reductionDAO.trouverParUtilisateur(utilisateurId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurDAO.findAll();
    }

    public boolean updateUtilisateur(Utilisateur u) {
        return utilisateurDAO.update(u);
    }

    public boolean deleteUtilisateur(int id) {
        return utilisateurDAO.delete(id);
    }


    private int generateId() {
        List<Utilisateur> utilisateurs = utilisateurDAO.findAll();
        int maxId = 0;
        for (Utilisateur u : utilisateurs) {
            if (u.getId() > maxId) {
                maxId = u.getId();
            }
        }
        return maxId + 1;
    }
}





