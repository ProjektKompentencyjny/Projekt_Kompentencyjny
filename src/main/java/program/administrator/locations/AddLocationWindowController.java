package program.administrator.locations;

import com.jfoenix.controls.JFXButton;
import database.locationsTable.Locations;
import database.locationsTable.LocationsEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.PropertyValueException;
import program.ImageFx;
import program.Qr;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.ResourceBundle;

public class AddLocationWindowController  implements Initializable {

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    @FXML
    Button saveButton, closeButton;

    @FXML
    JFXButton addButton;

    @FXML
    TextField locNameTextField,
            locAddressTextField,
            postalCodeTextField,
            cityTextField,
            imagePathTextField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void confirm(StackPane pane){
        List<LocationsEntity> locationsEntityList= Locations.getAllFromLocations();
        int id;


        id = (locationsEntityList.size()==0) ? 1 :
                locationsEntityList.get(locationsEntityList.size()-1).getIdLocation()+1;

        saveButton.setOnAction(actionEvent -> {
            String qrCode = id + " " + cityTextField.getText()+ " " + locAddressTextField.getText() + " " + postalCodeTextField.getText();
            LocationsEntity locationsEntity = null;
            try {
                locationsEntity = new LocationsEntity(id,
                        locNameTextField.getText(),
                        locAddressTextField.getText(),
                        postalCodeTextField.getText(),
                        cityTextField.getText(),
                        ImageFx.getByteArrayImage(Qr.qrCodeImage(qrCode)),
                        getimage()
                        );

                Locations.insertLocation(locationsEntity);

                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacja");
                alert.setContentText("Dodano Pomyślnie");
                alert.setHeaderText("Sukces!");
                alert.showAndWait();

                Stage stage = (Stage) addButton.getScene().getWindow();
                stage.close();
            } catch (IOException  | PropertyValueException exec) {
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setTitle("Błąd");
                alert.setHeaderText("Błąd");
                alert.setContentText("Któreś z pól jest puste");
                alert.showAndWait();
            }

            try{

                StackPane test = FXMLLoader.load(getClass().getResource("LocationsWindow.fxml"));
                pane.getChildren().add(test);

            }catch (IOException e ){
                e.printStackTrace();
            }

        });

        try{

            StackPane test = FXMLLoader.load(getClass().getResource("LocationsWindow.fxml"));
            pane.getChildren().add(test);

        }catch (IOException e ){
            e.printStackTrace();
        }

    }


    public byte[] getimage(){
        Path path = Paths.get(imagePathTextField.getText());
        byte [] data = null;
        try {
            data = Files.readAllBytes(path);
        }catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }

    @FXML
    public void addImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Files","*jpg"));
        File file = fileChooser.showOpenDialog(null);

        if(file!=null){
            imagePathTextField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    private void setCloseButton(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }



}
