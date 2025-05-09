package DAO;

import Modele.Option;
import java.sql.SQLException;
import java.util.List;

public interface OptionDAO {
    Option trouverParId(int id) throws SQLException;
    List<Option> trouverTout() throws SQLException;
    List<Option> trouverParHebergement(int hebergementId) throws SQLException;
    boolean ajouter(Option option) throws SQLException;
    boolean mettreAJour(Option option) throws SQLException;
    boolean supprimer(int id) throws SQLException;
    boolean ajouterOptionHebergement(int optionId, int hebergementId) throws SQLException;
    boolean supprimerOptionHebergement(int optionId, int hebergementId) throws SQLException;
}