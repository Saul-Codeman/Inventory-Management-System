package com.example.wgusoftware1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.wgusoftware1.Library.*;

public class AddPartController implements Initializable {

    //FXML
    @FXML
    private Label addPartUniqueLbl;

    @FXML
    private Button addPartCancelButton;

    @FXML
    private TextField addPartIdTxt;

    @FXML
    private RadioButton addPartInHouseButton;

    @FXML
    private TextField addPartInventoryTxt;

    @FXML
    private ToggleGroup addPartLocationTG;

    @FXML
    private TextField addPartMachineIdTxt;

    @FXML
    private TextField addPartMaxTxt;

    @FXML
    private TextField addPartMinTxt;

    @FXML
    private TextField addPartNameTxt;

    @FXML
    private RadioButton addPartOutsourcedButton;

    @FXML
    private TextField addPartPriceTxt;

    @FXML
    private Button addPartSaveButton;

    // Handlers

    Stage stage;
    Parent scene;

    // Switches label to machineID
    @FXML
    void addPartInHouseHandler(ActionEvent event) {
        addPartUniqueLbl.setText("Machine ID");
    }

    // Switches label to Company Name
    @FXML
    void addPartOutsourcedHandler(ActionEvent event) {
        addPartUniqueLbl.setText("Company Name");
    }

    // Cancel Changes
    @FXML
    void addPartCancelHandler(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            switchScreen(event, mainUrl);
        }
    }

    // Save and add the part
    @FXML
    void addPartSaveHandler(ActionEvent event) throws IOException {
        // Take and save the data
        try{
            int id;
            String name = addPartNameTxt.getText();
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Input fields cannot be empty.");
                alert.showAndWait();
                return;
            }
            // RUNTIME ERROR when typing a non-numeric value into text fields
            int stock = Integer.parseInt(addPartInventoryTxt.getText());
            double price = Double.parseDouble(addPartPriceTxt.getText());
            int max = Integer.parseInt(addPartMaxTxt.getText());
            int min = Integer.parseInt(addPartMinTxt.getText());

            if (min > max){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Max must be greater than min.");
                alert.showAndWait();
                return;
            }
            if (stock > max || min > stock){
                Alert alert = new Alert(Alert.AlertType.WARNING, "Inventory must be between max and min.");
                alert.showAndWait();
                return;
            }

            if (addPartInHouseButton.isSelected()) {
                int machineId = Integer.parseInt(addPartMachineIdTxt.getText());
                id = autoPartGenId();
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
            }
            else{
                String companyName = addPartMachineIdTxt.getText();
                if (companyName.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Input fields cannot be empty.");
                    alert.showAndWait();
                    return;
                }
                id = autoPartGenId();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }

            //Return to main menu
            switchScreen(event, mainUrl);
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Please enter a valid value for each input field");
            alert.showAndWait();
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
