package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button logoutButton;

    @FXML
    private Button adminButton;

    @FXML
    private ImageView hotelImage;

    @FXML
    private TableView<Tour> tourTable;

    @FXML
    private TableColumn<Tour, String> hotelColumn;

    @FXML
    private TableColumn<Tour, String> starsColumn;

    @FXML
    private TableColumn<Tour, String> locationColumn;

    @FXML
    private TableColumn<Tour, String> priceColumn;

    @FXML
    private Button chatButton;

    @FXML
    private TextField numField;

    @FXML
    private TextField fromField;

    @FXML
    private Button searchButton;

    @FXML
    private TextField toField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button reserveButton;

    private Image image;
    public static Tour tour= new Tour();
    final ObservableList<Tour> tours = FXCollections.observableArrayList();

    @FXML
    void chat(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/ChatPage.fxml"));
        try {
            loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        Parent root =loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(root) );
        stage.show();
    }

    @FXML
    void logout(ActionEvent event) {
        Main.user=new User();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/LoginPage.fxml"));
        try {
            loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        Parent root =loader.getRoot();
        logoutButton.getScene().setRoot(root);
    }

    @FXML
    void reserve(ActionEvent event) {
        try{
            PreparedStatement pst=ConnectionConfiguration.getConnection().prepareStatement(
                    "INSERT INTO reserve (user_id, tour_id, number_of_person, `date`, total) VALUES (?,?,?, ?, ?)"
            );
            pst.setInt(1,Main.user.getId());
            pst.setInt(2,tour.getId());
            pst.setInt(3,Integer.parseInt(numField.getText()));
            pst.setString(4, datePicker.getValue().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            pst.setString(5, String.valueOf(Integer.parseInt(tour.getPrice())*Integer.parseInt(numField.getText())));
            pst.execute();
            pst.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You reserved place in the tour");
        alert.setHeaderText(tour.getHotel());
        alert.setContentText(tour.toString()+"\n"+ (Integer.parseInt(tour.getPrice())*Integer.parseInt(numField.getText()))+ "for " + numField.getText());
        numField.setText("");
        alert.showAndWait();
    }

    @FXML
    void search(ActionEvent event) {
        tours.clear();
        try {
            PreparedStatement pst = ConnectionConfiguration.getConnection().prepareStatement("SELECT * FROM tour WHERE `from` LIKE ? AND location LIKE ?");
            pst.setString(1,"%"+fromField.getText()+"%");
            pst.setString(2,"%"+toField.getText()+"%");
            ResultSet rs=pst.executeQuery();
            while (rs.next()){
                tours.add(new Tour(
                        rs.getInt("id"),
                        rs.getString("hotel"),
                        rs.getString("stars"),
                        rs.getString("price"),
                        rs.getString("location"),
                        rs.getString("description"),
                        rs.getString("from")));
            }
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        hotelColumn.setCellValueFactory(new PropertyValueFactory<>("hotel"));
        starsColumn.setCellValueFactory(new PropertyValueFactory<>("stars"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        tourTable.setItems(tours);
    }

    @FXML
    void showHotel(MouseEvent event) {
        tour = tourTable.getSelectionModel().getSelectedItem();
        if(Main.user.getMail().equals("12345")&& Main.user.getPassword().equals("qwerty")) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/AdminPage.fxml"));
            try {
                loader.load();
            }catch (IOException e){
                e.printStackTrace();
            }
            Parent root =loader.getRoot();
            Stage stage=new Stage();
            stage.setScene(new Scene(root) );
            stage.show();
        }else {
            try {
                PreparedStatement pst = ConnectionConfiguration.getConnection().prepareStatement("SELECT image, description FROM tour WHERE id = ?");
                pst.setInt(1, tour.getId());
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    descriptionArea.setText(rs.getString("description"));
                    InputStream is = rs.getBinaryStream("image");
                    OutputStream os = new FileOutputStream(new File("photo.jpg"));
                    byte[] content = new byte[1024];
                    int size;
                    while ((size = is.read(content)) != -1) {
                        os.write(content, 0, size);
                    }
                    image = new Image("file:photo.jpg");
                    hotelImage.setImage(image);
                    is.close();
                    os.close();
                    pst.close();
                }
                pst.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void admin(ActionEvent event) {
        tour=null;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/AdminPage.fxml"));
        try {
            loader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        Parent root =loader.getRoot();
        Stage stage=new Stage();
        stage.setScene(new Scene(root) );
        stage.show();
    }

    @FXML
    void initialize() {
        search(null);
        adminButton.setVisible(false);
        if(Main.user.getMail().equals("admin")&& Main.user.getPassword().equals("qwerty")){
            adminButton.setVisible(true);
        }
    }
}
