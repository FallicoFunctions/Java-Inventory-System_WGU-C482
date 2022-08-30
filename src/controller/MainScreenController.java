package controller;

/**
 * @author Nicholas Fallico
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static model.Inventory.getAllParts;
import static model.Inventory.getAllProducts;

/** This is the main screen controller class. It handles the interaction with the main screen.
 <p><b>
 This is my answer for a compatible feature for this application. Currently when we add an associated part to a product
 it does not deduct from the part inventory. An update to this application would deduct used parts from the inventory
 as would happen in a real world scenario.
 </b></p>
 */
public class MainScreenController implements Initializable {
    public TextField searchParts;
    public TableView partsTable;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInvCol;
    public TableColumn partPriceCol;
    public TextField searchProducts;
    public TableView productsTable;
    public TableColumn productIDCol;
    public TableColumn productNameCol;
    public TableColumn productInvCol;
    public TableColumn productPriceCol;

    Stage stage;
    Parent scene;

    public static Part selectedPart;
    public static int selectedPartIndex;

    /** This method obtains the selected part.
     @return selectedPart
     */
    public static Part getSelectedPart() {
        return selectedPart;
    }
    public static Product selectedProduct;
    public static int selectedProductIndex;

    /** This method obtains the selected Product.
     @return selectedProduct
     */
    public static Product getSelectedProduct() { return selectedProduct; }

    /** This method handles searching the parts table.
     @param actionEvent click the search button to search for a part
     */
    public void searchHandler1(ActionEvent actionEvent) {
        String searchField = searchParts.getText(); //Gets text entered into search field
        if (searchField.equals("")){ //If text field is blank and search is clicked it loads all parts into the table
            partsTable.setItems(Inventory.getAllParts());
            return;
        }
        else {
            boolean found=false;
            try {
                Part searchPart = Inventory.lookUpPart(Integer.parseInt(searchField)); //Parse text field for number. Lookup part by ID
                if (searchPart != null) { //If a number is found in the text field search for partID
                    ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
                    filteredPartsList.add(searchPart);
                    partsTable.setItems(filteredPartsList);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING); //Give warning saying no matching part ID
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("There were no parts found!");
                    alert.setContentText("The search term entered does not match any part ID!");
                    alert.showAndWait();
                }
            }
            catch (NumberFormatException e) { //Search part by text name
                ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
                for (Part p : getAllParts()) {
                    if (p.getName().contains(searchField)) {
                        found=true;
                        filteredPartsList.add(p);
                        partsTable.setItems(filteredPartsList);
                    }
                }
                if (!found) { //If neither ID or text name are not found give warning stating so
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Search Warning");
                    alert.setHeaderText("There were no parts found!");
                    alert.setContentText("The search term entered does not match any part name!");
                    alert.showAndWait();
                }
            }
        }
    }

    /** This method deletes a selected part from the part table.
     @param actionEvent click the delete button to delete selected part
     */
    public void deleteHandler1(ActionEvent actionEvent) {
        selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem(); //Select part in table
        if (selectedPart == null){ //If no part is selected to delete give error stating so
            Alert nullalert = new Alert(Alert.AlertType.ERROR);
            nullalert.setTitle("Part Deletion Error");
            nullalert.setHeaderText("The part is NOT able to be deleted!");
            nullalert.setContentText("There was no part selected!");
            nullalert.showAndWait();
        }
        else {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //Confirm deletion request
                alert.setTitle("Delete Part");
                alert.setHeaderText("Are you sure you want to delete?");
                alert.setContentText("Press OK to delete part. \nPress Cancel to keep part.");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) { //If OK is selected then part is deleted
                    Inventory.deletePart(selectedPart);
                    System.out.println(selectedPart);
                    System.out.println(getAllParts());
                } else {
                    alert.close();
                }
            }
            catch (Exception e) {}
        }
    }

    /** This method handles modify requests for parts on the parts table.
     @param actionEvent click the modify button to modify a part
     */
    public void modifyHandler1(ActionEvent actionEvent) throws IOException {
        selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem(); //Select part from parts table
        selectedPartIndex = getAllParts().indexOf(selectedPart);

        if (selectedPart == null) { //If no part is selected state so
            Alert nullAlert = new Alert(Alert.AlertType.ERROR);
            nullAlert.setTitle("Part Modification Error");
            nullAlert.setHeaderText("The part is NOT able to be modified!");
            nullAlert.setContentText("There was no part selected!");
            nullAlert.showAndWait();
        }
        else {
            try { //If part is selected bring user to modify part page
                stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/modify_part.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            catch (IOException e) {}
        }
    }

    /** This method brings the user to the add part page.
     @param actionEvent click the add button to go to the add part page
     */
    public void addHandler1(ActionEvent actionEvent) throws IOException {
         stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow(); //Load add part window
         scene = FXMLLoader.load(getClass().getResource("/view/add_part.fxml"));
         stage.setScene(new Scene(scene));
         stage.show();
    }

    /** This method handles searching the products table.
     @param actionEvent click the search button to search for a product
     */
    public void searchHandler2(ActionEvent actionEvent) {
        String searchField = searchProducts.getText(); //Gets text entered into search field
        if (searchField.equals("")){ //If text field is blank and search is clicked it will load all products into the table
            productsTable.setItems(Inventory.getAllProducts());
            return;
        }
        else {
            boolean found=false;
            try {
                Product searchProduct = Inventory.lookUpProduct(Integer.parseInt(searchField)); //Parse text field for number. Lookup product by ID
                if (searchProduct != null) { //If a number is found in the text field search for productID
                    ObservableList<Product> filteredProductsList = FXCollections.observableArrayList();
                    filteredProductsList.add(searchProduct);
                    productsTable.setItems(filteredProductsList);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING); //Give warning saying no matching product ID
                    alert.setTitle("Product Search Warning");
                    alert.setHeaderText("There were no products found!");
                    alert.setContentText("The search term entered does not match any product ID!");
                    alert.showAndWait();
                }
            }
            catch (NumberFormatException e) { //Search product by text name
                ObservableList<Product> filteredProductsList = FXCollections.observableArrayList();
                for (Product p : getAllProducts()) {
                    if (p.getName().contains(searchField)) {
                        found=true;
                        filteredProductsList.add(p);
                        productsTable.setItems(filteredProductsList);
                    }
                }
                if (!found) { //If neither ID or text name are not found give warning stating so
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Product Search Warning");
                    alert.setHeaderText("There were no products found!");
                    alert.setContentText("The search term entered does not match any product name!");
                    alert.showAndWait();
                }
            }
        }
    }

    /** This method deletes a selected product from the part table.
     @param actionEvent click the delete button to delete selected product
     */
    public void deleteHandler2(ActionEvent actionEvent) {
        selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem(); //Select product in table
        if (selectedProduct == null){ //If no product is selected to delete give error stating so
            Alert nullalert = new Alert(Alert.AlertType.ERROR);
            nullalert.setTitle("Product Deletion Error");
            nullalert.setHeaderText("The product is NOT able to be deleted!");
            nullalert.setContentText("There was no product selected!");
            nullalert.showAndWait();
        }
        else {
            try {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //Confirm deletion request
                alert.setTitle("Delete Product");
                alert.setHeaderText("Are you sure you want to delete?");
                alert.setContentText("Press OK to delete product. \nPress Cancel to keep product.");
                alert.showAndWait();

                if (alert.getResult() == ButtonType.OK) { //If OK is selected then part is deleted
                    Inventory.deleteProduct(selectedProduct);
                    System.out.println(selectedProduct);
                    System.out.println(getAllProducts());
                } else {
                    alert.close();
                }
            }
            catch (Exception e) {}
        }
    }

    /** This method handles modify requests for parts on the products table.
     @param actionEvent click the modify button to modify a product
     */
    public void modifyHandler2(ActionEvent actionEvent) throws IOException {
        selectedProduct = (Product) productsTable.getSelectionModel().getSelectedItem(); //Select product from parts table
        selectedProductIndex = getAllProducts().indexOf(selectedProduct);

        if (selectedProduct == null) { //If no product is selected state so
            Alert nullAlert = new Alert(Alert.AlertType.ERROR);
            nullAlert.setTitle("Product Modification Error");
            nullAlert.setHeaderText("The product is NOT able to be modified!");
            nullAlert.setContentText("There was no product selected!");
            nullAlert.showAndWait();
        }
        else {
            try { //If product is selected bring user to modify product page
                stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/modify_product.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            catch (IOException e) {}
        }
    }

    /** This method brings the user to the add product page.
     @param actionEvent click the add button to go to the add product page
     */
    public void addHandler2(ActionEvent actionEvent) throws IOException {
        stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow(); //Load add product window
        scene = FXMLLoader.load(getClass().getResource("/view/add_product.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /** This method handles exiting the program.
     @param actionEvent click the exit button to close the program
     */
    public void exitHandler(ActionEvent actionEvent) {
        System.exit(0);
    }

    /** This method is the initialize method.
     @param url
     @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        partsTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}

