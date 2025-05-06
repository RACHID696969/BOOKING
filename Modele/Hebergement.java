package Modele;

public class Hebergement {
    private int id;
    private String nom, type, activites;
    private int nbEtoiles;
    private double distanceCentre, prixNuit;

    public Hebergement() {}

    public Hebergement(int id, String nom, String type, int nbEtoiles, double distanceCentre, String activites, double prixNuit) {
        this.id = id; this.nom = nom; this.type = type; this.nbEtoiles = nbEtoiles;
        this.distanceCentre = distanceCentre; this.activites = activites; this.prixNuit = prixNuit;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getNbEtoiles() { return nbEtoiles; }
    public void setNbEtoiles(int nbEtoiles) { this.nbEtoiles = nbEtoiles; }
    public double getDistanceCentre() { return distanceCentre; }
    public void setDistanceCentre(double distanceCentre) { this.distanceCentre = distanceCentre; }
    public String getActivites() { return activites; }
    public void setActivites(String activites) { this.activites = activites; }
    public double getPrixNuit() { return prixNuit; }
    public void setPrixNuit(double prixNuit) { this.prixNuit = prixNuit; }
}
