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


/**
 * Library Class used to store commonly used functions throughout the controllers
 */
public class Library {

    /**
     * URL strings for the switchView function
     */
    public static final String addPartUrl = "/com/example/wgusoftware1/AddPart.fxml";
    public static final String addProductUrl = "/com/example/wgusoftware1/AddProduct.fxml";
    public static final String mainUrl = "/com/example/wgusoftware1/Main.fxml";
    public static final String modifyProductUrl = "/com/example/wgusoftware1/ModifyProduct.fxml";
    public static final String modifyPartUrl = "/com/example/wgusoftware1/ModifyPart.fxml";


    /**
     *
     * @param partTable sets table
     * @param partIdCol sets id in table
     * @param partNameCol sets name in table
     * @param partInventoryCol sets inventory in table
     * @param partPriceCol sets price in table
     * Displays all parts to the parts tables
     */
    public static void setPartsTable(TableView<Part> partTable, TableColumn<Part, Integer> partIdCol, TableColumn<Part, String> partNameCol, TableColumn<Part, Integer> partInventoryCol, TableColumn<Part, Double> partPriceCol){
        // Initialize Parts
        partTable.setItems(Inventory.getAllParts());

        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     *
     * @param productTable sets table
     * @param productIdCol sets id in table
     * @param productNameCol sets name in table
     * @param productInventoryCol sets inventory in table
     * @param productPriceCol sets price in table
     * Displays all products to the products tables
     */
    public static void setProductsTable(TableView<Product> productTable, TableColumn<Product, Integer> productIdCol, TableColumn<Product, String> productNameCol, TableColumn<Product, Integer> productInventoryCol, TableColumn<Product, Double> productPriceCol) {
        productTable.setItems(getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     *
     * @param event Changes the scene to the url provided upon an action
     * @param url url of different form
     * @throws IOException catches RUNTIME ERROR
     *
     */
    public static void switchScreen(ActionEvent event, String url) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(Library.class.getResource(url));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     *
     * @param searchPartField search field on form
     * @param partTableView table to search
     * Searches for a part id or name in the text field using the searchParts function and returns any part matching,
     * or it will return all if the part is not found
     */
    public static void searchPart(TextField searchPartField, TableView<Part> partTableView){
        try{
            partTableView.setItems(searchParts(searchPartField.getText()));
        }catch (Exception e){
            if(partTableView.getItems().equals(getAllParts())){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Search field empty");
                alert.showAndWait();
                partTableView.setItems(getAllParts());
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found");
                alert.showAndWait();
                partTableView.setItems(getAllParts());
            }
        }
    }

    /**
     *
     * @param searchProductField search field on form
     * @param productTableView table to search
     * Searches for a product id or name in the text field using the searchProducts function and returns any part matching,
     * or it will return all if the product is not found
     */
    public static void searchProduct(TextField searchProductField, TableView<Product> productTableView){
        try{
            productTableView.setItems(searchProducts(searchProductField.getText()));
        }catch (Exception e){
            if(productTableView.getItems().equals(getAllProducts())){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Search field empty");
                alert.showAndWait();
                productTableView.setItems(getAllProducts());
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product not found");
                alert.showAndWait();
                productTableView.setItems(getAllProducts());
            }
        }
    }

    /**
     *
     * @param partTableView table to delete from
     * Deletes the part from the table
     */
    public static void deleteSelectedPart(TableView<Part> partTableView) {
        if (partTableView.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to delete");
            alert.showAndWait();
        } else {
            deletePart(partTableView.getSelectionModel().getSelectedItem(), partTableView.getItems());
            partTableView.setItems(partTableView.getItems());
        }
    }

    /**
     *
     * @param selectedPart selected part in table
     * @param partObservableList list of parts
     * @return partObservableList.remove(selectedPart)
     * Deletes the associated part in the modify or add product menus
     */
    public static boolean deletePart(Part selectedPart, ObservableList<Part> partObservableList) {
        return partObservableList.remove(selectedPart);
    }

    /**
     *
     * @param productTableView table to delete from
     * Deletes the selected product in the table
     */
    public static void deleteSelectedProduct(TableView<Product> productTableView){
        if (productTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to delete");
            alert.showAndWait();
        }
        else{
            deleteProduct(productTableView.getSelectionModel().getSelectedItem());
            productTableView.setItems(getAllProducts());
        }
    }

    /**
     *
     * @param product product to set
     * @param id set field to product
     * @param name set field to product
     * @param inventory set field to product
     * @param price set field to product
     * @param max set field to product
     * @param min set field to product
     * Set text fields for Products
     */
    public static void setProductFields(Product product, TextField id, TextField name, TextField inventory, TextField price, TextField max, TextField min) {
        id.setText(String.valueOf(product.getId()));
        name.setText((product.getName()));
        inventory.setText(String.valueOf(product.getStock()));
        price.setText(String.valueOf(product.getPrice()));
        max.setText((String.valueOf(product.getMax())));
        min.setText((String.valueOf(product.getMin())));
    }

    /**
     *
     * @param part part to set
     * @param id set field to part
     * @param name set field to part
     * @param inventory set field to part
     * @param price set field to part
     * @param max set field to part
     * @param min set field to part
     * Set text fields for Parts
     */
    public static void setPartFields(Part part, TextField id, TextField name, TextField inventory, TextField price, TextField max, TextField min) {
        id.setText(String.valueOf(part.getId()));
        name.setText((part.getName()));
        inventory.setText(String.valueOf(part.getStock()));
        price.setText(String.valueOf(part.getPrice()));
        max.setText((String.valueOf(part.getMax())));
        min.setText((String.valueOf(part.getMin())));
    }

    /**
     *
     * @param mainController location to modify
     * @param partTableView table of select part
     * @param event Takes the part in the selected table and sends it via the sendPart function to the modify part controller
     * @throws IOException catches RUNTIME ERROR
     */
    public static void modifySelectedPart(MainController mainController, TableView<Part> partTableView, ActionEvent event) throws IOException{
        if(partTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a part to modify");
            alert.showAndWait();
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

    /**
     *
     * @param mainController location to modify
     * @param productTableView table of select product
     * @param event Takes the product in the selected table and sends it via the sendProduct function to the modify product controller
     * @throws IOException catches RUNTIME ERROR
     */
    public static void modifySelectedProduct(MainController mainController, TableView<Product> productTableView, ActionEvent event) throws IOException{
        if(productTableView.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product to modify");
            alert.showAndWait();
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

    /**
     *
     * @param partTable initial table to add from
     * @param partTable2 table to add to
     * @param idCol know part based on id
     * Add part to table 2 in the Modify Product controller menu
     */
    public static void addPartToTable2(TableView<Part> partTable, TableView<Part> partTable2, TableColumn<Part, Integer> idCol){
        ObservableList<Part> parts;
        parts = partTable2.getItems();
        parts.addAll(partTable.getSelectionModel().getSelectedItems());
        partTable2.setItems(parts);
        partTable2.getSortOrder().add(idCol);
    }
    // Remove part from table 2 in the modify Product controller menu
    /*
    public static void removePartFromTable2(TableView<Part> partTable2, TableColumn<Part, Integer> idCol){
        ObservableList<Part> parts;
        parts = partTable2.getItems();
        parts.remove(partTable2.getSelectionModel().getSelectedItem());
    }
     */

    /**
     *
     * @param associatedPartIdCol sets field to associated part
     * @param associatedPartInvCol sets field to associated part
     * @param associatedPartNameCol sets field to associated part
     * @param associatedPartPriceCol sets field to associated part
     * @param associatedPartTable table to set
     * @param product product with associated part
     * Sets the table of the associated parts in the modify product controller menu
     */
    public static void setAssociatedPartsTable(TableColumn<Part, Integer> associatedPartIdCol, TableColumn<Part, Integer> associatedPartInvCol, TableColumn<Part, String> associatedPartNameCol, TableColumn<Part, Double> associatedPartPriceCol, TableView<Part> associatedPartTable, Product product) {
        associatedPartTable.setItems(product.getAllAssociatedParts());
        associatedPartTable.getSortOrder().add(associatedPartIdCol);
        associatedPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * static int part and product id for tracking unique ids
     * FUTURE ENHANCEMENT: Could use a list of unique ids so that when one id is removed that specific id
     * could be used again. The current version only increments and does not account for removal of an id
     */
    private static int partId = 0;
    private static int productId = 0;

    /**
     *
     * @return incremented partId
     * Generates an incrementing id for parts
     */
    public static int autoPartGenId(){
        return ++partId;
    }

    /**
     *
     * @return incremented productId
     * Generates an incrementing id for products
     */
    public static int autoProductGenId(){
        return ++productId;
    }


}
