
package Controleur;

import DAO.HebergementDAO;
import DAO.HebergementDAOImpl;
import DAO.OptionDAO;
import DAO.OptionDAOImpl;
import Modele.Hebergement;
import Modele.Option;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HebergementControleur {
    private HebergementDAO hebergementDAO;
    private OptionDAO optionDAO;

    public HebergementControleur() {
        this.hebergementDAO = new HebergementDAOImpl();
        this.optionDAO = new OptionDAOImpl();
    }

    public List<Hebergement> getAllHebergements() {
        try {
            return hebergementDAO.trouverTout();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public Hebergement getHebergementById(int id) {
        try {
            return hebergementDAO.trouverParId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Hebergement> rechercherHebergements(String type, int etoilesMin, double prixMax, double distanceMax) {
        try {
            return hebergementDAO.filtrerParCriteres(type, etoilesMin, prixMax, distanceMax);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<String> getTypesHebergement() {
        try {
            return hebergementDAO.getTypesDisponibles();
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Option> getOptionsHebergement(int hebergementId) {
        try {
            return optionDAO.trouverParHebergement(hebergementId);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean ajouterHebergement(Hebergement hebergement) {
        try {
            return hebergementDAO.ajouter(hebergement);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean mettreAJourHebergement(Hebergement hebergement) {
        try {
            return hebergementDAO.mettreAJour(hebergement);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean supprimerHebergement(int id) {
        try {
            return hebergementDAO.supprimer(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean ajouterOptionHebergement(int optionId, int hebergementId) {
        try {
            return optionDAO.ajouterOptionHebergement(optionId, hebergementId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean supprimerOptionHebergement(int optionId, int hebergementId) {
        try {
            return optionDAO.supprimerOptionHebergement(optionId, hebergementId);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
