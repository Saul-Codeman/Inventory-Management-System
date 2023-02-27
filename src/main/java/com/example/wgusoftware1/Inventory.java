package com.example.wgusoftware1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Inventory {

    // Declare Fields
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    // Add parts and products
    public static void addPart(Part part){
        allParts.add(part);
    }
    public static void addProduct(Product product){
        allProducts.add(product);
    }

    // Get Parts and products
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    //Lookup parts and products by ID
    public static Part lookupPart(int id){
        if(!allParts.isEmpty()){
            for(int i = 0; i < allParts.size(); i++){
                if(allParts.get(i).getId() == id){
                    return allParts.get(i);
                }
            }
        }
        return null;
    }

    public static Product lookupProduct(int id){
        if(!allProducts.isEmpty()){
            for(int i = 0; i < allProducts.size(); i++){
                if(allProducts.get(i).getId() == id){
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }
    //Lookup Parts and Products by name
    public static Part lookupPart(String name){
        if(!allParts.isEmpty()){
            for(int i = 0; i < allParts.size(); i++){
                if(allParts.get(i).getName() == name){
                    return allParts.get(i);
                }
            }
        }
        return null;
    }
    public static Product lookupProduct(String name){
        if(!allProducts.isEmpty()){
            for(int i = 0; i < allProducts.size(); i++){
                if(allProducts.get(i).getName() == name){
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }
    //Update parts and products
    public static void updatePart(int id, Part modifiedPart){
        int i = -1;
        for (Part part : getAllParts()){
            i++;
            if(part.getId() == id){
                getAllParts().set(i, modifiedPart);
                return;
            }
        }
    }
    public static void updateProduct(int id, Product modifiedProduct){
        int i = -1;
        for (Product product : getAllProducts()){
            i++;
            if(product.getId() == id){
                getAllProducts().set(i, modifiedProduct);
                return;
            }
        }
    }
    //Delete parts and products
    public static boolean deletePart(Part part){
        return allParts.remove(part);
    }
    public static boolean deleteProduct(Product product){
        return allProducts.remove(product);
    }

    // Searches for a part based on id or name
    public static ObservableList<Part> searchParts(String search){
        ObservableList<Part> Parts = FXCollections.observableArrayList();
        search = search.trim().toLowerCase();
        for (Part part : getAllParts()){
            if(part.getName().toLowerCase().contains(search) || Integer.toString(part.getId()).contains(search)){
                Parts.add(part);
            }
        }
        if (Parts.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Part not found");
            alert.showAndWait();
            return getAllParts();
        }else{
        return Parts;
        }
    }

    // Searches for products based on id or name
    public static ObservableList<Product> searchProducts(String search){
        ObservableList<Product> Products = FXCollections.observableArrayList();
        search = search.trim().toLowerCase();
        for (Product product : getAllProducts()){
            if(product.getName().toLowerCase().contains(search) || Integer.toString(product.getId()).contains(search)){
                Products.add(product);
            }
        }
        if (Products.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Product not found");
            alert.showAndWait();
            return getAllProducts();
        }else{
            return Products;
        }
    }

}
