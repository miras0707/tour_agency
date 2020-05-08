package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField locationField;

    @FXML
    private TextField starsField;

    @FXML
    private TextField hotelField;

    @FXML
    private Button controlButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField fromField;

    @FXML
    private TextField priceField;

    @FXML
    private ImageView hotelImage;

    @FXML
    private TextArea descriptionArea;

    FileInputStream fis;
    File file;
    @FXML
    void control(ActionEvent event) {
        if(controlButton.getText().equals("Create")){
            try {
                PreparedStatement pst = ConnectionConfiguration.getConnection().prepareStatement(" INSERT INTO tour (hotel, location, `from`, price, description, stars, image) VALUES (?, ?, ?, ?, ?, ?, ?)");
                pst.setString(1,hotelField.getText());
                pst.setString(2, locationField.getText());
                pst.setString(3,fromField.getText());
                pst.setString(4, priceField.getText());
                pst.setString(5, descriptionArea.getText());
                pst.setString(6,starsField.getText());
                pst.setBinaryStream(7, fis, (int)file.length());
                pst.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            try{
                PreparedStatement pst=ConnectionConfiguration.getConnection().prepareStatement("UPDATE tour set hotel = ?, location = ?, `from` = ?, price = ?, description = ?, stars = ?, image = ? where id = ?");
                pst.setString(1,hotelField.getText());
                pst.setString(2, locationField.getText());
                pst.setString(3,fromField.getText());
                pst.setString(4, priceField.getText());
                pst.setString(5, descriptionArea.getText());
                pst.setString(6,starsField.getText());
                pst.setBinaryStream(7, fis, (int)file.length());
                pst.setInt(8,MainPageController.tour.getId());
                pst.executeUpdate();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @FXML
    void delete(ActionEvent event) {
        try{
            PreparedStatement pst=ConnectionConfiguration.getConnection().prepareStatement("DELETE tour where id = ?");
            pst.setInt(1,MainPageController.tour.getId());
            pst.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void initialize() {
        controlButton.setCancelButton(true);
        deleteButton.setCancelButton(true);
        if(MainPageController.tour==null){
            controlButton.setText("Create");
            deleteButton.setDisable(true);
        }
        else{
            try {
                PreparedStatement pst = ConnectionConfiguration.getConnection().prepareStatement("SELECT * from tour where id = ?");
                pst.setInt(1, MainPageController.tour.getId());
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    InputStream is = rs.getBinaryStream("image");
                    file = new File("photo.jpg");
                    OutputStream os = new FileOutputStream(file);
                    byte[] content = new byte[1024];
                    int size;
                    while ((size = is.read(content)) != -1) {
                        os.write(content, 0, size);
                    }
                    fis=new FileInputStream(file);
                    Image image = new Image("file:photo.jpg");
                    hotelImage.setImage(image);
                    hotelField.setText(rs.getString("hotel"));
                    descriptionArea.setText(rs.getString("description"));
                    priceField.setText(rs.getString("price"));
                    locationField.setText(rs.getString("location"));
                    fromField.setText(rs.getString("from"));
                    starsField.setText(rs.getString("stars"));
                    is.close();
                    os.close();
                    fis.close();
                    pst.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @FXML
    void browse(MouseEvent event) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Hotel Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image File", "*.jpg", "*.png"));
        file = fileChooser.showOpenDialog(new Stage());
        if(file!=null){
            try {
                Image image = new Image(file.toURI().toURL().toString());
                hotelImage.setImage(image);
                fis= new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }
}