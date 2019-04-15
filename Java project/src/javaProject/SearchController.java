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
import javafx.stage.Stage;
import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class SearchController extends Controller implements Initializable {
    @FXML
    private TableView searchTable;
    @FXML
    private RadioButton searchByID;
    @FXML
    private RadioButton searchByName;
    @FXML
    private TableColumn searchIdCol;
    @FXML
    private TableColumn searchNameCol;
    @FXML
    private TableColumn searchPriceCol;
    @FXML
    private TableColumn searchQuantityCol;
    @FXML
    private TextField searchBox;

    private  ObservableList<Product> searchingData = data;
    @FXML
    private  ProductList searchingList = new ProductList(productList);

    protected static Product selectedSearchingProduct;

    @Override
    public void initialize (URL location, ResourceBundle resource) {
        searchIdCol.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
        searchNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        searchPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
        searchQuantityCol.setCellValueFactory(new PropertyValueFactory<>("Quantity"));

        searchTable.setItems(searchingData);
    }

    /**
     * show error message when user type a wrong input format
     * @param
     */
    public void handlerSearch(ActionEvent actionEvent) {
        try {
            if(searchByID.isSelected()){
                searchTable.setItems(FXCollections.observableList(
                        searchingList.searchByID(searchBox.getText()))
                );
            } else if(searchByName.isSelected()) {
                searchTable.setItems(FXCollections.observableList(
                        searchingList.searchByName(searchBox.getText()))
                );
            } else if (searchByID.isSelected() == false && searchByName.isSelected() == false) {
                throw new IllegalArgumentException("You didn't select any searching option");
            }
        }catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);

        }catch (Exception ex){
            JOptionPane.showMessageDialog(null, "an error occurs", "ERROR" , JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * display detailed information of selected product in searching window
     * @param actionEvent
     * @throws Exception
     */
    public void SelectProduct(ActionEvent actionEvent) throws Exception {
        try {
            //call this from Controller class
            selectedSearchingProduct = (Product) searchTable.getSelectionModel().getSelectedItem();

            if (selectedSearchingProduct != null) {
                searchingList.setSelectSearchProduct(selectedSearchingProduct);

                //find the id of the selected product
                String selectedID =  selectedSearchingProduct.getProductID();
                for(int i=0; i< data.size(); i++) {
                    if (selectedID.equals(data.get(i).getProductID())){
                        selectionIndex = i;
                    }
                }

                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("DetailInfoScreen.fxml"));
                stage.setTitle("Detail Information");
                stage.setScene(new Scene(root));
                stage.showAndWait();


                searchTable.refresh();

            } else {
                throw new IllegalArgumentException("You didn't select any product");
            }
        } catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(null,e.getMessage() , "ERROR", JOptionPane.ERROR_MESSAGE);
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Can not show information", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * close the window
     * @param actionEvent
     */
    public void CancelSearch(ActionEvent actionEvent) {
        ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
    }


}
