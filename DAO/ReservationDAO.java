package DAO;

import Modele.Reservation;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ReservationDAO {
    Reservation trouverParId(int id) throws SQLException;
    List<Reservation> trouverTout() throws SQLException;
    void creer(Reservation r) throws SQLException;
    void mettreAJour(Reservation r) throws SQLException;
    void supprimer(int id) throws SQLException;
}