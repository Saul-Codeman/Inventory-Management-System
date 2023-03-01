package com.example.wgusoftware1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class that contains the information about each product in inventory
 */
public class Product {

    // Declare Fields
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private Double price;
    private int stock;
    private int min;
    private int max;

    // Getters and Setters
    /**
     * Gets id
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets price
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * Sets price
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * Gets stock
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets stock
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Gets min
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets min
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets max
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets max
     * @param max that max to set
     */
    public void setMax(int max) {
        this.max = max;
    }


    /**
     * Product constructor
     * @param id of product constructed
     * @param name of product constructed
     * @param price of product constructed
     * @param stock of product constructed
     * @param min of product constructed
     * @param max of product constructed
     */
    public Product(int id, String name, Double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Adds the associated part to product
     * @param part to add to product
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * Deletes an associated part of the product
     * @param part to remove from product
     * @return associatedParts with removed part
     */
    public boolean deleteAssociatedPart(Part part){
        return associatedParts.remove(part);
    }

    /**
     * Returns all associated parts of the product
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}
