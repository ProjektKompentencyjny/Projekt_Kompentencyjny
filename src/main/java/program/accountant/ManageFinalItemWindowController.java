package program.accountant;

import com.jfoenix.controls.JFXButton;
import database.amortizationTable.Amortization;
import database.amortizationTable.AmortizationEntity;
import database.categoriesTable.Categories;
import database.categoriesTable.CategoriesEntity;
import database.groupsTable.Groups;
import database.groupsTable.GroupsEntity;
import database.itemsTable.Items;
import database.itemsTable.ItemsEntity;
import database.itemsTableTemp.ItemsTemp;
import database.locationsTable.LocationsEntity;
import database.roomTable.RoomEntity;
import database.subcategoriesTable.Subcategories;
import database.subcategoriesTable.SubcategoriesEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import program.ImageFx;
import program.accountant.amortization.AmortizationStarterWindowController;
import program.accountant.amortization.ManageAmortizationWindowController;
import program.administrator.assortment.AssortmentController;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ManageFinalItemWindowController implements Initializable {

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
            addButton, infoGroupButton,startAmortizationButton,manageAmortizationButton;

    @FXML
    AnchorPane manageFinalItemPane;

    @FXML
    Label catGroupLabel, subCatLabel, groupLabel;
    @FXML
    Button closeButton, saveButton;

    @FXML
    ComboBox<LocationsEntity> locationComboBox;
    @FXML
    ComboBox<RoomEntity> roomComboBox;
    @FXML
    ComboBox<CategoriesEntity> categoryComboBox;
    @FXML
    ComboBox<SubcategoriesEntity> subcategoryComboBox;
    @FXML
    ComboBox<GroupsEntity> groupComboBox;

    ImageFx imageFx = new ImageFx();
    String id;
    Integer rowId;
    Integer flag = 0;
    List<LocationsEntity> locationsEntityList;
    List<RoomEntity> roomEntityList;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);


    public void initImageView(Image imageItem, Image imageQr) {

        itemImage.setImage(imageItem);
        qrCodeImage.setImage(imageQr);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        categoryComboBox.getItems().addAll(Categories.getAllFromItems());
        edit();
    }

    public void initData(ItemsEntity itemsEntity, StackPane pane){

        locationComboBox.setValue(itemsEntity.getLocationsEntityId());
        roomComboBox.setValue(itemsEntity.getRoomEntity());
        groupIdTxtField.setText(itemsEntity.getGroupsEntity().getGroupId().toString());

        idNumberTxtField.setText(itemsEntity.getItemId());
        nameProductTxtField.setText(itemsEntity.getItemName());
        invoiceNumberTxtField.setText(itemsEntity.getInvoiceNumber().getInvoiceNumber());
        nettoValueTxtField.setText(itemsEntity.getNetValue().toString());
        grossValueTxtField.setText(itemsEntity.getGrossValue().toString());

        id  = idNumberTxtField.getText();
        rowId = itemsEntity.getRowId();

        closeButton.setOnAction(actionEvent -> {

            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

            try {
                StackPane test = FXMLLoader.load((AssortmentController.class.getResource("Assortment.fxml")));
                pane.getChildren().add(test);
            } catch (IOException e) {
                e.printStackTrace();
            }


        });

        saveButton.setOnAction(actionEvent -> {
            try {
                if(groupComboBox.getItems().isEmpty())
                    throw new NullPointerException();

                Items.updateGroup(rowId,groupComboBox.getValue());
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacja");
                alert.setContentText("Przedmiot został pomyślnie zmodyfikowany ");
                alert.setHeaderText("Sukces");
                alert.showAndWait();
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
                StackPane test = FXMLLoader.load((AssortmentController.class.getResource("Assortment.fxml")));
                pane.getChildren().add(test);

            } catch (IOException | NullPointerException | IllegalArgumentException e){
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setContentText("Któreś z pól jest puste");
                alert.setHeaderText("Błąd");
                alert.showAndWait();

            }


        });

        List<AmortizationEntity> amortizationEntityList = Amortization.getAllFromAmortizationbyItemID(itemsEntity);
        if(amortizationEntityList.isEmpty()) {
            startAmortizationButton.setVisible(true);
            startAmortizationButton.setDisable(false);
        }else {
            manageAmortizationButton.setVisible(true);
            manageAmortizationButton.setDisable(false);
        }

        startAmortizationButton.setOnAction(actionEvent -> {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation((AmortizationStarterWindowController.class.getResource("AmortizationStarterWindow.fxml")));
                Scene scene = new Scene(fxmlLoader.load());

                // Potem zrobić tutaj sprawdzenie czy już nie jest amortyzowany

                AmortizationStarterWindowController amortizationStarterWindowController = fxmlLoader.getController();
                amortizationStarterWindowController.initData(itemsEntity,manageFinalItemPane);
                Stage stage = new Stage();
                stage.setTitle("Zarządzanie");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }

        });

        manageAmortizationButton.setOnAction(actionEvent -> {


            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation((ManageAmortizationWindowController.class.getResource("ManageAmortizationWindow.fxml")));
                Scene scene = new Scene(fxmlLoader.load());

                ManageAmortizationWindowController manageAmortizationWindowController = fxmlLoader.getController();
                manageAmortizationWindowController.initData(itemsEntity,manageFinalItemPane);
                Stage stage = new Stage();
                stage.setTitle("Zarządzanie");
                stage.setScene(scene);
                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }



        });



    }



    private void edit() {
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
    private void updateSubCategoryComboBox() {
        groupComboBox.getItems().clear();
        if (flag == 0) {
            subcategoryComboBox.hide();
            subcategoryComboBox.getItems().addAll(Subcategories.getAllByLocId(categoryComboBox.getSelectionModel().getSelectedItem()));
            subcategoryComboBox.show();
            flag++;
        } else {
            subcategoryComboBox.getItems().clear();
            subcategoryComboBox.hide();
            subcategoryComboBox.getItems().addAll(Subcategories.getAllByLocId(categoryComboBox.getSelectionModel().getSelectedItem()));
            subcategoryComboBox.show();
        }

    }

    @FXML
    private void updateGroupComboBox() {
        groupComboBox.autosize();
        if (flag == 0) {
            groupComboBox.hide();
            groupComboBox.getItems().addAll(Groups.getAllByLocId(subcategoryComboBox.getSelectionModel().getSelectedItem()));
            groupComboBox.show();
            flag++;
        } else {
            groupComboBox.getItems().clear();
            groupComboBox.hide();
            groupComboBox.getItems().addAll(Groups.getAllByLocId(subcategoryComboBox.getSelectionModel().getSelectedItem()));
            groupComboBox.show();
        }


    }

    @FXML
    private void updateCategoryComboBox() {

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








}
