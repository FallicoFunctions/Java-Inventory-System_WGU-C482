package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Nicholas Fallico
 */

/** This is the product class. It is for the creation of products through the use of parts.*/
public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList(); //Create ObservableList for associatedParts in relation to their products
    private int productID;
    private String name;
    private double price = 0.0;
    private int inStock = 0;
    private int min;
    private int max;

    /** This is the Product constructor. */
    public Product(int productID, String name, double price, int inStock, int min, int max){
        setProductID(productID);
        setName(name);
        setPrice(price);
        setInStock(inStock);
        setMin(min);
        setMax(max);
    }

    /** This method obtains ProductID.
     @return productID
     */
    public int getProductID() {
        return this.productID;
    }

    /** This method sets the productID.
     @param productID the productID to set
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /** This method obtains the name.
     @return name
     */
    public String getName() {
        return this.name;
    }

    /** This method sets the name.
     @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /** This method obtains the price.
     @return price
     */
    public double getPrice() {
        return this.price;
    }

    /** This method sets the price.
     @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /** This method obtains the inventory in stock.
     @return inStock
     */
    public int getInStock() {
        return this.inStock;
    }

    /** This method sets the inventory in stock.
     @param inStock the inventory in stock to set
     */
    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    /** This method obtains the minimum inventory allowed in stock.
     @return min
     */
    public int getMin() {
        return this.min;
    }

    /** This method sets the minimum inventory allowed in stock.
     @param min the minimum inventory allowed in stock
     */
    public void setMin(int min) {
        this.min = min;
    }

    /** This method obtains the maximum inventory allowed in stock.
     @return max
     */
    public int getMax() {
        return this.max;
    }

    /** This method sets the maximum inventory allowed in stock.
     @param max the minimum inventory allowed in stock
     */
    public void setMax(int max) {
        this.max = max;
    }

    /** This method adds an associated part to a product.
     @param partToAdd associated part to add to a product
     */
    public void addAssociatedPart(Part partToAdd) {
        associatedParts.add(partToAdd);
    }

    /** This method deleted an associated part from a product.
     @param partToDelete associated part to delete from a product
     @return Returns true if associated part found and deleted from product and false if not found
     */
    public boolean deleteAssociatedPart(int partToDelete) {
        for (int i = 0; i < associatedParts.size(); i++) {
            if (associatedParts.get(i).getId() == partToDelete) {
                associatedParts.remove(i);
                return true;
            }
        }

        return false;
    }

    /** This method returns all associated parts.
     @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() { return associatedParts; }

    /** This method sets associated parts.
     @param associatedParts the associated parts ObservableList to set
     */
    public void setAssociatedPartsList(ObservableList<Part> associatedParts) {
        this.associatedParts = associatedParts;
    }

    /** This method checks that text fields are not empty when adding or modifying a product.
     @param name the text entry field for name on modify or add screens
     @param inStock the text entry field for amount of inventory on modify or add screens
     @param price the text entry field for price on modify or add screens
     @param max the text entry field for maximum inventory amount on modify or add screens
     @param min the text entry field for minimum inventory amount on modify or add screens
     @param errProductField String of text combining all of the error messages of the entry fields
     @return Returns errProductField which then displays the error messages stating text fields cannot be blank
     */
    public static String getEmptyFields(String name, String inStock, String price, String max, String min, String errProductField) {
        if (name.equals("")) {
            errProductField = errProductField + "The Product Name field cannot be empty. ";
        }
        if (inStock.equals("")) {
            errProductField = errProductField + "\nThe Product Inventory field cannot be empty. ";
        }
        if (price.equals("")) {
            errProductField = errProductField + "\nThe Product Price field cannot be empty. ";
        }
        if (max.equals("")) {
            errProductField = errProductField + "\nThe Product Max field cannot be empty. ";
        }
        if (min.equals("")) {
            errProductField = errProductField + "\nThe Product Min field cannot be empty. ";
        }
        return errProductField;
    }
}
