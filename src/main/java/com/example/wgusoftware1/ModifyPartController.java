package com.example.wgusoftware1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.example.wgusoftware1.Library.*;

public class ModifyPartController implements Initializable {

    //FXML

    @FXML
    private Button modifyPartCancelButton;

    @FXML
    private TextField modifyPartIdTxt;

    @FXML
    private RadioButton modifyPartInHouseButton;

    @FXML
    private TextField modifyPartInventoryTxt;

    @FXML
    private ToggleGroup modifyPartLocationTG;

    @FXML
    private TextField modifyPartMachineIdTxt;

    @FXML
    private TextField modifyPartMaxTxt;

    @FXML
    private TextField modifyPartMinTxt;

    @FXML
    private TextField modifyPartNameTxt;

    @FXML
    private RadioButton modifyPartOutsourcedButton;

    @FXML
    private TextField modifyPartPriceTxt;

    @FXML
    private Button modifyPartSaveButton;

    @FXML
    private Label modifyPartUniqueLbl;

    //Handler
    Stage stage;
    Parent scene;

    @FXML
    void modifyPartInHouseHandler(ActionEvent event) {
        modifyPartUniqueLbl.setText("Machine ID");
    }

    @FXML
    void modifyPartOutsourcedHandler(ActionEvent event) {
        modifyPartUniqueLbl.setText("Company Name");
    }

    @FXML
    void modifyPartCancelHandler(ActionEvent event) throws IOException {
        switchScreen(event, mainUrl);
    }

    @FXML
    void modifyPartSaveHandler(ActionEvent event) throws IOException{
        int id = Integer.parseInt(modifyPartIdTxt.getText());
        String name = modifyPartNameTxt.getText();
        int stock = Integer.parseInt(modifyPartInventoryTxt.getText());
        double price = Double.parseDouble(modifyPartPriceTxt.getText());
        int max = Integer.parseInt(modifyPartMaxTxt.getText());
        int min = Integer.parseInt(modifyPartMinTxt.getText());

        if (modifyPartInHouseButton.isSelected()) {
            int machineId = Integer.parseInt(modifyPartMachineIdTxt.getText());
            Inventory.updatePart(id, new InHouse(id, name, price, stock, min, max, machineId));
        }
        else{
            String companyName = modifyPartMachineIdTxt.getText();
            Inventory.updatePart(id, new Outsourced(id, name, price, stock, min, max, companyName));
        }

        switchScreen(event, mainUrl);
    }

    public void sendPart(Part part){

        setPartFields(part, modifyPartIdTxt, modifyPartNameTxt, modifyPartInventoryTxt, modifyPartPriceTxt, modifyPartMaxTxt, modifyPartMinTxt);

        if(part instanceof InHouse) {
            modifyPartInHouseButton.setSelected(true);
            modifyPartUniqueLbl.setText("Machine ID");
            modifyPartMachineIdTxt.setText(String.valueOf(((InHouse) part).getMachineId()));
        }
        else {
            modifyPartOutsourcedButton.setSelected(true);
            modifyPartUniqueLbl.setText("Company Name");
            modifyPartMachineIdTxt.setText(((Outsourced) part).getCompanyName());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
