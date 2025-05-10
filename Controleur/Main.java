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
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/Login.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Accueil.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showRegistrationView() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/Inscription.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showSearchView() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/RechercheHebergement.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showHebergementDetailView(int hebergementId) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/DetailHebergement.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur et définir l'hébergement à afficher
            Vue.DetailHebergementController controller = loader.getController();
            controller.initData(hebergementId);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showReservationView(int hebergementId) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/ReservationForm.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur et définir l'hébergement à réserver
            Vue.ReservationFormController controller = loader.getController();
            controller.initData(hebergementId);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showPaiementView(Modele.Reservation reservation) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/Paiement.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur et définir la réservation à payer
            Vue.PaiementController controller = loader.getController();
            controller.initData(reservation);

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showAdminPanel() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/AdminPanel.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showReportingView() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vue/Ressources/Reporting.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
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