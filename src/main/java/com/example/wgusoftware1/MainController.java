package com.example.wgusoftware1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.wgusoftware1.Library.*;

public class MainController extends Application implements Initializable {

    //FXML
    @FXML
    private Button mainExitButton;

    @FXML
    private Button mainPartAddButton;

    @FXML
    private Button mainPartDeleteButton;

    @FXML
    private TableColumn<Part, Integer> mainPartIdCol;

    @FXML
    private TableColumn<Part, Integer> mainPartInventoryCol;

    @FXML
    private Button mainPartModifyButton;

    @FXML
    private TableColumn<Part, String> mainPartNameCol;

    @FXML
    private TableColumn<Part, Double> mainPartPriceCol;

    @FXML
    private TextField mainPartSearchTxt;

    @FXML
    private TableView<Part> mainPartTable;

    @FXML
    private Button mainProductAddButton;

    @FXML
    private Button mainProductDeleteButton;

    @FXML
    private TableColumn<Product, Integer> mainProductIdCol;

    @FXML
    private TableColumn<Product, Integer> mainProductInventoryCol;

    @FXML
    private Button mainProductModifyButton;

    @FXML
    private TableColumn<Product, String> mainProductNameCol;

    @FXML
    private TableColumn<Product, Double> mainProductPriceCol;

    @FXML
    private TextField mainProductSearchTxt;

    @FXML
    private TableView<Product> mainProductTable;

    //Handlers

    Stage stage;
    Parent scene;

    @FXML
    void mainExitHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to close the application?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    @FXML
    void partAddHandler(ActionEvent event) throws IOException {
        switchScreen(event, addPartUrl);
    }

    @FXML
    void partDeleteHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the part?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            deleteSelectedPart(mainPartTable);
        }
    }

    @FXML
    void partModifyHandler(ActionEvent event) throws IOException {
        modifySelectedPart(this, mainPartTable, event);
    }

    @FXML
    void partSearchHandler(ActionEvent event) {
        searchPart(mainPartSearchTxt, mainPartTable);
    }

    @FXML
    void productAddHandler(ActionEvent event) throws IOException {
        switchScreen(event, addProductUrl);
    }

    @FXML
    void productDeleteHandler(ActionEvent event) {
        if (mainProductTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to delete");
            alert.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the product?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            if(mainProductTable.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()) {
                deleteSelectedProduct(mainProductTable);
            }
            else{
                alert = new Alert(Alert.AlertType.WARNING, "Cannot delete a product with an associated part");
                alert.showAndWait();
            }
        }
    }

    @FXML
    void productModifyHandler(ActionEvent event) throws IOException {
        modifySelectedProduct(this, mainProductTable,event);
    }

    @FXML
    void productSearchHandler(ActionEvent event) {
        searchProduct(mainProductSearchTxt, mainProductTable);
    }

    //Methods

    @Override
    public void init(){
        System.out.println("Initialized");
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainController.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 500);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        Product product1 = new Product(autoProductGenId(),"Giant Bike", 299.99, 5, 1, 10);
        Product product2 = new Product(autoProductGenId(), "Tricycle", 99.99, 3, 1, 10);
        InHouse inPart1 = new InHouse(autoPartGenId(),"Brakes", 15.00, 10, 2, 100, 100);
        InHouse inPart2 = new InHouse(autoPartGenId(),"Wheel", 11.00, 16, 2, 200, 200);
        Outsourced outPart1 = new Outsourced(autoPartGenId(), "Seat", 15.00, 10, 2, 200, "ABC Company");


        Inventory.addPart(inPart1);
        Inventory.addPart(inPart2);
        Inventory.addPart(outPart1);

        Inventory.addProduct(product1);
        Inventory.addProduct(product2);

        product1.addAssociatedPart(inPart1);
        product1.addAssociatedPart(inPart2);
        product2.addAssociatedPart(inPart1);
        product2.addAssociatedPart(inPart2);


        launch(args);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize Parts
        setPartsTable(mainPartTable, mainPartIdCol, mainPartNameCol, mainPartInventoryCol, mainPartPriceCol);

        //Initialize Products
        setProductsTable(mainProductTable, mainProductIdCol, mainProductNameCol, mainProductInventoryCol, mainProductPriceCol);

    }
}