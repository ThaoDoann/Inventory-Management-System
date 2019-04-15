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


public class AddProductController extends Controller implements Initializable {

    @FXML
    private ImageView addImage;
    @FXML
    private TextArea addDescription;
    @FXML
    private TextField addPrice;
    @FXML
    private TextField addName;
    @FXML
    private TextField addQuantity;
    @FXML
    private TextField addID;
    @FXML
    private static File file;

    @Override
    public void initialize (URL location, ResourceBundle resource) {
    }



    /**
     * add information for a new product to add to the list
     *validate an ID, name, quantity and price inputs
     * make sure that an image for a product is added
     */
    public void addProduct(ActionEvent actionEvent) {
        try {
            String id = addID.getText().trim();

            //check if this is an unique id or not
            for (int i = 0; i < data.size(); i++) {
                if (id.trim().toLowerCase().equals(data.get(i).getProductID().trim().toLowerCase()))
                    throw new IllegalArgumentException("This ID is already exist, please choose another ID");
            }
            //a valid id must have at least 5 letters
            if (!id.matches("[A-Za-z0-9]{5,}")) {
                throw new IllegalArgumentException("Invalid ID. A valid ID must have at least 5 letters and there is no white space");
            }

            // name field can not be empty
            String name = addName.getText();
            if(name == null | name.isEmpty()){
                throw new IllegalArgumentException("Product name can not be empty");
            }

            //check if price and quantity input is valid or not
            double price = Double.parseDouble(addPrice.getText());
            int quantity = Integer.parseInt(addQuantity.getText());
            if(price <0 || quantity < 0){
                 throw new IllegalArgumentException("Price and quantity can not be negative");
            }

            //description is optional
            String description = addDescription.getText();

            //all images for this application must be in image directory
            //make sure that image is selected before adding product to the list
            String imagePath = "";
            if (file == null){
                throw new IllegalArgumentException("An image is required.  Please choose an image");
            } else {
                imagePath = "image/" + file.getName();
            }

            //add a new product to the list
            data.add(new Product(id, name, price, quantity, description, imagePath));
            ProductList.writeProductsToFile();

            ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
        } catch (Exception ex){
            if (ex instanceof NumberFormatException )
                JOptionPane.showMessageDialog(null, "Invalid input. A valid number is required " + ex.getMessage());
            else if (ex instanceof IllegalArgumentException)
                JOptionPane.showMessageDialog(null, ex.getMessage());
            else
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a valid input");
        }
    }


    /**
     * this will add an image when we click this button
     * only images ending with .jpg and .png are acceptable
     * @param actionEvent
     */
    public void handlerAddImage(ActionEvent actionEvent) {
        Stage stage = new Stage();
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg","*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(imageFilter);

        try{
            //Make sure that user choose an image for each product (required)
            file = fileChooser.showOpenDialog(stage);
            if (file == null){
                throw new IllegalArgumentException("An image is required.  Please choose an image");
            }

            BufferedImage bufferedImage = ImageIO.read(file);

            //this is optional function to save whatever image selection to the "image" directory
//            File file1 = new File(System.getProperty("user.dir") + "/src/image/" + file.getName());
//            ImageIO.write(bufferedImage,"jpg", file1);

            Image imageChooser = SwingFXUtils.toFXImage(bufferedImage, null);

            //add an  image from image folfer to the imageView
            addImage.setImage(imageChooser);
        }catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "An error occurs");
        }
    }

    //close the window
    public void cancelAdd(ActionEvent actionEvent) {
        ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
    }

}
