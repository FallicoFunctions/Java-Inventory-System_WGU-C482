package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Nicholas Fallico
 */

/** This is the Inventory class. It is applicable to all parts and products in the system. */
public class Inventory {
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList(); //Create an ObservableList for products
    private static ObservableList<Part> allParts = FXCollections.observableArrayList(); //Create and ObservableList for parts
    private static int partID = 0;
    private static int productID = 0;

    /** This method adds a new part to the ObservableList named allParts.
     @param newPart new part to be added
     */
    public static void addPart(Part newPart) { //Add new part to parts list
        if (newPart != null) {
            allParts.add(newPart);
        }
    }

    /** This method adds a new product to the ObservableList named allProducts.
     @param newProduct new product to be added
     */
    public static void addProduct(Product newProduct) { //Add new product to products list
        if (newProduct != null) {
            allProducts.add(newProduct);
        }
    }

    /** This method searches through the ObservableList for parts. It searches using part ID.
     @param partId unique ID to identify a part
     @return allParts.get(i) returns the part if the partId is found
     @return returns null if partId is not found
     */
    public static Part lookUpPart(int partId) { //Search through the parts list to find a part using part ID
        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getId() == partId) {
                    return allParts.get(i);
                }
            }
        }
        return null;
    }

    /** This method searches through the ObservableList for products. It searched using a product ID.
     @param productId unique ID to identify a product
     @return allProducts.get(i) returns the product if the productId is found
     @return returns null if productId is not found
     */
    public static Product lookUpProduct(int productId) { //Search through the product list to find a product using product ID
        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allProducts.size(); i++) {
                if (allProducts.get(i).getProductID() == productId) {
                    return allProducts.get(i);
                }
            }
        }
        return null;
    }

    /** This method searches through the ObservableList for parts. It searches using part name.
     @param partName String of part name searched
     @return Returns list of parts that match the String
     */
    public static ObservableList<Part> lookUpPart(String partName) {  //Search through the part list using part name
        ObservableList<Part> nameList = FXCollections.observableArrayList();
        for (Part p:allParts) {
            if (p.getName().contains(partName)){
                nameList.add(p);
            }
        }
        return nameList;
    }

    /** This method searches through the ObservableList for products. It searches using product name.
     @param productName String of product name searched
     @return Returns list of products that match the String
     */
    public ObservableList<Product> lookUpProduct(String productName) { //Search through the product list using product name
        ObservableList<Product> productList = FXCollections.observableArrayList();
        for (Product p:allProducts) {
            if (p.getName().contains(productName)){
                productList.add(p);
            }
        }
        return productList;
    }

    /** This method updates a part in the allParts ObservableList.
     @param part the part to be updated
     @param partIndex the index of the part in the allParts ObservableList
     */
    public static void updatePart(int partIndex, Part part) { //Update part in allParts ObservableList
        allParts.set(partIndex, part);
    }

    /** This method updates a part in the allProducts ObservableList.
     @param product the product to be updated
     @param productIndex the index of the product in the allProducts ObservableList
     */
    public static void updateProduct(int productIndex, Product product) { //Update product in allProducts ObservableList
        allProducts.set(productIndex, product);
    }

    /** This method deletes a part from the allParts ObservableList.
     @param selectedPart the selected part to be deleted
     @return Return true removes part from the allParts ObservableList if the part is found
     @return Return false if the selected part is not found on the allParts ObservableList
     */
    public static boolean deletePart(Part selectedPart) { //Delete part from allParts ObservableList
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == selectedPart.getId()) {
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /** This method deletes a part from the allProducts ObservableList.
     @param selectedProduct the selected product to be deleted
     @return Return true removes product from the allProducts ObservableList if the product is found
     @return Return false if the selected product is not found on the allProducts ObservableList
     */
    public static boolean deleteProduct(Product selectedProduct) { //Delete product from allProducts ObservableList
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getProductID() == selectedProduct.getProductID()) {
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }

    /** This method returns the allParts ObservableList.
     @return Returns the allParts ObservableList
     */
    public static ObservableList<Part> getAllParts() { //Get list of allParts ObservableList
        return allParts;
    }

    /** This method returns the allProducts ObservableList.
     @return Returns the allProducts ObservableList
     */
    public static ObservableList<Product> getAllProducts() { //Get list of allProducts ObservableList
        return allProducts;
    }

    /** This method returns the size of the allParts ObservableList.
     @return Returns the size of the allParts ObservableList
     */
    public int partListSize() { //Get size of allParts ObservableList
        return allParts.size();
    }

    /** This method gets the current partID and increments it by one for a unique value.
     @return Returns incremented partID
     */
    public static int getPartIDCount() { //Get unique part ID and increment by one
        partID++;
        return partID;
    }

    /** This method gets the current productID and increments it by one for a unique value.
     @return Returns incremented productID
     */
    public static int getProductIDCount() { //Get unique product ID and increment by one
        productID++;
        return productID;
    }

    /** This method checks that text fields are not empty when adding or modifying a part.
     @param name the text entry field for name on modify or add screens
     @param inStock the text entry field for amount of inventory on modify or add screens
     @param price the text entry field for price on modify or add screens
     @param max the text entry field for maximum inventory amount on modify or add screens
     @param min the text entry field for minimum inventory amount on modify or add screens
     @param partDyn the text entry field for machineID or companyName on modify or add screens
     @param errPartField String of text combining all of the error messages of the entry fields
     @return Returns errPartField which then displays the error messages stating text fields cannot be blank
     */
    public static String getEmptyFields(String name, String inStock, String price, String max, String min, String partDyn, String errPartField) {
        if (name.equals("")) {
            errPartField = errPartField + "The Part Name field cannot be empty. ";
        }
        if (inStock.equals("")) {
            errPartField = errPartField + "\nThe Part Inventory field cannot be empty. ";
        }
        if (price.equals("")) {
            errPartField = errPartField + "\nThe Part Price field cannot be empty. ";
        }
        if (max.equals("")) {
            errPartField = errPartField + "\nThe Part Max field cannot be empty. ";
        }
        if (min.equals("")) {
            errPartField = errPartField + "\nThe Part Min field cannot be empty. ";
        }
        if (partDyn.equals("")) {
            errPartField = errPartField + "\nThe Part MachineID or Company Name field cannot be empty. ";
        }
        return errPartField;
    }

    /** This method checks the text entry fields for valid inputs on the modify or add screens for parts and products.
     @param inStock the text entry field for amount of inventory on modify or add screens
     @param price the text entry field for price on modify or add screens
     @param max the text entry field for maximum inventory amount on modify or add screens
     @param min the text entry field for minimum inventory amount on modify or add screens
     @param errPartValid String of text combining all of the error messages of the entry fields
     @return Returns errPartValid which then displays the error messages of invalid inputs
     */
    public static String getPartValidation(int inStock, double price, int max, int min, String errPartValid) {
        if (inStock < 1) {
            errPartValid = errPartValid + "\nThe Inventory cannot be less than 1. ";
        }
        if (price <= 0) {
            errPartValid = errPartValid + "\nThe Price must be greater than $0. ";
        }
        if (max < min) {
            errPartValid = errPartValid + "\nThe Maximum stock must be greater than the Minimum stock. ";
        }
        if (inStock > max) {
            errPartValid = errPartValid + "\nThe Inventory must be less than or equal to the Maximum stock. ";
        }
        if (inStock < min) {
            errPartValid = errPartValid + "\nThe Inventory must be greater than or equal to the Minimum stock. ";
        }
        return errPartValid;
    }
}
