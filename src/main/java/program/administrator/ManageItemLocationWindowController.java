package program.administrator;

import com.jfoenix.controls.JFXButton;
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
import java.util.ResourceBundle;

public class ManageItemLocationWindowController implements Initializable {
    @FXML
    ImageView itemImage, qrCodeImage;
    @FXML
    TextField idNumberTxtField,
            nameProductTxtField,
            invoiceNumberTxtField,
            nettoValueTxtField,
            grossValueTxtField,
            imagePathTxtField,
            groupIdTxtField;
    @FXML
    JFXButton editButton;

    @FXML
    JFXButton addButton;

    @FXML
    ComboBox <LocationsEntity>locationComboBox;

    @FXML
    ComboBox <RoomEntity >roomComboBox;

    @FXML
    Button closeButton, saveButton, deleteButton;
    @FXML
    Label imageLabel;

    ImageFx imageFx = new ImageFx();
    String id ;
    Integer rowId;
    Integer flag =0;
    List<LocationsEntity> locationsEntityList;
    List<RoomEntity> roomEntityList;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    public void initImageView(Image imageItem,Image imageQr){

        itemImage.setImage(imageItem);
        qrCodeImage.setImage(imageQr);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        locationComboBox.getItems().addAll(Locations.getAllFromLocations());

        edit();
    }

    public void initData(ItemsEntityTemp itemsEntityTemp, StackPane pane) {

        LocationsEntity locationsEntity = new LocationsEntity();
        RoomEntity roomEntity = new RoomEntity();

        if(itemsEntityTemp.getLocationID()!=null){
            locationsEntityList = Locations.getAllFromLocationbyLocationId(itemsEntityTemp.getLocationID());
            if(!locationsEntityList.isEmpty()) {
                locationsEntity.setIdLocation(itemsEntityTemp.getLocationID());
                locationsEntity.setNameLocation(locationsEntityList.get(0).getNameLocation());
                locationComboBox.setValue(locationsEntity);
            }
        }

        if(itemsEntityTemp.getRoomId()!=null){
            roomEntityList = Room.getAllByRoomId(itemsEntityTemp.getRoomId());
            if(!roomEntityList.isEmpty()) {
                roomEntity.setIdRoom(itemsEntityTemp.getRoomId());
                roomEntity.setRoomName(roomEntityList.get(0).getRoomName());
                roomComboBox.setValue(roomEntity);
            }
        }

        idNumberTxtField.setText(itemsEntityTemp.getItemId());
        nameProductTxtField.setText(itemsEntityTemp.getItemName());
        invoiceNumberTxtField.setText(itemsEntityTemp.getInvoiceNumber());
        nettoValueTxtField.setText(itemsEntityTemp.getNetValue().toString());
        grossValueTxtField.setText(itemsEntityTemp.getGrossValue().toString());
        if(itemsEntityTemp.getGroupId()!=null)
            groupIdTxtField.setText(itemsEntityTemp.getGroupId().toString());

        id  = idNumberTxtField.getText();
        rowId = itemsEntityTemp.getRowId();

        closeButton.setOnAction(actionEvent -> {

            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

            try {
                StackPane test = FXMLLoader.load(getClass().getResource("ConfirmItemsWindow.fxml"));
                pane.getChildren().add(test);
            } catch (IOException e) {
                e.printStackTrace();
            }


        });

        deleteButton.setOnAction(actionEvent -> {
            ItemsTemp.deleteFromItemsTemp(itemsEntityTemp.getRowId());
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

            try {
                StackPane test = FXMLLoader.load(getClass().getResource("ConfirmItemsWindow.fxml"));
                pane.getChildren().add(test);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    private void edit(){
        editButton.setOnAction(actionEvent -> {

            idNumberTxtField.setEditable(true);
            nameProductTxtField.setEditable(true);
            nettoValueTxtField.setEditable(true);
            grossValueTxtField.setEditable(true);

            imageLabel.setVisible(true);
            imageLabel.setDisable(false);

            imagePathTxtField.setEditable(true);
            imagePathTxtField.setDisable(false);
            imagePathTxtField.setVisible(true);

            saveButton.setVisible(true);
            saveButton.setDisable(false);

            deleteButton.setVisible(true);
            deleteButton.setDisable(false);

            locationComboBox.setDisable(false);
            roomComboBox.setDisable(false);

            addButton.setVisible(true);
            addButton.setDisable(false);


        });

    }


    @FXML
    public void addsImage() throws MalformedURLException{
        imageFx.addImage(imagePathTxtField,itemImage);
    }

    private byte[] getimage() {
        if (imagePathTxtField.getText().equals("")) {
            return ItemsTemp.getByteArrayImage(invoiceNumberTxtField.getText(),id,rowId);
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
    private void updateRoomComboBox(){

        System.out.println(roomComboBox.getValue());
        if(flag ==0) {
            roomComboBox.hide();
            roomComboBox.getItems().addAll(Room.getAllByLocId(locationComboBox.getSelectionModel().getSelectedItem()));
            roomComboBox.show();
            flag++;
        }else{
            roomComboBox.getItems().clear();
            roomComboBox.hide();
            roomComboBox.getItems().addAll(Room.getAllByLocId(locationComboBox.getSelectionModel().getSelectedItem()));
            roomComboBox.show();
        }

    }
    @FXML
    private void clearRoomComboBox(){
        roomComboBox.valueProperty().set(null);
    }

    @FXML
    private void setSaveButton() throws IOException {
        try {

        if (nameProductTxtField.getText().trim().isEmpty()) {
            throw new NullPointerException();
        }

            ItemsTemp.update(idNumberTxtField.getText(),
                    nameProductTxtField.getText(),
                    Float.parseFloat(nettoValueTxtField.getText()),
                    Float.parseFloat(grossValueTxtField.getText()),
                    getimage(),
                    invoiceNumberTxtField.getText(),
                    locationComboBox.getValue(),
                    roomComboBox.getValue(),
                    ImageFx.getByteArrayImage(Qr.qrCodeImage(idNumberTxtField.getText())),
                    rowId
                    );

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacja");
            alert.setContentText("Zmodyfikowano Pomyślnie");
            alert.setHeaderText("Sukces!");
            alert.showAndWait();
        }catch (IOException | NullPointerException | IllegalArgumentException e){
            System.out.println("test");
            alert.setAlertType(Alert.AlertType.WARNING);
            alert.setTitle("Błąd");
            alert.setHeaderText("Błąd");
            alert.setContentText("Któreś z pól jest puste");
            alert.showAndWait();
        }


    }





    }

