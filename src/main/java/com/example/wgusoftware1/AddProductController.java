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

import static com.example.wgusoftware1.Inventory.addProduct;
import static com.example.wgusoftware1.Inventory.lookupProduct;
import static com.example.wgusoftware1.Library.*;

public class AddProductController implements Initializable {

    // FXML
    @FXML
    private Button addProductAddButton;

    @FXML
    private Button addProductCancelButton;

    @FXML
    private TableColumn<Part, Integer> addProductIdCol;

    @FXML
    private TableColumn<Part, Integer> addProductIdCol2;

    @FXML
    private TextField addProductIdTxt;

    @FXML
    private TableColumn<Part, Integer> addProductInventoryCol;

    @FXML
    private TableColumn<Part, Integer> addProductInventoryCol2;

    @FXML
    private TextField addProductInventoryTxt;

    @FXML
    private TextField addProductMaxTxt;

    @FXML
    private TextField addProductMinTxt;

    @FXML
    private TableColumn<Part, String> addProductNameCol;

    @FXML
    private TableColumn<Part, String> addProductNameCol2;

    @FXML
    private TextField addProductNameTxt;

    @FXML
    private TableColumn<Part, Double> addProductPriceCol;

    @FXML
    private TableColumn<Part, Double> addProductPriceCol2;

    @FXML
    private TextField addProductPriceTxt;

    @FXML
    private Button addProductRemoveButton;

    @FXML
    private Button addProductSaveButton;

    @FXML
    private TextField addProductSearchTxt;

    @FXML
    private TableView<Part> addProductTable;

    @FXML
    private TableView<Part> addProductTable2;

    // Handler
    Stage stage;
    Parent scene;

    @FXML
    void addProductAddHandler(ActionEvent event) {
        if (addProductTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to add");
            alert.showAndWait();
            return;
        }
        addPartToTable2(addProductTable, addProductTable2, addProductIdCol);
    }

    @FXML
    void addProductCancelHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            switchScreen(event, mainUrl);
        }
    }

    @FXML
    void addProductRemoveHandler(ActionEvent event) {
        if (addProductTable2.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to remove");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove the associated part, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            deleteSelectedPart(addProductTable2);
        }
    }

    @FXML
    void addProductSaveHandler(ActionEvent event) throws IOException {
        // Take and save the data
        try {
            int id;
            String name = addProductNameTxt.getText();
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Input fields cannot be empty.");
                alert.showAndWait();
                return;
            }
            int stock = Integer.parseInt(addProductInventoryTxt.getText());
            double price = Double.parseDouble(addProductPriceTxt.getText());
            int max = Integer.parseInt(addProductMaxTxt.getText());
            int min = Integer.parseInt(addProductMinTxt.getText());

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
            id = autoProductGenId();
            addProduct(new Product(id, name, price, stock, min, max));
            for (Part associatedPart : addProductTable2.getItems()) {
                lookupProduct(id).addAssociatedPart(associatedPart);
            }
            switchScreen(event, mainUrl);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialogue");
            alert.setContentText("Please enter a valid value for each input field");
            alert.showAndWait();
        }
    }
    @FXML
    void addProductSearchHandler(ActionEvent event) {
        searchPart(addProductSearchTxt, addProductTable);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductTable.setItems(Inventory.getAllParts());

        addProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));


        addProductIdCol2.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductNameCol2.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInventoryCol2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCol2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

