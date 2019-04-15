package javaProject;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class EditProductController extends Controller implements Initializable {
    @FXML
    private TextField editID;
    @FXML
    private TextField editPrice;
    @FXML
    private TextField editName;
    @FXML
    private TextField editQuantity;
    @FXML
    private TextArea editDescription;
    @FXML
    private ImageView editImage;
    private static File file;

    @Override
    public void initialize (URL location, ResourceBundle resource) {
        editID.setText(data.get(selectionIndex).getProductID());
        editName.setText(data.get(selectionIndex).getName());
        editPrice.setText(Double.toString(data.get(selectionIndex).getPrice()));
        editQuantity.setText(Integer.toString(data.get(selectionIndex).getQuantity()));
        editDescription.setText(data.get(selectionIndex).getDescription());
        editImage.setImage(new Image(data.get(selectionIndex).getImagePath()));
    }

    /**
     * edit product information and change it in text file
     * @param actionEvent
     */
    @FXML
    public void editProduct(ActionEvent actionEvent) {
        try {
            String id = editID.getText();
            for (int i = 0; i < data.size(); i++) {
                if(!id.trim().toLowerCase().equals(data.get(selectionIndex).getProductID().trim().toLowerCase())) {
                    if (id.trim().toLowerCase().equals(data.get(i).getProductID().trim().toLowerCase()))
                        throw new IllegalArgumentException("This ID is already exist, please choose another ID");
                }
            }
            //a valid id must have at least 5 letters
            if (!id.matches("[A-Za-z0-9]{5,}")) {
                throw new IllegalArgumentException("Invalid ID. A valid ID must have at least 5 letters and there is no white space");
            }

            String name = editName.getText();
            if(name == null | name.isEmpty()){
                throw new IllegalArgumentException("Product name can not be empty");
            }

            double price = Double.parseDouble(editPrice.getText());
            int quantity = Integer.parseInt(editQuantity.getText());
            if (price < 0 || quantity < 0) {
                throw new IllegalArgumentException("Price and quantity can not be negative");
            }

            String description = editDescription.getText();

            //all images for this application must be in image directory
            //make sure that image is selected before adding product to the list
            String imagePath = "";
            if ( editImage.getImage() == null){
                throw new IllegalArgumentException("An image is required.  Please choose an image");
            }else if (file != null){
                imagePath = "image/" + file.getName();
            } else if (file == null){
                imagePath = data.get(selectionIndex).getImagePath();
            }

            //change edited information of selected product
            data.get(selectionIndex).setProductID(id);
            data.get(selectionIndex).setName(name);
            data.get(selectionIndex).setPrice(price);
            data.get(selectionIndex).setQuantity(quantity);
            data.get(selectionIndex).setDescription(description);
            data.get(selectionIndex).setImagePath(imagePath);

            ProductList.writeProductsToFile();
            ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
        } catch (Exception e){
            if (e instanceof NumberFormatException )
                JOptionPane.showMessageDialog(null, "Invalid input. A valid number is required " + e.getMessage(),
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            else if (e instanceof IllegalArgumentException)
                JOptionPane.showMessageDialog(null, e.getMessage(),  "ERROR", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid input","ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     *close the window
     */
    public void cancelEdit(ActionEvent actionEvent) {
        ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
    }

    /**
     * edit image by choosing an image in your computer
     * under image directory
     * @param actionEvent
     */
    public void handlerEditImage(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(imageFilter);

        try{
            //Make sure that user choose an image for each product (required)
            file = fileChooser.showOpenDialog(stage);
            if (file == null){
                throw new IllegalArgumentException("An image is required.  Please choose an image");
            }
            BufferedImage bufferedImage = ImageIO.read(file);
            Image imageChooser = SwingFXUtils.toFXImage(bufferedImage, null);
            editImage.setImage(imageChooser);

        }catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "An error occurs",  "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
}
