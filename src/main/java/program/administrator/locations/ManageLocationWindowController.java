package program.administrator.locations;

import com.jfoenix.controls.JFXButton;
import database.itemsTable.Items;
import database.itemsTable.ItemsEntity;
import database.itemsTableTemp.ItemsEntityTemp;
import database.itemsTableTemp.ItemsTemp;
import database.locationsTable.Locations;
import database.locationsTable.LocationsEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import program.ImageFx;
import program.Qr;

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

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

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


        saveButton.setOnAction(actionEvent -> {
            String qrCode = idLocTxtField.getText() + " " + cityTxtField.getText()+ " " + adressTxtField.getText() + " " + postalCodeTxtField.getText();

            try {
                if (locNameTxtField.getText().isEmpty() || adressTxtField.getText().isEmpty() ||
                        postalCodeTxtField.getText().isEmpty() || cityTxtField.getText().isEmpty()) {
                    throw new IOException();
                }
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Potwierdzenie");
                alert.setHeaderText("Zmiana danych spowoduje wygenerowanie nowego kodu QR");
                alert.setContentText("Czy na pewno chcesz koontynuować ??");
                Optional<ButtonType> result = alert.showAndWait();

                if(result.get() == ButtonType.OK) {
                    Locations.updateLocations(Integer.parseInt(idLocTxtField.getText()),
                            locNameTxtField.getText(),
                            adressTxtField.getText(),
                            postalCodeTxtField.getText(),
                            cityTxtField.getText(),
                            getimage(),
                            ImageFx.getByteArrayImage(Qr.qrCodeImage(qrCode))
                    );

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

        deleteButton.setOnAction(actionEvent -> {

            String printItemId="";
            String printItemId2="";
            List<ItemsEntityTemp> itemsEntityTempList = ItemsTemp.getAllByLocId(Integer.parseInt(idLocTxtField.getText()));
            List<ItemsEntity> itemsEntityList = Items.getAllByLocId(Integer.parseInt(idLocTxtField.getText()));

            for(ItemsEntityTemp x : itemsEntityTempList){
                printItemId = printItemId +" "+x.getItemId();
            }

            for(ItemsEntity x : itemsEntityList){
                printItemId2 = printItemId2+ " " + x.getItemId();
            }



            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Potwierdzenie");
            if(!itemsEntityTempList.isEmpty()) {
                alert.setHeaderText("Usunięcie tej lokalizacji spowoduje usunięcie " +
                        "wszystkich powiązanych pomieszczeń,oraz te symbole w tabeli" +
                        "zostaną bez przypisanej lokalizacji: " + printItemId);
            }else {
                alert.setHeaderText("Usunięcie tej lokalizacji spowoduje usunięcie " +
                        "wszystkich powiązanych pomieszczeń");
            }
            alert.setContentText("Czy na pewno chcesz koontynuować ??");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK) {

                try {
                    Locations.deleteFromItemsTemp(Integer.parseInt(idLocTxtField.getText()));
                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Potwierdzenie");
                    alert.setContentText("Usunięto pomyślnie ");
                    alert.setHeaderText("Informacja");
                    alert.showAndWait();

                    Stage stage = (Stage) saveButton.getScene().getWindow();
                    stage.close();
                    StackPane test = FXMLLoader.load(getClass().getResource("LocationsWindow.fxml"));
                    pane.getChildren().add(test);
                } catch (Exception e ) {
                    alert.setAlertType(Alert.AlertType.ERROR);
                    alert.setTitle("Błąd");
                    alert.setContentText("Błąd");
                    alert.setHeaderText("Nie można usunąć ponieważ są przedmioty: "+printItemId2+ " przypisane do tej lokalizacji ");
                    alert.showAndWait();
                }

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

    private byte[] getimage() {
        if (imagePathTxtField.getText().equals("")) {
            return Locations.getByteArrayImage(Integer.parseInt(idLocTxtField.getText()) );
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






}
