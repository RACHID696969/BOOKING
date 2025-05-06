package DAO;

import Modele.Utilisateur;
import java.util.List;

public interface UtilisateurDAO {
    List<Utilisateur> findAll();
    Utilisateur findById(int id);
    Utilisateur findByEmailAndPassword(String email, String password);
    boolean insert(Utilisateur u);
    boolean update(Utilisateur u);
    boolean delete(int id);
}
