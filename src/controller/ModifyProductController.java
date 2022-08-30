package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.MainScreenController.*;
import static model.Inventory.getAllParts;

/** This class is the modify product controller class. It handles modify products in the inventory system.*/
public class ModifyProductController implements Initializable {
    public TextField idField;
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField minField;
    public TextField searchParts;
    public Button addButton;
    public Button removePartButton;
    public TableView partsInStockTable;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInvCol;
    public TableColumn partPriceCol;
    public TableView addPartsToProductTable;
    public TableColumn associatedPartIDCol;
    public TableColumn associatedPartNameCol;
    public TableColumn associatedPartInvCol;
    public TableColumn associatedPartPriceCol;

    Stage stage;
    Parent scene;

    private int productID = getSelectedProduct().getProductID();
    @FXML
    private String errProductValid = new String();

    @FXML
    private String errProductField = new String();

    private ObservableList<Part> bottomTableList = FXCollections.observableArrayList(); //Create ObservableList for bottom table of associated parts

    /** This method handles searching the associated parts table.
     @param actionEvent click the enter key to search for a part
     */
    public void searchFunction(ActionEvent actionEvent) {
        String searchField = searchParts.getText(); //Gets text entered into search field
        if (searchField.equals("")){ //If text field is blank and search is clicked it loads all parts into the table
            partsInStockTable.setItems(Inventory.getAllParts());
            return;
        }
        else {
            boolean found=false;
            try {
                Part searchPart = Inventory.lookUpPart(Integer.parseInt(searchField)); //Parse text field for number. Lookup part by ID
                if (searchPart != null) { //If a number is found in the text field search for partID
                    ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
                    filteredPartsList.add(searchPart);
                    partsInStockTable.setItems(filteredPartsList);
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
                        partsInStockTable.setItems(filteredPartsList);
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

    /** This method saves the new product to be added.
     @param mouseEvent mouse click the save button
     */
    public void onMouseSave(MouseEvent mouseEvent) throws IOException {
        int id = productID; //Autogen part ID
        String name = nameField.getText(); //Get name of part
        String invS = invField.getText(); //Get inventory/stock of part
        String priceS = priceField.getText(); //Get price of part
        String maxS = maxField.getText(); //Get max inv of part
        String minS = minField.getText(); //Get min inv of part
        ObservableList<Part> associatedParts = addPartsToProductTable.getItems();

        errProductField = Product.getEmptyFields(name, invS, priceS, maxS, minS, errProductField);
        if (errProductField.length() > 0){ //Check if text fields are empty and give error message if they are
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Part Addition Warning");
            alert.setHeaderText("The part was NOT added!");
            alert.setContentText(errProductField);
            alert.showAndWait();
            errProductField = "";
        }
        else {
            if (associatedParts.isEmpty()) { //Check if associated parts is empty and give error message if they are
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Product Save Error");
                alert.setHeaderText("The product was not saved!");
                alert.setContentText("An associated part was not selected!");
                alert.showAndWait();
                return;
            }
            int inv = 0;
            try {
                inv = Integer.parseInt(invS); //Get integer for inv
            } catch (NumberFormatException e) { //Give error if text entry is not a number
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inv ERROR");
                alert.setHeaderText("Inv value must be a number!");
                alert.setContentText("Please fix Inv value");
                alert.showAndWait();
                return;
            }
            double price = 0;
            try {
                price = Double.parseDouble(priceS); //Get double for price
            } catch (NumberFormatException e) { //Give error if text entry is not a number
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Price ERROR");
                alert.setHeaderText("Price value must be a number!");
                alert.setContentText("Please fix Price value");
                alert.showAndWait();
                return;
            }
            int max = 0;
            try {
                max = Integer.parseInt(maxS); //Get integer for max
            } catch (NumberFormatException e) { //Give error if text entry is not a number
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Max ERROR");
                alert.setHeaderText("Max value must be a number!");
                alert.setContentText("Please fix Max value");
                alert.showAndWait();
                return;
            }
            int min = 0;
            try {
                min = Integer.parseInt(minS); //Get integer for min
            } catch (NumberFormatException e) { //Give error if text entry is not a number
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Min ERROR");
                alert.setHeaderText("Min value must be a number!");
                alert.setContentText("Please fix Min value");
                alert.showAndWait();
                return;
            }
            try {
                errProductValid = Inventory.getPartValidation( //Parsing text field entries for integers or doubles
                        Integer.parseInt(invS),
                        Double.parseDouble(priceS),
                        Integer.parseInt(maxS),
                        Integer.parseInt(minS),
                        errProductValid);
                if (errProductValid.length() > 0) { //Checks text field validation and give error messages if criteria not met
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Addition Warning");
                    alert.setHeaderText("The part was NOT added!");
                    alert.setContentText(errProductValid);
                    alert.showAndWait();
                    errProductValid = "";
                }
                else {
                    Product updateProduct = new Product(productID, name, price, inv, min, max); //Save modified product
                    Inventory.updateProduct(selectedProductIndex, updateProduct);
                    updateProduct.setAssociatedPartsList(associatedParts); //Add associated parts to new product

                    stage = (Stage) ((Button)mouseEvent.getSource()).getScene().getWindow(); //Return to main screen
                    scene = FXMLLoader.load(getClass().getResource("/view/main_screen.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
            }
            catch (IOException e) {}
        }
    }

    /** This method is the cancel method. It does not save the input data and takes you back to main screen.
     @param actionEvent click on the cancel button
     */
    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //Confirm cancel
            alert.setTitle("Exit to Main Screen");
            alert.setHeaderText("Are you sure you want to cancel?");
            alert.setContentText("Press OK to exit to the Main screen. \nPress Cancel to stay on this screen.");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {//Return to main screen
                stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/main_screen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } else {
                alert.close();
            }
        }
        catch (IOException e) {}
    }

    /** This method adds parts to the bottom associated parts table from a selection on the top table.
     @param actionEvent select the add button to move a part into the bottom associated parts table
     */
    public void addAction(ActionEvent actionEvent) {
        Part part = (Part) partsInStockTable.getSelectionModel().getSelectedItem(); //Select associated part from top table
        if (part == null) { //Check if part is selected and give error if it is not
            Alert nullalert = new Alert(Alert.AlertType.ERROR);
            nullalert.setTitle("Associated Part Addition Error");
            nullalert.setHeaderText("The part was not added!");
            nullalert.setContentText("A part was not selected!");
            nullalert.showAndWait();
        }
        else {
            bottomTableList.add(part); //Add part to bottom table
        }
    }

    /** This method removes a part from the bottom associated parts table.
     @param actionEvent select the remove button to remove a part from the bottom associated parts table
     */
    public void removePart(ActionEvent actionEvent) {
        Part part = (Part) addPartsToProductTable.getSelectionModel().getSelectedItem(); //Select associated part from bottom table
        if (part == null) { //Check if part is selected and give error if not
            Alert nullalert = new Alert(Alert.AlertType.ERROR);
            nullalert.setTitle("Associated Part Removal Error");
            nullalert.setHeaderText("The part was not removed!");
            nullalert.setContentText("A part was not selected!");
            nullalert.showAndWait();
        }
        else {
            bottomTableList.remove(part); //Remove associated part from bottom table
        }
    }

    /** This method is the initialize method.
     @param url
     @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Product selectedProduct = getSelectedProduct();
        int productID = getSelectedProduct().getProductID();
        idField.setText("Auto Gen: " + productID);
        nameField.setText(selectedProduct.getName());
        invField.setText(Integer.toString(selectedProduct.getInStock()));
        priceField.setText(Double.toString(selectedProduct.getPrice()));
        maxField.setText(Integer.toString(selectedProduct.getMax()));
        minField.setText(Integer.toString(selectedProduct.getMin()));
        partsInStockTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        bottomTableList = selectedProduct.getAllAssociatedParts();
        addPartsToProductTable.setItems(bottomTableList);
        associatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
