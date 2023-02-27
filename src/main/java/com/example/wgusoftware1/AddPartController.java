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

    @FXML
    void addPartInHouseHandler(ActionEvent event) {
        addPartUniqueLbl.setText("Machine ID");
    }

    @FXML
    void addPartOutsourcedHandler(ActionEvent event) {
        addPartUniqueLbl.setText("Company Name");
    }

    @FXML
    void addPartCancelHandler(ActionEvent event) throws IOException {
        switchScreen(event, mainUrl);
    }

    @FXML
    void addPartSaveHandler(ActionEvent event) throws IOException {
        // Take and save the data
        try{
            int id = Integer.parseInt(addPartIdTxt.getText());
            String name = addPartNameTxt.getText();
            int stock = Integer.parseInt(addPartInventoryTxt.getText());
            double price = Double.parseDouble(addPartPriceTxt.getText());
            int max = Integer.parseInt(addPartMaxTxt.getText());
            int min = Integer.parseInt(addPartMinTxt.getText());

            if (min > max){
                System.out.println("Max must be greater than min.");
            }

            if (addPartInHouseButton.isSelected()) {
                int machineId = Integer.parseInt(addPartMachineIdTxt.getText());
                Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineId));
            }
            else{
                String companyName = addPartMachineIdTxt.getText();
                Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
            }

            //Return to main menu
            switchScreen(event, mainUrl);
        }
        catch(NumberFormatException e){
            System.out.println("Please enter valid values in text fields.");
        }

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
