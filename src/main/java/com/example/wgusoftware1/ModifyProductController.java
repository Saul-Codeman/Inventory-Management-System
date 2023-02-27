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

import static com.example.wgusoftware1.Inventory.lookupProduct;
import static com.example.wgusoftware1.Library.*;

public class ModifyProductController implements Initializable {

    @FXML
    private Button modifyProductAddButton;

    @FXML
    private Button modifyProductCancelButton;

    @FXML
    private TableColumn<Part, Integer> modifyProductIdCol;

    @FXML
    private TableColumn<Part, Integer> modifyProductIdCol2;

    @FXML
    private TextField modifyProductIdTxt;

    @FXML
    private TableColumn<Part, Integer> modifyProductInventoryCol;

    @FXML
    private TableColumn<Part, Integer> modifyProductInventoryCol2;

    @FXML
    private TextField modifyProductInventoryTxt;

    @FXML
    private TextField modifyProductMaxTxt;

    @FXML
    private TextField modifyProductMinTxt;

    @FXML
    private TableColumn<Part, String> modifyProductNameCol;

    @FXML
    private TableColumn<Part, String> modifyProductNameCol2;

    @FXML
    private TextField modifyProductNameTxt;

    @FXML
    private TableColumn<Part, Double> modifyProductPriceCol;

    @FXML
    private TableColumn<Part, Double> modifyProductPriceCol2;

    @FXML
    private TextField modifyProductPriceTxt;

    @FXML
    private Button modifyProductRemoveButton;

    @FXML
    private Button modifyProductSaveButton;

    @FXML
    private TextField modifyProductSearchTxt;

    @FXML
    private TableView<Part> modifyProductTable;

    @FXML
    private TableView<Part> modifyProductTable2;

    //Handler

    Stage stage;
    Parent scene;

    @FXML
    void modifyProductAddHandler(ActionEvent event) {
        addPartToTable2(modifyProductTable, modifyProductTable2, modifyProductIdCol);
    }


    @FXML
    void modifyProductCancelHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            switchScreen(event, mainUrl);
        }
    }


    @FXML
    void modifyProductRemoveHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove the associated part, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            deleteSelectedPart(modifyProductTable2);
        }
    }

    @FXML
    void modifyProductSaveHandler(ActionEvent event) throws IOException{
        try {
            int id = Integer.parseInt(modifyProductIdTxt.getText());
            String name = modifyProductNameTxt.getText();
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Input fields cannot be empty.");
                alert.showAndWait();
                return;
            }
            int stock = Integer.parseInt(modifyProductInventoryTxt.getText());
            double price = Double.parseDouble(modifyProductPriceTxt.getText());
            int max = Integer.parseInt(modifyProductMaxTxt.getText());
            int min = Integer.parseInt(modifyProductMinTxt.getText());

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

            Inventory.updateProduct(id, new Product(id, name, price, stock, min, max));
            for (Part associatedPart : modifyProductTable2.getItems()) {
                lookupProduct(id).addAssociatedPart(associatedPart);
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

    public void sendProduct(Product product){
        setProductFields(product, modifyProductIdTxt, modifyProductNameTxt, modifyProductInventoryTxt, modifyProductPriceTxt, modifyProductMaxTxt, modifyProductMinTxt);

        setPartsTable(modifyProductTable, modifyProductIdCol, modifyProductNameCol, modifyProductInventoryCol, modifyProductPriceCol);
        setAssociatedPartsTable(modifyProductIdCol2, modifyProductInventoryCol2, modifyProductNameCol2, modifyProductPriceCol2, modifyProductTable2, product);
    }
    @FXML
    void modifyProductSearchHandler(ActionEvent event) {
        searchPart(modifyProductSearchTxt, modifyProductTable);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
