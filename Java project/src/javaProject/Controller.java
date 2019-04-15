package javaProject;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.*;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Label IDInfo;
    @FXML
    private Label nameInfo;
    @FXML
    private Label priceInfo;
    @FXML
    private Label quantityInfo;
    @FXML
    private Label descriptionInfo;
    @FXML
    protected  TableView <Product> tableView;
    @FXML
    private TableColumn IDColumn;
    @FXML
    private TableColumn<Product,String> nameColumn;
    @FXML
    private TableColumn<Product,Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private ImageView showImage;
    @FXML
    protected Label currentProduct;
    @FXML
    protected  Label totalProducts;

    //index  of selected product in array
    protected static int selectionIndex = 0;
    protected static  List<Product> productList = ProductList.getProductsFromFile();
    protected static ObservableList<Product> data = FXCollections.observableList(productList);

    @Override
    public void initialize (URL location, ResourceBundle resource) {
        //display data to the tableView
        IDColumn.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("Price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

        //display the first record (if it's exist)
        if(data.size() > 0){
            displayDetailInfo(0);
        }


        //set items to the table view
        tableView.setItems(data);

        //show the number of total products
        totalProducts.setText(Integer.toString(data.size()));
    }

    /**
     * display detail information of specific products
     * @param index index of a product from the array list
     */
    public void displayDetailInfo (int index){
        IDInfo.setText(data.get(index).getProductID());
        nameInfo.setText(data.get(index).getName());
        priceInfo.setText(Double.toString(data.get(index).getPrice()));
        quantityInfo.setText(Integer.toString(data.get(index).getQuantity()));
        descriptionInfo.setText(data.get(index).getDescription());
        showImage.setImage(new Image(data.get(index).getImagePath()));
    }


    /**
     * show information of selected product whenever click
     * display the index of product clicked from the list
     */
    @FXML
    public void handleSelectProducts(MouseEvent mouseEvent) {
       selectionIndex = tableView.getSelectionModel().getSelectedIndex();
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            displayDetailInfo(selectionIndex);
            currentProduct.setText(Integer.toString(tableView.getSelectionModel().getSelectedIndex() +1));
        }
    }

    /**
     * display the edit screen of a selected product
     * after editing, refresh the tableView to get edited information
     */
    public void onEditChange(ActionEvent actionEvent) {
        try {
            //make sure that user enters a product before opening the edit screen
            if(tableView.getSelectionModel().getSelectedItem() != null) {
                //get the selected product index
                selectionIndex = tableView.getSelectionModel().getSelectedIndex();

                //display the edit screen if the selected product is not null
                Stage addStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("EditWindow.fxml"));
                addStage.setTitle("Edit Products");
                addStage.setScene(new Scene(root));
                addStage.showAndWait();


                //refresh data from tableView
                tableView.setItems(data);
                tableView.refresh();
            } else
                throw new IllegalArgumentException("Please select a product to edit");

        }catch(IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "An error occurs", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *  Display the add window when the add button is clicked
     *  update the total amount of products exist
     */
    public void addProduct(ActionEvent actionEvent) {
        try {
            Stage addStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("AddWindow.fxml"));
            addStage.setTitle("Add Products");
            addStage.setScene(new Scene(root));
            addStage.showAndWait();

            totalProducts.setText(Integer.toString(data.size()));
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "ERROR", "An error occurs", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Delete a selected product when the delete button is clicked
     * confirm before delete a record
     */
    public void deleteFunction(javafx.event.ActionEvent actionEvent) {
        ObservableList <Product> allproduct, singleProduct;
        try {
            //make sure that user enters a product before opening the edit screen
            if(tableView.getSelectionModel().getSelectedItem() != null) {
                 allproduct = tableView.getItems();
                singleProduct = tableView.getSelectionModel().getSelectedItems(); //with s

                //confirm before delete
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Delete " + singleProduct.get(0).getName() + "?");
                alert.setContentText("Are you sure to delete this record?");
                Optional<ButtonType> result = alert.showAndWait();

                //if the answer is OK, delete the record and update the text file
                if (result.get() == ButtonType.OK) {
                    singleProduct.forEach(allproduct::remove);
                    ProductList.writeProductsToFile();

                    //update the current product index (previous one) and its detail information
                    currentProduct.setText(Integer.toString(tableView.getSelectionModel().getSelectedIndex() + 1));
                    displayDetailInfo(tableView.getSelectionModel().getSelectedIndex());

                    //update the number of total products
                    totalProducts.setText(Integer.toString(data.size()));
                }
            } else
                throw new IllegalArgumentException("Please select a product to delete");
        }catch(IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"An error occurs","ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * search products by ID or by name
     * pop up a window when the search button is clicked
     * @param actionEvent
     */
    public void searchProduct(ActionEvent actionEvent) {
        try {
            Stage addStage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("SearchWindow.fxml"));
            addStage.setTitle("Search Products");
            addStage.setScene(new Scene(root));
            addStage.show();

            //refresh data from tableView
            tableView.setItems(data);
            tableView.refresh();

            //update the number of total products (in case user delete a product)
            totalProducts.setText(Integer.toString(data.size()));
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "ERROR", "An error occurs", JOptionPane.ERROR_MESSAGE);
        }
    }

}


