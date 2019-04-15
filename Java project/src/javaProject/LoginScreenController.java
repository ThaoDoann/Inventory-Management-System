package javaProject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {
    public TextField tfUserName;
    public PasswordField tfPassword;
    public AnchorPane loginPane;


    /**
     *  username and password
     */

    private String login [][] = {{"user" , "password"}, {"prof", "professor"}};
    @Override
    public void initialize (URL location, ResourceBundle resource) {

    }

    /**
     * validate username and password
     * @param actionEvent
     * @throws Exception
     */
    public void loginBtnHandler(ActionEvent actionEvent)throws Exception {
        String userName = tfUserName.getText().trim().toLowerCase();
        String passWord = tfPassword.getText().trim().toLowerCase();
        boolean validInput = false;

        //check if username and password are valid
        for (int i=0; i< login.length; i++){
            if (userName.equals(login[i][0])  && passWord.equals(login[i][1])){
                validInput = true;
                break;
            }
        }

        if (validInput == true){
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("ProductOverView.fxml"));
            stage.setTitle("Main Screen");
            Scene scene = new Scene (root);
            stage.setScene(scene);
            stage.show();

            ((Stage) ((Button) actionEvent.getSource()).getScene().getWindow()).close();
        } else {
            JOptionPane.showMessageDialog(null, String.format("Your user name or password is wrong.\n" +
                    " Please enter a valid user name and password"),  "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * make the scene bigger when SAIGON is clicked
     * @param actionEvent
     */
    public void handlerSaiGon(ActionEvent actionEvent) {
        loginPane.getScene().getWindow().setWidth(loginPane.getScene().getWindow().getWidth() +100);
        loginPane.getScene().getWindow().setHeight(loginPane.getScene().getWindow().getHeight() + 50);
    }
}
