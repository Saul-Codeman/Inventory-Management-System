package com.example.wgusoftware1;

public class InHouse extends Part {

    // Declare Field
    private int machineId;

    // InHouse constructor
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    // Getter and Setter
    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}