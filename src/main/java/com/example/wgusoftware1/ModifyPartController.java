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
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.wgusoftware1.Library.*;

/**
 * ModifyPartController class used to modify parts in the list of parts
 */
public class ModifyPartController implements Initializable {

    /**
     * Button of the GUI interface
     */
    @FXML
    private Button modifyPartCancelButton;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField modifyPartIdTxt;

    /**
     * Radio button of the GUI interface
     */
    @FXML
    private RadioButton modifyPartInHouseButton;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField modifyPartInventoryTxt;

    /**
     * Toggle group of the GUI interface
     */
    @FXML
    private ToggleGroup modifyPartLocationTG;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField modifyPartMachineIdTxt;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField modifyPartMaxTxt;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField modifyPartMinTxt;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField modifyPartNameTxt;

    /**
     * Radio button of the GUI interface
     */
    @FXML
    private RadioButton modifyPartOutsourcedButton;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField modifyPartPriceTxt;

    /**
     * Button of the GUI interface
     */
    @FXML
    private Button modifyPartSaveButton;

    /**
     * Label of the GUI interface
     */
    @FXML
    private Label modifyPartUniqueLbl;

    //Handler
    Stage stage;
    Parent scene;

    /**
     *
     * @param event Changes the label to machine ID when activated
     */
    @FXML
    void modifyPartInHouseHandler(ActionEvent event) {
        modifyPartUniqueLbl.setText("Machine ID");
    }

    /**
     *
     * @param event Changes the label to Company Name when activated
     */
    @FXML
    void modifyPartOutsourcedHandler(ActionEvent event) {
        modifyPartUniqueLbl.setText("Company Name");
    }

    /**
     *
     * @param event Cancels changes to the part and returns to main
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void modifyPartCancelHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all changes made, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            switchScreen(event, mainUrl);
        }
    }

    /**
     *
     * @param event Saves the changes to the part and returns to main
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    void modifyPartSaveHandler(ActionEvent event) throws IOException{
        try {
            int id = Integer.parseInt(modifyPartIdTxt.getText());
            String name = modifyPartNameTxt.getText();
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Input fields cannot be empty.");
                alert.showAndWait();
                return;
            }
            // RUNTIME ERROR when typing a non-numeric value into text fields
            int stock = Integer.parseInt(modifyPartInventoryTxt.getText());
            double price = Double.parseDouble(modifyPartPriceTxt.getText());
            int max = Integer.parseInt(modifyPartMaxTxt.getText());
            int min = Integer.parseInt(modifyPartMinTxt.getText());

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Max must be greater than min.");
                alert.showAndWait();
                return;
            }
            if (stock > max || min > stock) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Inventory must be between max and min.");
                alert.showAndWait();
                return;
            }

            if (modifyPartInHouseButton.isSelected()) {
                int machineId = Integer.parseInt(modifyPartMachineIdTxt.getText());
                Inventory.updatePart(id, new InHouse(id, name, price, stock, min, max, machineId));
            } else {
                String companyName = modifyPartMachineIdTxt.getText();
                if (companyName.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Input fields cannot be empty.");
                    alert.showAndWait();
                    return;
                }
                Inventory.updatePart(id, new Outsourced(id, name, price, stock, min, max, companyName));
            }

            switchScreen(event, mainUrl);
        }
        catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Please enter a valid value for each input field");
            alert.showAndWait();
        }
    }

    /**
     *
     * @param part Collects the part from main and sends it through to the modify part controller to be modified
     */
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
