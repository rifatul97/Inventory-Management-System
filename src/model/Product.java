package model;

import javafx.collections.ObservableList;

/**
 * Product data model that contains associate parts.
 *
 * @author Rifatul Karim
 */
public class Product {

    /**
     * Holds the data of list of parts of a product.
     */
    private ObservableList<Part> associatedParts;

    /**
     * The Id for the part
     */
    private int id;

    /**
     * The name for the part
     */
    private String name;

    /**
     * The price for the part
     */
    private double price;

    /**
     * The Inventory Level data for the part
     */
    private int stock;

    /**
     * The Min value for the part
     */
    private int min;

    /**
     * The Max value for the part
     */
    private int max;

    /**
     *
     * @param id the id of a product
     * @param name the name of a product
     * @param price the price of a product
     * @param stock the stock value of a product
     * @param min the min value of a product
     * @param max the max value of a product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Stores all the associate part of a product
     *
     * @return returns all of the parts available on the inventory
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }

    /**
     * Set associate parts to a product
     *
     * @param associatedParts sets the associate part to the product
     */
    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

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
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
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
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

}
