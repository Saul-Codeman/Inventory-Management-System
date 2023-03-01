package com.example.wgusoftware1;

/**
 * Outsourced class that is an extension of part that is used to specify the source of the part
 */
public class Outsourced extends Part {

    // Declare Field
    private String companyName;


    // Constructor

    /**
     * Outsourced constructor
     * @param id of part constructed
     * @param name of part constructed
     * @param price of part constructed
     * @param stock of part constructed
     * @param min of part constructed
     * @param max of part constructed
     * @param companyName of part constructed
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    // Getters and Setters

    /**
     * Get company name
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * set company name
     * @param companyName to be set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
