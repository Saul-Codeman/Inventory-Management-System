package com.example.wgusoftware1;

public class InHouse extends Part {

    // Declare Field
    private int machineId;

    // InHouse constructor

    /**
     *
     * @param id of part constructed
     * @param name of part constructed
     * @param price of part constructed
     * @param stock of part constructed
     * @param min of part constructed
     * @param max of part constructed
     * @param machineId of part constructed
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    // Getter and Setter

    /**
     *
     * @return machineId
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     *
     * @param machineId to be set
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}