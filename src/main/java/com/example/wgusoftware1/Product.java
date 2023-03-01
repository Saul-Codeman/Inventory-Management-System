package com.example.wgusoftware1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public Double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max that max to set
     */
    public void setMax(int max) {
        this.max = max;
    }


    /**
     *
     * @param id of product constructed
     * @param name of product constructed
     * @param price of product constructed
     * @param stock of product constructed
     * @param min of product constructed
     * @param max of product constructed
     *            Constructor
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
     *
     * @param part to add to product
     * Adds the associated part to product
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     *
     * @param part to remove from product
     * @return associatedParts with removed part
     * Deletes an associated part of the product
     */
    public boolean deleteAssociatedPart(Part part){
        return associatedParts.remove(part);
    }

    /**
     *
     * @return associatedParts
     * Returns all associated parts of the product
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}
