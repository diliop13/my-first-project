package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SampleController {

    @FXML
    private ComboBox<String> start; 

    @FXML
    private ComboBox<String> destination; 
    
    @FXML
    private DatePicker depDate;

    @FXML
    private DatePicker retDate;

    

    // Λίστα με όλες τις διαθέσιμες πόλεις
    private final ObservableList<String> allCities = FXCollections.observableArrayList(
            "Athens (ATH)", "Barcelona (BCN)", "Belgrade(BEG)", "Thessaloniki (SKG)"
    );

    @FXML
    public void initialize() {
        start.setItems(FXCollections.observableArrayList(allCities));
        destination.setItems(FXCollections.observableArrayList(allCities));

        start.valueProperty().addListener((obs, oldVal, newVal) -> {
            updateDestinationList(newVal);
        });
       
    }

    private void updateDestinationList(String selectedStart) {
        destination.getItems().setAll(allCities);
        if (selectedStart != null) {
            destination.getItems().remove(selectedStart);
        }

        if (destination.getValue() != null && destination.getValue().equals(selectedStart)) {
            destination.setValue(null);
        }
    }

    @FXML
    private void handleSearch(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample2.fxml"));
        Parent root = loader.load();
        
        SampleController2 controller2 = loader.getController();
        controller2.setFlightInfo(
        		start.getValue(),
        	    destination.getValue(),
        	    depDate.getValue()
        	);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        

        Scene currentScene = ((Node) event.getSource()).getScene();
        Scene newScene = new Scene(root, 600, 500); 
        newScene.getStylesheets().addAll(currentScene.getStylesheets());
        
        stage.setScene(newScene);
        stage.show();
    }
}