package Controleur;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setTitle("Booking Application");

        // Charger la vue de login
        showLoginView();
    }

    public static void showLoginView() {
        try {
            System.out.println("Chargement de la vue Login...");
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/Login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("Vue Login chargée avec succès");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de Login.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void showMainView() {
        try {
            System.out.println("Chargement de la vue principale...");
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/Accueil.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("Vue principale chargée avec succès");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de Accueil.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void showRegistrationView() {
        try {
            System.out.println("Chargement de la vue Inscription...");
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/Inscription.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("Vue Inscription chargée avec succès");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de Inscription.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void showSearchView() {
        try {
            System.out.println("Chargement de la vue Recherche...");
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/RechercheHebergement.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("Vue Recherche chargée avec succès");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de RechercheHebergement.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void showHebergementDetailView(int hebergementId) {
        try {
            System.out.println("Chargement des détails de l'hébergement " + hebergementId + "...");
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/DetailHebergement.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur et définir l'hébergement à afficher
            Vue.DetailHebergementController controller = loader.getController();
            controller.initData(hebergementId);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("Détails de l'hébergement chargés avec succès");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de DetailHebergement.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void showReservationView(int hebergementId) {
        try {
            System.out.println("Chargement du formulaire de réservation...");
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/ReservationForm.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur et définir l'hébergement à réserver
            Vue.ReservationFormController controller = loader.getController();
            controller.initData(hebergementId);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("Formulaire de réservation chargé avec succès");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de ReservationForm.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void showPaiementView(Modele.Reservation reservation) {
        try {
            System.out.println("Chargement de la vue Paiement...");
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/Paiement.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur et définir la réservation à payer
            Vue.PaiementController controller = loader.getController();
            controller.initData(reservation);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("Vue Paiement chargée avec succès");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de Paiement.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void showAdminPanel() {
        try {
            System.out.println("Chargement du panel Admin...");
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/AdminPanel.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("Panel Admin chargé avec succès");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de AdminPanel.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }



    public static void showMesReservationsView() {
        try {
            System.out.println("Chargement de la vue Mes Réservations...");
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/MesReservations.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            System.out.println("Vue Mes Réservations chargée avec succès");
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de MesReservations.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}