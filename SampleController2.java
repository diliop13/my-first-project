package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class SampleController2 {

    @FXML
    private Button b1, b2, b3, b4, b5;

    @FXML
    private Text textb11;

    private String startCity;
    private String destinationCity;
    private LocalDate depDate;

    public void setFlightInfo(String start, String destination,LocalDate departure) {
        this.startCity = start;
        this.destinationCity = destination;
        this.depDate = departure;
        
        textb11.setText("Flights from " + start + " to " + destination);

        b1.setText("08:00 - 09:35 | Aegean Airlines");
        b2.setText("12:45 - 14:15 | Air France");
        b3.setText("17:30 - 19:05 | Ryanair");
        b4.setText("20:15 - 21:50 | Sky Express");
        b5.setText("22:10 - 23:40 | Transavia");
    }

    @FXML
    private void handleFlightSelect(ActionEvent event) throws Exception {

        Button clickedButton = (Button) event.getSource();
        String selectedFlight = clickedButton.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample3.fxml"));
        Parent root = loader.load();
        
        SampleController3 controller3 = loader.getController();
        controller3.setBookingInfo(
            startCity, 
            destinationCity, 
            selectedFlight,
            depDate
        );

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        
        Scene currentScene = ((Node) event.getSource()).getScene();
        Scene newScene = new Scene(root, 750, 650); 
        newScene.getStylesheets().addAll(currentScene.getStylesheets()); 
        
        stage.setScene(newScene);
        stage.show();
    }
    
    @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
        Parent root = loader.load();
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene currentScene = ((Node) event.getSource()).getScene();
        
        // Επιστροφή στο αρχικό μέγεθος (400x400)
        Scene newScene = new Scene(root, 400, 400); 
        
        newScene.getStylesheets().addAll(currentScene.getStylesheets()); 

        stage.setScene(newScene);
        stage.show();
    }
}