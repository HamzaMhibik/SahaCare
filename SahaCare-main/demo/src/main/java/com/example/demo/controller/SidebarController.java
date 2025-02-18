package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import java.io.IOException;

public class SidebarController {

    @FXML
    private StackPane rootContainer; // Root container (StackPane)

    @FXML
    private BorderPane mainContainer; // Main content (BorderPane)

    @FXML
    private HBox header; // Header

    @FXML
    private VBox sidebar; // Sidebar

    @FXML
    private ListView<String> sidebarList; // Sidebar menu list

    @FXML
    public void initialize() {
        // Initialize sidebar items
        initializeSidebar();
        // Add listener to sidebar items to load corresponding content
        sidebarList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                loadContent(newValue);
            }
        });

        // Load the default content (Dashboard)
        loadContent("Tableau de bord");
    }

    private void initializeSidebar() {
        if (sidebarList != null) {
            sidebarList.getItems().addAll(
                    "Tableau de bord",
                    "Calendrier",
                    "Enregistrements",  // Changed from "Historiques"
                    "Patients",          // Changed from "Médecins"
                    "Articles"
            );
        } else {
            System.out.println("Sidebar ListView is null. Check FXML.");
        }
    }

    private void loadContent(String menuItem) {
        String fxmlFile = "";

        // Determine which FXML to load based on the menu item selected
        switch (menuItem) {
            case "Calendrier":
                fxmlFile = "/com/example/demo/view/medecin/Calendrier.fxml";
                break;
            case "Enregistrements":
                fxmlFile = "/com/example/demo/view/medecin/Enregistrement.fxml";
                break;
            case "Patients":
                fxmlFile = "/com/example/demo/view/medecin/Patients.fxml";
                break;
            case "Articles":
                fxmlFile = "/com/example/demo/view/medecin/Articles.fxml";
                break;
            default:
                // Default view (e.g., Dashboard)
                fxmlFile = "/com/example/demo/view/medecin/Dashboard.fxml";
                break;
        }

        try {
            // Load the selected FXML file into the center of the BorderPane
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Region content = loader.load();
            mainContainer.setCenter(content);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading FXML: " + fxmlFile);
        }
    }
}
