package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField mailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registrationButton;

    @FXML
    private Button loginButton;

    @FXML
    void login(ActionEvent event) {
        try {
            PreparedStatement pst = ConnectionConfiguration.getConnection().prepareStatement(" SELECT * FROM `user` WHERE mail = ? AND password = ?");
            pst.setString(1, mailField.getText());
            pst.setString(2, passwordField.getText());
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
                Main.user = new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("password"),
                        rs.getString("mail"),
                        rs.getString("birth_date")
                );
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/MainPage.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                loginButton.getScene().setRoot(root);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("");
                alert.setHeaderText(null);
                alert.setContentText("");
                alert.showAndWait();
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void registration(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/RegistrationPage.fxml"));
        try {
            loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        registrationButton.getScene().setRoot(root);
    }

    @FXML
    void initialize() {
    }
}
