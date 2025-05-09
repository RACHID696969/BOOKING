package DAO;

import Modele.Reduction;
import java.sql.SQLException;
import java.util.List;

public interface ReductionDAO {
    Reduction trouverParId(int id) throws SQLException;
    List<Reduction> trouverTout() throws SQLException;
    List<Reduction> trouverParUtilisateur(int utilisateurId) throws SQLException;
    boolean ajouter(Reduction reduction) throws SQLException;
    boolean mettreAJour(Reduction reduction) throws SQLException;
    boolean supprimer(int id) throws SQLException;
    boolean ajouterReductionUtilisateur(int reductionId, int utilisateurId) throws SQLException;
    boolean supprimerReductionUtilisateur(int reductionId, int utilisateurId) throws SQLException;
}