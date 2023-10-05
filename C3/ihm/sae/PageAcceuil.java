package sae;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import SAE.*;
import Graphe.*;

public class PageAcceuil extends Application {

    private ObservableList<String> martialStatusList = FXCollections.observableArrayList("HOBBIES", "GEST_ANIMAL_ALLERGY", "HOST_ANIMAL", "GUEST_FOOD", "HOST_FOOD", "PAIR_GENDER", "HISTORY");
    private List<ChoiceBox<String>> choiceBoxes;

    @FXML
    private ChoiceBox<String> martialStatusBox1;
    @FXML
    private ChoiceBox<String> martialStatusBox2;
    @FXML
    private ChoiceBox<String> martialStatusBox3;
    @FXML
    private ChoiceBox<String> martialStatusBox4;
    @FXML
    private ChoiceBox<String> martialStatusBox5;
    @FXML
    private ChoiceBox<String> martialStatusBox6;
    @FXML
    private ChoiceBox<String> martialStatusBox7;

    @FXML
    private TableView<CSVRow> tableView;
    @FXML
    private TableColumn<CSVRow, String> column1;
    @FXML
    private TableColumn<CSVRow, String> column2;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL fxmlFileUrl = getClass().getResource("PageAcceuil.fxml");
        if (fxmlFileUrl == null) {
            System.out.println("Impossible de charger le fichier fxml");
            System.exit(-1);
        }
        loader.setLocation(fxmlFileUrl);
        Parent root = loader.load();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("FXML demo");
        primaryStage.show();
    }

    @FXML
    private void initialize() {
        martialStatusBox1.setItems(martialStatusList);
        martialStatusBox1.setValue("Choisir");

        martialStatusBox2.setItems(martialStatusList);
        martialStatusBox2.setValue("Choisir");

        martialStatusBox3.setItems(martialStatusList);
        martialStatusBox3.setValue("Choisir");

        martialStatusBox4.setItems(martialStatusList);
        martialStatusBox4.setValue("Choisir");

        martialStatusBox5.setItems(martialStatusList);
        martialStatusBox5.setValue("Choisir");

        martialStatusBox6.setItems(martialStatusList);
        martialStatusBox6.setValue("Choisir");

        martialStatusBox7.setItems(martialStatusList);
        martialStatusBox7.setValue("Choisir");
    }
    
    @FXML
    private void importCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Charger les données du fichier CSV dans la table
            try {
                List<CSVRow> data = CSVUtil.readCSV(selectedFile);

                // Réinitialiser la table
                tableView.getItems().clear();
                tableView.getColumns().clear();

                if (!data.isEmpty()) {
                    // Utiliser la première ligne pour déterminer les indices des colonnes
                    CSVRow firstRow = data.get(0);
                    List<String> columnNames = firstRow.getValues();
                    int numColumns = columnNames.size();

                    
                    // Créer les colonnes dynamiquement
                    for (int columnIndex = 0; columnIndex < numColumns; columnIndex++) {
                        TableColumn<CSVRow, String> column = new TableColumn<>(columnNames.get(columnIndex));
                        int finalColumnIndex = columnIndex;
                        column.setCellValueFactory(cellData -> cellData.getValue().valueProperty(finalColumnIndex));
                        tableView.getColumns().add(column);
                    }

                    // Ajouter les données à la table
                    tableView.setItems(FXCollections.observableArrayList(data.subList(1, data.size())));
                }
            } catch (IOException e) {
                displayAlert("Erreur", "Une erreur s'est produite lors de la lecture du fichier CSV.");
            }
        }
    }





    @FXML
    private void applyFilters() {
        
        if (isAllFiltersSelected()) {
            // Récupérer les valeurs sélectionnées dans les ChoiceBox
            String filter1 = martialStatusBox1.getValue();
            String filter2 = martialStatusBox2.getValue();

            // Appliquer les filtres sur les données de la table
            ObservableList<CSVRow> filteredData = FXCollections.observableArrayList();
            ObservableList<CSVRow> data = tableView.getItems();

            for (CSVRow row : data) {
                if (row.matchesFilters(filter1, filter2)) {
                    filteredData.add(row);
                }
            }

            // Afficher les données filtrées dans la table
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

    public static void main(String[] args) {
        launch(args);
    }
}