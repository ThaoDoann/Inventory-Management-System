package javaProject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DetailInfoController extends SearchController implements Initializable {
    @FXML private Label displayID;
    @FXML private ImageView displayImage;
    @FXML private Label displayName;
    @FXML private Label displayPrice;
    @FXML private Label displayQuantity;
    @FXML private Label displayDescription;

    @Override
    public void initialize (URL location, ResourceBundle resource) {
        //display information when th screen is opened
        updateInfo();
    }

    //display an unpdate detailed information
    public void updateInfo (){
        displayID.setText(selectedSearchingProduct.getProductID());
        displayName.setText(selectedSearchingProduct.getName());
        displayPrice.setText(Double.toString(selectedSearchingProduct.getPrice()));
        displayQuantity.setText(Integer.toString(selectedSearchingProduct.getQuantity()));
        displayDescription.setText(selectedSearchingProduct.getDescription());
        displayImage.setImage(new Image(selectedSearchingProduct.getImagePath()));
    }


    public void handlerOKButton(ActionEvent actionEvent) {
        ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
    }

    /**
     * confirm the action and delete it if user clicks OK button
     * @param actionEvent
     */
    public void deleteInfo(ActionEvent actionEvent) {
        //confirm before delete
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete " + data.get(selectionIndex).getName() + "?");
        alert.setContentText("Are you sure to delete this record?");
        Optional<ButtonType> result = alert.showAndWait();

        //if the answer is OK, delete the record and update the text file
        if (result.get() == ButtonType.OK) {
            data.remove(selectionIndex);
            ProductList.writeProductsToFile();

            ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
        }

    }


    /**
     * pop up edit window and update detailed information if there are any changes
     * @param actionEvent
     */
    public void editInfo(ActionEvent actionEvent) {
        try {
            Stage addStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("EditWindow.fxml"));
            addStage.setTitle("Edit Products");
            addStage.setScene(new Scene(root));
            addStage.showAndWait();

            updateInfo();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "An error occurs", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
