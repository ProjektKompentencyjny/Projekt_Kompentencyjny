package program.administrator;

import com.jfoenix.controls.JFXButton;
import database.locationsTable.LocationsEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import program.ImageFx;

public class ManageLocationWindowController implements Initializable {
    @FXML
    TextField idLocTxtField,
            locNameTxtField,
            adressTxtField,
            postalCodeTxtField,
            cityTxtField,
            imagePathTxtField;

    @FXML
    Label imageLabel;

    @FXML
    JFXButton editButton;

    @FXML
    Button addButton,closeButton,saveButton,deleteButton;

    @FXML
    ImageView itemImage, qrCodeImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initData(LocationsEntity locationsEntity, StackPane pane){

        idLocTxtField.setText(locationsEntity.getIdLocation().toString());
        locNameTxtField.setText(locationsEntity.getNameLocation());
        adressTxtField.setText(locationsEntity.getStreetAddres());
        postalCodeTxtField.setText(locationsEntity.getPostalCode());
        cityTxtField.setText(locationsEntity.getCity());

        closeButton.setOnAction(actionEvent -> {

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

            try {
                StackPane test = FXMLLoader.load(getClass().getResource("LocationsWindow.fxml"));
                pane.getChildren().add(test);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });




    }

    public void initImageView(Image imageItem, Image imageQr){

        itemImage.setImage(imageItem);
        qrCodeImage.setImage(imageQr);
    }

    @FXML
    private void setEditButton(){

        imagePathTxtField.setVisible(true);
        imagePathTxtField.setDisable(false);

        imageLabel.setVisible(true);
        imageLabel.setDisable(false);

        addButton.setVisible(true);
        addButton.setDisable(false);

        saveButton.setVisible(true);
        saveButton.setDisable(false);

        deleteButton.setVisible(true);
        deleteButton.setDisable(false);

        adressTxtField.setEditable(true);
        cityTxtField.setEditable(true);
        locNameTxtField.setEditable(true);
        postalCodeTxtField.setEditable(true);

    }
    @FXML
    public void setAddButton() throws MalformedURLException{
        ImageFx imageFx = new ImageFx();
        imageFx.addImage(imagePathTxtField,itemImage);
    }


}
