package Modele;

import java.math.BigDecimal;
import java.sql.Date;

public class Reservation {
    private int        id;
    private int        clientId;
    private int        hebergementId;
    private Date       dateArrivee;
    private Date       dateDepart;
    private int        nbAdultes;
    private int        nbEnfants;
    private int        nbChambres;
    private BigDecimal total;
    private BigDecimal reduction;

    public Reservation() { }

    public Reservation(int clientId, int hebergementId,
                       Date dateArrivee, Date dateDepart,
                       int nbAdultes, int nbEnfants, int nbChambres,
                       BigDecimal total, BigDecimal reduction) {

        this.clientId      = clientId;
        this.hebergementId = hebergementId;
        this.dateArrivee   = dateArrivee;
        this.dateDepart    = dateDepart;
        this.nbAdultes     = nbAdultes;
        this.nbEnfants     = nbEnfants;
        this.nbChambres    = nbChambres;
        this.total         = total;
        this.reduction     = reduction;
    }

    // getters & setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }

    public int getHebergementId() { return hebergementId; }
    public void setHebergementId(int hebergementId) { this.hebergementId = hebergementId; }

    public Date getDateArrivee() { return dateArrivee; }
    public void setDateArrivee(Date dateArrivee) { this.dateArrivee = dateArrivee; }

    public Date getDateDepart() { return dateDepart; }
    public void setDateDepart(Date dateDepart) { this.dateDepart = dateDepart; }

    public int getNbAdultes() { return nbAdultes; }
    public void setNbAdultes(int nbAdultes) { this.nbAdultes = nbAdultes; }

    public int getNbEnfants() { return nbEnfants; }
    public void setNbEnfants(int nbEnfants) { this.nbEnfants = nbEnfants; }

    public int getNbChambres() { return nbChambres; }
    public void setNbChambres(int nbChambres) { this.nbChambres = nbChambres; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public BigDecimal getReduction() { return reduction; }
    public void setReduction(BigDecimal reduction) { this.reduction = reduction; }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", hebergementId=" + hebergementId +
                ", dateArrivee=" + dateArrivee +
                ", dateDepart=" + dateDepart +
                ", nbAdultes=" + nbAdultes +
                ", nbEnfants=" + nbEnfants +
                ", nbChambres=" + nbChambres +
                ", total=" + total +
                ", reduction=" + reduction +
                '}';
    }
}
