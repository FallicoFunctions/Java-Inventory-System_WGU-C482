package main;

/**
 * @author Nicholas Fallico
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

/** This class creates an application for an inventory database. */
public class Main extends Application {
    /** This method creates the main screen gui.
     @param primaryStage The startup and home screen.
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        addTestData(); //Add test data to inventory program

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_screen.fxml")); //Load main screen
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /** This method creates test data for the inventory system. There is test data for parts and products. */
    private static void addTestData() { //Create test data for inventory program
        InHouse washer = new InHouse(5054,"Washer",0.05,10,0,100,1234);
        Inventory.addPart(washer);
        Outsourced nut = new Outsourced(1863,"Nut",0.09,15,0,100,"Nutz");
        Inventory.addPart(nut);
        InHouse screw = new InHouse(6743,"Screw",0.10,10,0,100,5678);
        Inventory.addPart(screw);
        Outsourced bolt = new Outsourced(2938,"Bolt",0.11,15,0,100,"Boltz");
        Inventory.addPart(bolt);

        Product bike = new Product(7384,"Bike",100.00,2,0,100);
        bike.addAssociatedPart(washer);
        Inventory.addProduct(bike);
        Product scooter = new Product(4873,"Scooter",75.00,3,0,100);
        scooter.addAssociatedPart(bolt);
        Inventory.addProduct(scooter);
    }

    /** This is the main method. */
    public static void main(String[] args) {
        launch(args);
    }
}
