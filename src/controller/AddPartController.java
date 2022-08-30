package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Nicholas Fallico
 */

/** This class is the add part controller class. It handles adding new parts to the inventory system.*/
public class AddPartController implements Initializable {
    public TextField nameField;
    public TextField invField;
    public TextField priceField;
    public TextField maxField;
    public TextField machineIdField;
    public TextField minField;
    public TextField idField;
    Stage stage;
    Parent scene;

    @FXML public ToggleGroup inOutSelector;
    @FXML private RadioButton inHouseButton;
    @FXML private RadioButton outsourcedButton;
    @FXML private Label radioButtonLabel;

    private int partID;
    @FXML
    private String errPartValid = new String();

    @FXML
    private String errPartField = new String();

    /** This method selects the InHouse button toggle selector.
     @param mouseEvent mouse click the toggle button
     */
    @FXML
    public void inHouseSelector(MouseEvent mouseEvent) {
        if (this.inOutSelector.getSelectedToggle().equals(this.inHouseButton))
            radioButtonLabel.setText("Machine ID");
    }

    /** This method selects the Outsourced button toggle selector.
     @param mouseEvent mouse click the toggle button
     */
    @FXML
    public void outsourcedSelector(MouseEvent mouseEvent) {
        if (this.inOutSelector.getSelectedToggle().equals(this.outsourcedButton))
            radioButtonLabel.setText("Company Name");
    }

    /** This method saves the new part to be added.
     @param mouseEvent mouse click the save button
     */
    @FXML
    public void onMouseSave(MouseEvent mouseEvent) throws IOException {
        int id = partID; //Autogen part ID
        String name = nameField.getText(); //Get name of part
        String invS = invField.getText(); //Get inventory/stock of part
        String priceS = priceField.getText(); //Get price of part
        String maxS = maxField.getText(); //Get max inv of part
        String minS = minField.getText(); //Get min inv of part
        String companyS = machineIdField.getText(); //Get company name or machineID

        errPartField = Inventory.getEmptyFields(name, invS, priceS, maxS, minS, companyS, errPartField);
        if (errPartField.length() > 0){ //Checks if text entry fields are empty and give error message if they are empty
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Part Addition Warning");
            alert.setHeaderText("The part was NOT added!");
            alert.setContentText(errPartField);
            alert.showAndWait();
            errPartField = "";
        }
        else {
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
                errPartValid = Inventory.getPartValidation( //Parsing text field entries for integers or doubles
                        Integer.parseInt(invS),
                        Double.parseDouble(priceS),
                        Integer.parseInt(maxS),
                        Integer.parseInt(minS),
                        errPartValid);
                if (errPartValid.length() > 0) { //Checks text field validation and give error messages if criteria not met
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Part Addition Warning");
                    alert.setHeaderText("The part was NOT added!");
                    alert.setContentText(errPartValid);
                    alert.showAndWait();
                    errPartValid = "";
                }
                else {
                    if (this.outsourcedButton.isSelected()) { //If the Outsourced toggle button is selected a new Outsourced part is saved
                        Outsourced newPart = new Outsourced(id,name,price,inv,min,max,companyS);
                        Inventory.addPart(newPart);
                    }
                    else {
                        int machineId = 0;
                        try {
                            machineId = Integer.parseInt(companyS); //Get integer for machineId
                        } catch (NumberFormatException e) { //Give error if text entry is not a number
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Machine ID ERROR");
                            alert.setHeaderText("Machine ID must be a number!");
                            alert.setContentText("Please fix Machine ID value");
                            alert.showAndWait();
                            return;
                        }
                        InHouse newPart = new InHouse(id,name,price,inv,min,max,machineId); //If the InHouse toggle button is selected a new InHouse part is saved
                        Inventory.addPart(newPart);
                    }
                    stage = (Stage) ((Button)mouseEvent.getSource()).getScene().getWindow(); //Take you back to main screen
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
    @FXML
    public void onActionCancel(ActionEvent actionEvent) throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION); //Pop up to ask if you are sure you want to cancel
            alert.setTitle("Exit to Main Screen");
            alert.setHeaderText("Are you sure you want to cancel?");
            alert.setContentText("Press OK to exit to the Main screen. \nPress Cancel to stay on this screen.");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) { //If okay clicked then you are brought to the main screen
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

    /** This method is the initialize method.
     @param url
     @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partID = Inventory.getPartIDCount();
        idField.setText("Auto Gen: " + partID);
    }
}
