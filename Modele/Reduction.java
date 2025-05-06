package Modele;

public class Reduction {
    private int id;
    private String description;
    private double pourcentage;

    public Reduction() {}

    public Reduction(int id, String description, double pourcentage) {
        this.id = id; this.description = description; this.pourcentage = pourcentage;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPourcentage() { return pourcentage; }
    public void setPourcentage(double pourcentage) { this.pourcentage = pourcentage; }
}
