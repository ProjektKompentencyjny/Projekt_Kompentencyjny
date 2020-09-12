package program.administrator.locations;

import com.jfoenix.controls.JFXButton;
import database.itemsTable.Items;
import database.itemsTable.ItemsEntity;
import database.itemsTableTemp.ItemsEntityTemp;
import database.itemsTableTemp.ItemsTemp;
import database.locationsTable.Locations;
import database.locationsTable.LocationsEntity;
import database.roomTable.Room;
import database.roomTable.RoomEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import program.ImageFx;
import program.Qr;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ManageRoomWindowController implements Initializable {

    @FXML
    TextField idRoomTxtField,roomNameTxtField,imagePathTxtField;

    @FXML
    ComboBox<LocationsEntity> locComboBox;

    @FXML
    Button closeButton, saveButton,deleteButton;

    @FXML
    JFXButton addButton;

    @FXML
    ImageView itemImage, qrCodeImage;

    @FXML
    Label imageLabel;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        locComboBox.getItems().addAll(Locations.getAllFromLocations());

    }


    public void initData(RoomEntity roomEntity, StackPane pane){

        idRoomTxtField.setText(roomEntity.getIdRoom().toString());
        roomNameTxtField.setText(roomEntity.getRoomName());
        locComboBox.setValue(roomEntity.getLocationsEntityId());

        deleteButton.setOnAction(actionEvent -> {

            String printItemId="";
            String pringItemId2="";
            List<ItemsEntityTemp> itemsEntityTempList = ItemsTemp.getAllByRoomId(Integer.parseInt(idRoomTxtField.getText()));
            List<ItemsEntity> itemsEntityList = Items.getAllByRoomId(Integer.parseInt(idRoomTxtField.getText()));

            for(ItemsEntity x :itemsEntityList){
                pringItemId2=pringItemId2+" "+x.getItemId();
            }

            for(ItemsEntityTemp x : itemsEntityTempList){
                printItemId = printItemId +" "+x.getItemId();
            }

            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdzenie");
            if(!itemsEntityTempList.isEmpty()) {
                alert.setHeaderText("Usunięcie tego pomiszczenia spowoduje, że następujące przedmioty  " +
                        "zostaną bez przypisanego pomieszczenia : " + printItemId);
            }else {
                alert.setHeaderText("Uwaga!");
            }
            alert.setContentText("Czy na pewno chcesz koontynuować ??");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK) {

                try {
                    Room.deleteFromRoom(Integer.parseInt(idRoomTxtField.getText()));
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Potwierdzenie");
                    alert.setContentText("Usunięto pomyślnie ");
                    alert.setHeaderText("Informacja");
                    alert.showAndWait();

                    Stage stage = (Stage) saveButton.getScene().getWindow();
                    stage.close();

                    StackPane test = FXMLLoader.load(getClass().getResource("LocationsWindow.fxml"));
                    pane.getChildren().add(test);


                } catch (Exception e) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setContentText("Błąd");
                    alert.setHeaderText("Nie można usunąć ponieważ są przedmioty: "+pringItemId2+ " przypisane do tego pomieszczenia ");
                    alert.showAndWait();
                }
            }

        });

        saveButton.setOnAction(actionEvent -> {

                String qrCode = idRoomTxtField.getText() + " " + roomNameTxtField.getText() + " " + locComboBox.getValue();
                try {
                    if(idRoomTxtField.getText().isEmpty() || roomNameTxtField.getText().isEmpty()
                            || locComboBox.getValue().equals(null)){
                        throw new IOException();
                    }
                    alert.setAlertType(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Potwierdzenie");
                    alert.setHeaderText("Zmiana danych spowoduje wygenerowanie nowego kodu QR");
                    alert.setContentText("Czy na pewno chcesz koontynuować ??");
                    Optional<ButtonType> result = alert.showAndWait();


                    if(result.get() == ButtonType.OK) {
                            Room.updateRoom(Integer.parseInt(idRoomTxtField.getText()),
                                    roomNameTxtField.getText(),
                                    getimage(),
                                    ImageFx.getByteArrayImage(Qr.qrCodeImage(qrCode)),
                                    locComboBox.getValue());

                        Stage stage = (Stage) saveButton.getScene().getWindow();
                        stage.close();
                        StackPane test = FXMLLoader.load(getClass().getResource("LocationsWindow.fxml"));
                        pane.getChildren().add(test);

                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Informacja");
                        alert.setHeaderText("Sukces!");
                        alert.setContentText("Zmodyfikowano pomyślnie");
                        alert.showAndWait();

                        }



                } catch (IOException exception) {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setTitle("Informacja");
                    alert.setHeaderText("Uwaga");
                    alert.setContentText("Któreś z pól jest puste");
                    alert.showAndWait();
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

        locComboBox.setDisable(false);

        roomNameTxtField.setEditable(true);
        imagePathTxtField.setEditable(true);

    }

    private byte[] getimage() {
        if (imagePathTxtField.getText().equals("")) {
            return Room.getByteArrayImage(Integer.parseInt(idRoomTxtField.getText()) );
        }else{
            Path path = Paths.get(imagePathTxtField.getText());
            byte[] data = null;
            try {
                data = Files.readAllBytes(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

    }

    @FXML
    public void setAddButton() throws MalformedURLException {
        ImageFx imageFx = new ImageFx();
        imageFx.addImage(imagePathTxtField,itemImage);
    }

    @FXML
    private void setCloseButton(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


}
