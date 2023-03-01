package com.example.wgusoftware1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

/**
 * ModifyProductController class used to modify products in the list of products
 */
public class ModifyProductController implements Initializable {

    /**
     * Button of the GUI interface
     */
    @FXML
    private Button modifyProductAddButton;

    /**
     * Button of the GUI interface
     */
    @FXML
    private Button modifyProductCancelButton;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, Integer> modifyProductIdCol;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, Integer> modifyProductIdCol2;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField modifyProductIdTxt;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, Integer> modifyProductInventoryCol;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, Integer> modifyProductInventoryCol2;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField modifyProductInventoryTxt;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField modifyProductMaxTxt;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField modifyProductMinTxt;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, String> modifyProductNameCol;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, String> modifyProductNameCol2;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField modifyProductNameTxt;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, Double> modifyProductPriceCol;

    /**
     * Table column of the GUI interface
     */
    @FXML
    private TableColumn<Part, Double> modifyProductPriceCol2;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField modifyProductPriceTxt;

    /**
     * Button of the GUI interface
     */
    @FXML
    private Button modifyProductRemoveButton;

    /**
     * Button of the GUI interface
     */
    @FXML
    private Button modifyProductSaveButton;

    /**
     * Text field of the GUI interface
     */
    @FXML
    private TextField modifyProductSearchTxt;

    /**
     * Table of the GUI interface
     */
    @FXML
    private TableView<Part> modifyProductTable;

    /**
     * Table of the GUI interface
     */
    @FXML
    private TableView<Part> modifyProductTable2;

    //Handler

    Stage stage;
    Parent scene;

    /**
     * Saving an original parts list in case the user makes changes and cancels
     */
    private ObservableList<Part> tempPartsList;

    /**
     * Adds part to temporary list and displays that to associated parts table
     * @param event Action on button
     * @implNote
     * FUTURE ENHANCEMENT add the associated part to a queue to be saved in the save handler later.
     * I ended up using a temporary list to store the associated parts. I would only add or remove from that list and set table 2 to match
     * LOGICAL ERROR: Old method had a bug that would still add parts even when canceled
     */
    @FXML
    void modifyProductAddHandler(ActionEvent event) {
        if (modifyProductTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to add");
            alert.showAndWait();
            return;
        }
        tempPartsList = FXCollections.observableArrayList(modifyProductTable2.getItems());
        Part part = modifyProductTable.getSelectionModel().getSelectedItem();
        tempPartsList.add(part);
        modifyProductTable2.setItems(tempPartsList);
    }

    /**
     * Cancels modification and returns to main
     * @param event action on button
     * @throws IOException catches RUNTIME ERROR
     * @implNote
     * LOGICAL ERROR: Old method had a bug that would still add or remove parts even when canceled
     */
    @FXML
    void modifyProductCancelHandler(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will clear all text field values, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            modifyProductTable2.setItems(tempPartsList);
            switchScreen(event, mainUrl);
        }
    }

    /**
     * Removes part from temporary parts list and displays on the associated parts table
     * @param event action on button
     * @implNote
     * FUTURE ENHANCEMENT add the associated part to a queue to be saved in the save handler later.
     * LOGICAL ERROR: Old method had a bug that would still remove parts even when canceled
     * Old method used: deleteSelectedPart(modifyProductTable2);
     */
    @FXML
    void modifyProductRemoveHandler(ActionEvent event) {
        if (modifyProductTable2.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to remove");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This will remove the associated part, do you want to continue?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK) {
            tempPartsList = FXCollections.observableArrayList(modifyProductTable2.getItems());
            Part part = modifyProductTable2.getSelectionModel().getSelectedItem();
            tempPartsList.remove(part);
            modifyProductTable2.setItems(tempPartsList);
        }
    }

    /**
     * Saves modified changes and returns to main
     * @param event action on button
     * @throws IOException catches runtime error at startup
     * @throws NumberFormatException RUNTIME ERROR when typing a non-numeric value into text fields
     */
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

    /**
     * Sends the selected product from main to the modify product and add product menu to display associated lists and elements
     * @param product action on table and button
     */
    public void sendProduct(Product product){
        setProductFields(product, modifyProductIdTxt, modifyProductNameTxt, modifyProductInventoryTxt, modifyProductPriceTxt, modifyProductMaxTxt, modifyProductMinTxt);

        setPartsTable(modifyProductTable, modifyProductIdCol, modifyProductNameCol, modifyProductInventoryCol, modifyProductPriceCol);
        setAssociatedPartsTable(modifyProductIdCol2, modifyProductInventoryCol2, modifyProductNameCol2, modifyProductPriceCol2, modifyProductTable2, product);
    }

    /**
     * Searches the parts list
     * @param event action on search bar like an enter key
     */
    @FXML
    void modifyProductSearchHandler(ActionEvent event) {
        searchPart(modifyProductSearchTxt, modifyProductTable);
    }

    /**
     * Initialize form
     * @param url of current form
     * @param resourceBundle bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
