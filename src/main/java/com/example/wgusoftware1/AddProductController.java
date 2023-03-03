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

/**
 * AddProductController class used to add products to the list of products
 */
public class AddProductController implements Initializable {


    /**
     * Button of the GUI interface
     */
    @FXML
    private Button addProductAddButton;

    /**
     * Button of the GUI interface
     */
    @FXML
    private Button addProductCancelButton;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, Integer> addProductIdCol;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, Integer> addProductIdCol2;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField addProductIdTxt;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, Integer> addProductInventoryCol;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, Integer> addProductInventoryCol2;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField addProductInventoryTxt;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField addProductMaxTxt;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField addProductMinTxt;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, String> addProductNameCol;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, String> addProductNameCol2;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField addProductNameTxt;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, Double> addProductPriceCol;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, Double> addProductPriceCol2;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField addProductPriceTxt;

    /**
     * Button of the GUI interface
     */
    @FXML
    private Button addProductRemoveButton;

    /**
     * Button of the GUI interface
     */
    @FXML
    private Button addProductSaveButton;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField addProductSearchTxt;

    /**
     * Table of the GUI interface
     */
    @FXML
    private TableView<Part> addProductTable;

    /**
     * Table of the GUI interface
     */
    @FXML
    private TableView<Part> addProductTable2;

    // Handler
    Stage stage;
    Parent scene;

    /**
     * Adds the associated part to the product
     * @param event action on button
     */
    @FXML
    public void addProductAddHandler(ActionEvent event) {
        if (addProductTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to add");
            alert.showAndWait();
            return;
        }
        addPartToTable2(addProductTable, addProductTable2, addProductIdCol);
    }

    /**
     * Cancels the addition of a new product and returns to main
     * @param event action on button
     * @throws IOException catches RUNTIME ERROR
     */
    @FXML
    public void addProductCancelHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            switchScreen(event, mainUrl);
        }
    }

    /**
     * Removes the associated product from the new product
     * @param event action on button
     */
    @FXML
    public void addProductRemoveHandler(ActionEvent event) {
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

    /**
     * Saves the new product and returns to main
     * @param event action on button
     * @throws IOException catches RUNTIME ERROR
     * @throws NumberFormatException catches RUNTIME ERROR
     */
    @FXML
    public void addProductSaveHandler(ActionEvent event) throws IOException {
        // Take and save the data
        try {
            int id;
            String name = addProductNameTxt.getText();
            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Input fields cannot be empty.");
                alert.showAndWait();
                return;
            }
            // RUNTIME ERROR when typing a non-numeric value into text fields
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

    /**
     * Searches for part
     * @param event action on text field like enter key
     */
    @FXML
    public void addProductSearchHandler(ActionEvent event) {
        searchPart(addProductSearchTxt, addProductTable);
    }

    /**
     * Initialize form
     * @param url of current form
     * @param resourceBundle bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Sets up both the parts and associated parts tables
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

