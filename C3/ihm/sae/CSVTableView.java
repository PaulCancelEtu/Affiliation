package sae;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVTableView extends BorderPane {
    private TableView<CSVRow> tableView;
    private ChoiceBox<String>[] choiceBoxes;
    private File selectedFile;

    public CSVTableView() {
        
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        
        TableColumn<CSVRow, String> column1 = new TableColumn<>("Column 1");
        TableColumn<CSVRow, String> column2 = new TableColumn<>("Column 2");

        
        tableView.getColumns().addAll(column1, column2);

        
        choiceBoxes = new ChoiceBox[2];
        for (int i = 0; i < choiceBoxes.length; i++) {
            choiceBoxes[i] = new ChoiceBox<>();
            choiceBoxes[i].getItems().addAll("Option 1", "Option 2", "Option 3");
            choiceBoxes[i].setValue("Choisir");
        }

        
        Button importButton = new Button("Importer");
        importButton.setOnAction(event -> importCSV());

        
        Button applyFiltersButton = new Button("Appliquer les filtres");
        applyFiltersButton.setOnAction(event -> applyFilters());

        
        HBox toolbar = new HBox(10);
        toolbar.setPadding(new Insets(10));
        toolbar.getChildren().addAll(importButton, applyFiltersButton);
        toolbar.getChildren().addAll(choiceBoxes);

        
        setCenter(tableView);
        setBottom(toolbar);
    }

    private void importCSV() {
        
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        selectedFile = fileChooser.showOpenDialog(null);

        
        if (selectedFile != null) {
            
            System.out.println("Fichier sélectionné : " + selectedFile.getAbsolutePath());

            
            tableView.getItems().clear();
            tableView.getColumns().clear();

            
            TableColumn<CSVRow, String> column1 = new TableColumn<>("Column 1");
            TableColumn<CSVRow, String> column2 = new TableColumn<>("Column 2");
            column1.setCellValueFactory(new PropertyValueFactory<>("column1"));
            column2.setCellValueFactory(new PropertyValueFactory<>("column2"));
            tableView.getColumns().addAll(column1, column2);

            
            try {
                List<CSVRow> data = CSVUtil.readCSV(selectedFile);
                tableView.setItems(FXCollections.observableArrayList(data));
            } catch (IOException e) {
                e.printStackTrace();
                displayAlert("Erreur", "Une erreur s'est produite lors de la lecture du fichier CSV.");
            }
        }
    }

    private void applyFilters() {
        
        if (isAllFiltersSelected()) {
            
            String filter1 = choiceBoxes[0].getValue();
            String filter2 = choiceBoxes[1].getValue();

            
            ObservableList<CSVRow> filteredData = FXCollections.observableArrayList();
            ObservableList<CSVRow> data = tableView.getItems();

            for (CSVRow row : data) {
                if (row.matchesFilters(filter1, filter2)) {
                    filteredData.add(row);
                }
            }

            
            tableView.setItems(filteredData);
        } else {
            displayAlert("Erreur", "Veuillez sélectionner tous les filtres avant de les appliquer.");
        }
    }

    private boolean isAllFiltersSelected() {
        for (ChoiceBox<String> choiceBox : choiceBoxes) {
            if (choiceBox.getValue() == null || choiceBox.getValue().equals("Choisir")) {
                return false;
            }
        }
        return true;
    }

    private void displayAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


