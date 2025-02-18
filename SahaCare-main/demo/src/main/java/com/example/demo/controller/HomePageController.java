package com.example.demo.controller;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;
import java.sql.PreparedStatement;
import java.io.IOException;
import com.example.demo.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HomePageController {

    @FXML
    private Button patientButton;

    @FXML
    private Button doctorButton;

    @FXML
    private Button laboratoryButton;

    @FXML
    private VBox mainContent;

    @FXML
    private StackPane loginOverlay;

    @FXML
    private VBox loginForm;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label homeTitle;

    @FXML
    public void initialize() {
        // Animation de fondu pour le titre
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), homeTitle);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    @FXML
    public void handlePatientButtonClick() {
        showLoginForm();
    }

    @FXML
    public void handleDoctorButtonClick() {
        showLoginForm();
    }

    @FXML
    public void handleLaboratoryButtonClick() {
        showLoginForm();
    }

    private void showLoginForm() {
        loginOverlay.setVisible(true);
        loginOverlay.toFront();

        // Animation d'apparition
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), loginOverlay);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    @FXML
    private void closeLoginForm() {
        // Animation de disparition
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), loginOverlay);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(event -> loginOverlay.setVisible(false));
        fadeOut.play();
    }

    @FXML
    public void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        String hashedPassword = hashPassword(password);

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setString(1, email);
                statement.setString(2, hashedPassword);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String username = resultSet.getString("username");
                        String role = resultSet.getString("role");
                        System.out.println("Login successful! Welcome, " + username + " (" + role + ")");

                        loginOverlay.setVisible(false);
                        loadSidebarPage();
                    } else {
                        System.out.println("Invalid email or password.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
        }
    }

    private void loadSidebarPage() {
        try {
            Parent sidebarPage = FXMLLoader.load(getClass().getResource("/com/example/demo/view/SidebarView.fxml"));
            Stage stage = (Stage) patientButton.getScene().getWindow();
            Scene scene = new Scene(sidebarPage);

            Platform.runLater(() -> {
                stage.setScene(scene);
                stage.setFullScreen(true);
                stage.setFullScreenExitHint("");
                stage.show();
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String hashPassword(String password) {
        return password;
    }
}
