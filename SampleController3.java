package application;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SampleController3 {

    @FXML
    private GridPane seatGrid;
    
    @FXML
    private Button buyTicketButton;

    @FXML
    private Text infoTitleText; 
    
    @FXML
    private Text infoDetailsText; 
 
    
    private String startCity;
    private String destinationCity;
    private LocalDate depDate;

    private final int ROWS = 5;
    private final int SEATS_PER_ROW = 6; 
    
    private final int TOTAL_SEATS = ROWS * SEATS_PER_ROW;

    private Set<Integer> reservedSeats = new HashSet<>();
    private Button selectedSeatButton = null; 

    @FXML
    public void initialize() {
        generateRandomReservedSeats();
        createSeatButtons();
        
        if (buyTicketButton != null) {
            buyTicketButton.setDisable(true);
        }
    }

    private void generateRandomReservedSeats() {
        Random rand = new Random();
        int reservedCount = 6; 
        
        while (reservedSeats.size() < reservedCount) {
            reservedSeats.add(rand.nextInt(TOTAL_SEATS)); 
        }
    }

    private void createSeatButtons() {
        int id = 0;
        final int VISIBLE_COLS = 8; 

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < VISIBLE_COLS; c++) {
                
                if (c == 2 || c == 5) {
                    continue; 
                }
                
                Button seat = new Button(String.valueOf(id + 1));
                seat.setMinSize(50, 50);
                seat.getStyleClass().add("seat-button");

                if (reservedSeats.contains(id)) {
                    seat.setDisable(true); 
                } else {
                    
                    int seatID = id+1; 
                    seat.setOnAction(e -> {
                        
                        if (selectedSeatButton != null) {
                            selectedSeatButton.getStyleClass().remove("seat-selected");
                        }

                        seat.getStyleClass().add("seat-selected");
                        selectedSeatButton = seat;
                        System.out.println("Selected seat ID: " + seatID); 
                        if (buyTicketButton != null) {
                            buyTicketButton.setDisable(false);
                        }
                    });
                }

                seatGrid.add(seat, c, r);
                id++;
            }
        }
    }
    
    public void setBookingInfo(String start, String destination, String flightDetails,
                               LocalDate departure) {
        this.startCity = start;
        this.destinationCity = destination;
        this.depDate = departure;

        if (infoTitleText != null) {
            infoTitleText.setText(start + " - " + destination);
        }

        if (infoDetailsText != null) {
            infoDetailsText.setText(
                flightDetails + " | " +
                "Departure date: " + departure
            );
        }
    }
    
        @FXML
    private void handleBack(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample2.fxml"));
        Parent root = loader.load();
        
        SampleController2 controller2 = loader.getController();
        controller2.setFlightInfo(this.startCity, this.destinationCity, this.depDate);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene currentScene = ((Node) event.getSource()).getScene();
        
        Scene newScene = new Scene(root, 600, 500); 
        
        newScene.getStylesheets().addAll(currentScene.getStylesheets()); 

        stage.setScene(newScene);
        stage.show();
    }
    
    @FXML
    private void handleBuyTicket(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ticket Purchased");
        alert.setHeaderText(null);
        String seatNumber = "N/A";
        if (selectedSeatButton != null) {
            seatNumber = selectedSeatButton.getText();
        }
        alert.setContentText("Το εισιτήριο σας αγοράστηκε επιτυχώς!"+"H θέση σας είναι:"+seatNumber+".Ευχαριστούμε.");
        alert.showAndWait();
        
        Scene currentScene = ((Node) event.getSource()).getScene();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) currentScene.getWindow(); 
        Scene newScene = new Scene(root, 400, 400); 

        newScene.getStylesheets().addAll(currentScene.getStylesheets());
        
        stage.setScene(newScene);
        stage.show();
    }
}