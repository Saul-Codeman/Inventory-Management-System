package com.example.wgusoftware1;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.wgusoftware1.Inventory.*;


public class Library {

    // URL strings
    public static final String addPartUrl = "/com/example/wgusoftware1/AddPart.fxml";
    public static final String addProductUrl = "/com/example/wgusoftware1/AddProduct.fxml";
    public static final String mainUrl = "/com/example/wgusoftware1/Main.fxml";
    public static final String modifyProductUrl = "/com/example/wgusoftware1/ModifyProduct.fxml";
    public static final String modifyPartUrl = "/com/example/wgusoftware1/ModifyPart.fxml";

    /*
    public boolean search(int id)
    {
        for (Part part : Inventory.getAllParts())
        {
            if(part.getId() == id) {
                return true;
            }
        }
        return false;
    }
     */
    public static void setPartsTable(TableView<Part> partTable, TableColumn<Part, Integer> partIdCol, TableColumn<Part, String> partNameCol, TableColumn<Part, Integer> partInventoryCol, TableColumn<Part, Double> partPriceCol){
        // Initialize Parts
        partTable.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
    public static void setProductsTable(TableView<Product> productTable, TableColumn<Product, Integer> productIdCol, TableColumn<Product, String> productNameCol, TableColumn<Product, Integer> productInventoryCol, TableColumn<Product, Double> productPriceCol) {
        productTable.setItems(getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public static void switchScreen(ActionEvent event, String url) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Library.class.getResource(url));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    public static void searchPart(TextField searchPartField, TableView<Part> partTableView){
        try{
            partTableView.setItems(searchParts(searchPartField.getText()));
        }catch (Exception e){
            if(partTableView.getItems().equals(getAllParts())){
                System.out.println("Hi");
            }
            else{
                partTableView.setItems(getAllParts());
            }
        }
    }
    public static void searchProduct(TextField searchProductField, TableView<Product> productTableView){
        try{
            productTableView.setItems(searchProducts(searchProductField.getText()));
        }catch (Exception e){
            if(productTableView.getItems().equals(getAllProducts())){
                System.out.println("Hi");
            }
            else{
                productTableView.setItems(getAllProducts());
            }
        }
    }
    // Delete Part
    public static void deleteSelectedPart(TableView<Part> partTableView) {
        if (partTableView.getSelectionModel().getSelectedItem() == null) {
            System.out.println("Please select an item to delete");
        } else {
            deletePart(partTableView.getSelectionModel().getSelectedItem(), partTableView.getItems());
            partTableView.setItems(partTableView.getItems());
        }
    }
    //Delete Associated Part
    public static boolean deletePart(Part selectedPart, ObservableList<Part> partObservableList) {
        return partObservableList.remove(selectedPart);
    }

    // Delete Product
    public static void deleteSelectedProduct(TableView<Product> productTableView){
        if (productTableView.getSelectionModel().getSelectedItem() == null){
            System.out.println("Please select an item to delete");
        }
        else{
            deleteProduct(productTableView.getSelectionModel().getSelectedItem());
            productTableView.setItems(getAllProducts());
        }
    }
    // Set text fields for Products
    public static void setProductFields(Product product, TextField id, TextField name, TextField inventory, TextField price, TextField max, TextField min) {
        id.setText(String.valueOf(product.getId()));
        name.setText((product.getName()));
        inventory.setText(String.valueOf(product.getStock()));
        price.setText(String.valueOf(product.getPrice()));
        max.setText((String.valueOf(product.getMax())));
        min.setText((String.valueOf(product.getMin())));
    }
    // Set text fields for Parts
    public static void setPartFields(Part part, TextField id, TextField name, TextField inventory, TextField price, TextField max, TextField min) {
        id.setText(String.valueOf(part.getId()));
        name.setText((part.getName()));
        inventory.setText(String.valueOf(part.getStock()));
        price.setText(String.valueOf(part.getPrice()));
        max.setText((String.valueOf(part.getMax())));
        min.setText((String.valueOf(part.getMin())));
    }

    // Modify Parts
    public static void modifySelectedPart(MainController mainController, TableView<Part> partTableView, ActionEvent event) throws IOException{
        if(partTableView.getSelectionModel().getSelectedItem() == null){
            System.out.println("No part selected");
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(mainController.getClass().getResource(modifyPartUrl));
            loader.load();
            ModifyPartController modifyPartController = loader.getController();
            modifyPartController.sendPart(partTableView.getSelectionModel().getSelectedItem());
            mainController.stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            mainController.stage.setScene(new Scene(scene));
            mainController.stage.show();
        }
    }

    //Modify Products
    public static void modifySelectedProduct(MainController mainController, TableView<Product> productTableView, ActionEvent event) throws IOException{
        if(productTableView.getSelectionModel().getSelectedItem() == null){
            System.out.println("No part selected");
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(mainController.getClass().getResource(modifyProductUrl));
            loader.load();
            ModifyProductController modifyProductController = loader.getController();
            modifyProductController.sendProduct(productTableView.getSelectionModel().getSelectedItem());
            mainController.stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            mainController.stage.setScene(new Scene(scene));
            mainController.stage.show();
        }
    }

    // Add part to table 2 of Modify Product controller
    public static void addPartToTable2(TableView<Part> partTable, TableView<Part> partTable2, TableColumn<Part, Integer> idCol){
        ObservableList<Part> parts;
        parts = partTable2.getItems();
        parts.addAll(partTable.getSelectionModel().getSelectedItems());
        partTable2.setItems(parts);
        partTable2.getSortOrder().add(idCol);
    }
    public static void setAssociatedPartsTable(TableColumn<Part, Integer> associatedPartIdCol, TableColumn<Part, Integer> associatedPartInvCol, TableColumn<Part, String> associatedPartNameCol, TableColumn<Part, Double> associatedPartPriceCol, TableView<Part> associatedPartTable, Product product) {
        associatedPartTable.setItems(product.getAllAssociatedParts());
        associatedPartTable.getSortOrder().add(associatedPartIdCol);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

}
