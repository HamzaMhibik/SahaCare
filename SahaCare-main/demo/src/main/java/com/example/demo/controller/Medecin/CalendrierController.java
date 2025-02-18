package com.example.demo.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;

public class CalendrierController {

    @FXML
    private GridPane calendarGrid;

    @FXML
    public void initialize() {
        // Ajouter les dates au calendrier
        String[][] dates = {
                {"1", "2", "3", "4", "5", "6", "7"},
                {"8", "9", "10", "11", "12", "13", "14"},
                {"15", "16", "17", "18", "19", "20", "21"},
                {"22", "23", "24", "25", "26", "27", "28"},
                {"29", "30", "1", "2", "3", "4", "5"}
        };

        for (int row = 0; row < dates.length; row++) {
            for (int col = 0; col < dates[row].length; col++) {
                Label dateLabel = new Label(dates[row][col]);
                calendarGrid.add(dateLabel, col, row + 1);
            }
        }
    }

    @FXML
    private void addAppointment(ActionEvent event) {
        // Logique pour ajouter un rendez-vous
        System.out.println("Ajouter un rendez-vous");
    }
}