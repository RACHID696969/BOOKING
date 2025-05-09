package DAO;

import Modele.Hebergement;
import java.sql.SQLException;
import java.util.List;

public interface HebergementDAO {
    Hebergement trouverParId(int id) throws SQLException;
    List<Hebergement> trouverTout() throws SQLException;
    List<Hebergement> filtrerParCriteres(String type, int etoilesMin, double prixMax, double distanceMax) throws SQLException;
    boolean ajouter(Hebergement h) throws SQLException;
    boolean mettreAJour(Hebergement h) throws SQLException;
    boolean supprimer(int id) throws SQLException;
    List<String> getTypesDisponibles() throws SQLException;
}