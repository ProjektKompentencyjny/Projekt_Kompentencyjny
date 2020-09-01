package program.accountant;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import database.categoriesTable.Categories;
import database.categoriesTable.CategoriesEntity;
import database.groupsTable.Groups;
import database.groupsTable.GroupsEntity;
import database.itemsTableTemp.ItemsEntityTemp;
import database.itemsTableTemp.ItemsTemp;
import database.locationsTable.Locations;
import database.locationsTable.LocationsEntity;
import database.roomTable.Room;
import database.roomTable.RoomEntity;
import database.subcategoriesTable.Subcategories;
import database.subcategoriesTable.SubcategoriesEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import program.ImageFx;
import program.Qr;
import program.administrator.ErorWindowController;
import program.administrator.ManageItemLocationWindowController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class ManageItemGroupWindowController implements Initializable {
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
    JFXButton editButton,
            addButton,infoGroupButton;


    @FXML
    Label catGroupLabel,subCatLabel,groupLabel;
    @FXML
    Button closeButton, saveButton;

    @FXML
    ComboBox <LocationsEntity>locationComboBox;
    @FXML
    ComboBox <RoomEntity >roomComboBox;
    @FXML
    ComboBox<CategoriesEntity> categoryComboBox;
    @FXML
    ComboBox<SubcategoriesEntity> subcategoryComboBox;
    @FXML
    ComboBox<GroupsEntity> groupComboBox;

    ImageFx imageFx = new ImageFx();
    String id ;
    Integer rowId;
    Integer flag =0;
    List<LocationsEntity> locationsEntityList;
    List<RoomEntity> roomEntityList;


    public void initImageView(Image imageItem,Image imageQr){

        itemImage.setImage(imageItem);
        qrCodeImage.setImage(imageQr);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //locationComboBox.getItems().addAll(Locations.getAllFromItems());
        categoryComboBox.getItems().addAll(Categories.getAllFromItems());

        edit();
    }

    public void initData(ItemsEntityTemp itemsEntityTemp, StackPane pane) {

        LocationsEntity locationsEntity = new LocationsEntity();
        RoomEntity roomEntity = new RoomEntity();


        if(itemsEntityTemp.getLocationID()!=null){
            locationsEntityList = Locations.getAllFromLocationbyLocationId(itemsEntityTemp.getLocationID());
            locationsEntity.setIdLocation(itemsEntityTemp.getLocationID());
            locationsEntity.setNameLocation(locationsEntityList.get(0).getNameLocation());
            locationComboBox.setValue(locationsEntity);
        }

        if(itemsEntityTemp.getRoomId()!=null) {
            roomEntityList = Room.getAllByRoomId(itemsEntityTemp.getRoomId());
            roomEntity.setIdRoom(itemsEntityTemp.getRoomId());
            roomEntity.setRoomName(roomEntityList.get(0).getRoomName());
            roomComboBox.setValue(roomEntity);
        }



        idNumberTxtField.setText(itemsEntityTemp.getItemId());
        nameProductTxtField.setText(itemsEntityTemp.getItemName());
        invoiceNumberTxtField.setText(itemsEntityTemp.getInvoiceNumber());
        nettoValueTxtField.setText(itemsEntityTemp.getNetValue().toString());
        grossValueTxtField.setText(itemsEntityTemp.getGrossValue().toString());

        if(itemsEntityTemp.getGroupId()!=null) {
            groupIdTxtField.setText(itemsEntityTemp.getGroupId().toString());
            infoGroupButton.setVisible(true);
            infoGroupButton.setDisable(false);
        }

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

        saveButton.setOnAction(actionEvent -> {

            try {
                if(groupComboBox.getItems().isEmpty())
                    throw new NullPointerException();

                ItemsTemp.updateGroup(rowId,groupComboBox.getValue(),invoiceNumberTxtField.getText());
                openErrorWindow("Pomyślnie zmodyfikowano ");
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
                StackPane test = FXMLLoader.load(getClass().getResource("ConfirmItemsWindow.fxml"));
                pane.getChildren().add(test);

            } catch (IOException | NullPointerException | IllegalArgumentException e){
                try {
                    openErrorWindow("Któreś pole jest puste ");
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }

        });




    }

    private void edit(){
        editButton.setOnAction(actionEvent -> {

            catGroupLabel.setVisible(true);
            subCatLabel.setVisible(true);
            groupLabel.setVisible(true);

            categoryComboBox.setDisable(false);
            categoryComboBox.setVisible(true);

            subcategoryComboBox.setDisable(false);
            subcategoryComboBox.setVisible(true);

            groupComboBox.setDisable(false);
            groupComboBox.setVisible(true);

            saveButton.setVisible(true);
            saveButton.setDisable(false);
        });

    }


    @FXML
    private void updateSubCategoryComboBox(){
        groupComboBox.getItems().clear();
        if(flag==0){
            subcategoryComboBox.hide();
            subcategoryComboBox.getItems().addAll(Subcategories.getAllByLocId(categoryComboBox.getSelectionModel().getSelectedItem()));
            subcategoryComboBox.show();
            flag++;
        }else{
            subcategoryComboBox.getItems().clear();
            subcategoryComboBox.hide();
            subcategoryComboBox.getItems().addAll(Subcategories.getAllByLocId(categoryComboBox.getSelectionModel().getSelectedItem()));
            subcategoryComboBox.show();
        }

    }

    @FXML
    private void updateGroupComboBox(){
        groupComboBox.autosize();
        if(flag==0){
            groupComboBox.hide();
            groupComboBox.getItems().addAll(Groups.getAllByLocId(subcategoryComboBox.getSelectionModel().getSelectedItem()));
            groupComboBox.show();
            flag++;
        }else{
            groupComboBox.getItems().clear();
            groupComboBox.hide();
            groupComboBox.getItems().addAll(Groups.getAllByLocId(subcategoryComboBox.getSelectionModel().getSelectedItem()));
            groupComboBox.show();
        }


    }

    @FXML
    private void updateCategoryComboBox(){

        subcategoryComboBox.getItems().clear();
        groupComboBox.getItems().clear();


    }

    @FXML
    private void setInfoGroupButton(){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("GroupInfoWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            GroupInfoWindowController groupInfoWindowController = fxmlLoader.getController();
            groupInfoWindowController.initData(Groups.getAllByGroupId(Integer.parseInt(groupIdTxtField.getText())));

            Stage stage = new Stage();
            stage.setTitle("Zarządzanie");
            stage.setScene(scene);
            //stage.initModality(Modality.APPLICATION_MODAL);
            //stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }


    }

    private void openErrorWindow(String info ) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(ManageItemLocationWindowController.class.getResource("ErrorWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        ErorWindowController erorWindowController = fxmlLoader.getController();
        erorWindowController.initData(info);

        Stage stage = new Stage();
        stage.setTitle("Okno informacyjne ");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }







    }

