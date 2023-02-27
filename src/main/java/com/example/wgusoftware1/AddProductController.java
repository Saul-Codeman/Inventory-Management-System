package com.example.wgusoftware1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
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
        addPartToTable2(addProductTable, addProductTable2, addProductIdCol);
    }

    @FXML
    void addProductCancelHandler(ActionEvent event) throws IOException {
        switchScreen(event, mainUrl);
    }

    @FXML
    void addProductRemoveHandler(ActionEvent event) {
        deleteSelectedPart(addProductTable2);
    }

    @FXML
    void addProductSaveHandler(ActionEvent event) throws IOException {
        // Take and save the data
        int id = Integer.parseInt(addProductIdTxt.getText());
        String name = addProductNameTxt.getText();
        int stock = Integer.parseInt(addProductInventoryTxt.getText());
        double price = Double.parseDouble(addProductPriceTxt.getText());
        int max = Integer.parseInt(addProductMaxTxt.getText());
        int min = Integer.parseInt(addProductMinTxt.getText());
        addProduct(new Product(id, name, price, stock, max, min));
        for(Part associatedPart : addProductTable2.getItems()) {
            lookupProduct(id).addAssociatedPart(associatedPart);
        }
        switchScreen(event, mainUrl);
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

