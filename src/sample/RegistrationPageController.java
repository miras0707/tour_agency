package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class RegistrationPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField birthDateField;

    @FXML
    private TextField mailField;

    @FXML
    private TextField surnameField;

    @FXML
    private TextField nameField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registrationButton;

    @FXML
    private PasswordField confirmField;

    @FXML
    void login(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/LoginPage.fxml"));
        try {
            loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        loginButton.getScene().setRoot(root);
    }

    @FXML
    void registration(ActionEvent event) {
        if(confirmField.getText().equals(passwordField.getText()) && !confirmField.getText().isEmpty()){
            try{
                PreparedStatement pst = ConnectionConfiguration.getConnection().prepareStatement(" INSERT INTO `user` (password, `name`, surname, mail, birth_date) VALUES (?, ?, ?, ?, ?)");
                pst.setString(1, passwordField.getText());
                pst.setString(2, nameField.getText());
                pst.setString(3, surnameField.getText());
                pst.setString(4, mailField.getText());
                pst.setString(5, birthDateField.getText());
                pst.execute();
                pst.close();
                login(null);
            }catch (Exception e){e.printStackTrace();}
        }
        else{
            confirmField.setText("");
            passwordField.setText("");
        }

    }

    @FXML
    void initialize() {

    }
}