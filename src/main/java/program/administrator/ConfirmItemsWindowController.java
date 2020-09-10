package program.administrator;

import com.jfoenix.controls.JFXButton;
import database.itemsTable.Items;
import database.itemsTableTemp.HelpItemsTemp;
import database.itemsTableTemp.ItemsEntityTemp;
import database.itemsTableTemp.ItemsTemp;
import database.locationsTable.Locations;
import database.locationsTable.LocationsEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.hibernate.exception.ConstraintViolationException;
import program.ImageFx;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ConfirmItemsWindowController implements Initializable {

    @FXML
    StackPane mainWindowStackPane;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    StackPane AddProductWindowStackPane;
    @FXML
    TableView tableViewConfirmItems;

    @FXML
    Label invoiceLabel;


    @FXML
    JFXButton addButton,confirmItemsButton;

    List<ItemsEntityTemp> itemsEntityTempList;
    List<LocationsEntity> locationsEntityList;
    MainWindowController mainWindowController = new MainWindowController();

    List<HelpItemsTemp> helpItemsList =  new LinkedList<HelpItemsTemp>();
    Integer temp ;
    StackPane pane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainWindowController.connecting(this);
        itemsEntityTempList = ItemsTemp.getAllByInvNumber(invoiceLabel.getText());


        TableColumn<HelpItemsTemp,String> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<HelpItemsTemp,String> column2 = new TableColumn<>("Nazwa przedmiotu");
        column2.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<HelpItemsTemp,String> column3 = new TableColumn<>("Numer Faktury");
        column3.setCellValueFactory(new PropertyValueFactory<>("invNumber"));

        TableColumn<HelpItemsTemp,Integer> column4 = new TableColumn<>("Id lokacji");
        column4.setCellValueFactory(new PropertyValueFactory<>("locationID"));

        TableColumn<HelpItemsTemp,Integer> column5 = new TableColumn<>("Id pokoju");
        column5.setCellValueFactory(new PropertyValueFactory<>("roomId"));

        TableColumn<HelpItemsTemp,String> column6 = new TableColumn<>("Wartość Netto");
        column6.setCellValueFactory(new PropertyValueFactory<>("netValue"));

        TableColumn<HelpItemsTemp,String> column7 = new TableColumn<>("Wartość Brutto");
        column7.setCellValueFactory(new PropertyValueFactory<>("grossValue"));

        TableColumn<HelpItemsTemp,String> column8 = new TableColumn<>("Id grupy");
        column8.setCellValueFactory(new PropertyValueFactory<>("groupId"));

        TableColumn<HelpItemsTemp,String> column9 = new TableColumn<>("");
        column9.setCellValueFactory(new PropertyValueFactory<>("actionButton"));


        tableViewConfirmItems.getColumns().addAll(column1,column2,column3,column4,column5,column6,column7,column8,column9);

        List<JFXButton> jfxButtons = new LinkedList<JFXButton>();


        for(int j=0;j<itemsEntityTempList.size();j++){

            locationsEntityList = Locations.getAllFromLocationbyLocationId(itemsEntityTempList.get(j).getLocationID());

            tableViewConfirmItems.getItems().addAll((new HelpItemsTemp(itemsEntityTempList.get(j).getItemId(),
                    itemsEntityTempList.get(j).getItemName(),
                    itemsEntityTempList.get(j).getInvoiceNumber(),
                    itemsEntityTempList.get(j).getLocationID(),
                    itemsEntityTempList.get(j).getGroupId(),
                    itemsEntityTempList.get(j).getNetValue(),
                    itemsEntityTempList.get(j).getGrossValue(),
                    itemsEntityTempList.get(j).getRoomId(),
                    new JFXButton("Zarzadzaj"))
                    ));


            helpItemsList.add((HelpItemsTemp) tableViewConfirmItems.getItems().get(j));
            jfxButtons.add(helpItemsList.get(j).getActionButton());
            int finalJ = j ;

            helpItemsList.get(j).getActionButton().setOnAction(actionEvent -> {

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("ManageItemLocationWindow.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    ManageItemLocationWindowController manageItemLocationWindowController = fxmlLoader.getController();
                    manageItemLocationWindowController.initImageView(ImageFx.convertToJavaFXImage(itemsEntityTempList.get(finalJ).getItemImage(), 300, 300),
                            ImageFx.convertToJavaFXImage(itemsEntityTempList.get(finalJ).getQrCode(),200,200));
                    manageItemLocationWindowController.initData(itemsEntityTempList.get(finalJ), AddProductWindowStackPane);

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

        }


    }

    @FXML
    public  void setInvoiceLabel(String invNumber) throws  IOException{

        this.invoiceLabel.setText(invNumber);
    }

    @FXML
    public  void setConfirmItemsButton(){

        int counter =0;

        if(itemsEntityTempList.size()!=0){

            for(int i=0;i<itemsEntityTempList.size();i++){

                try {
                    if (!itemsEntityTempList.get(i).getLocationID().equals(0)
                            && !itemsEntityTempList.get(i).getRoomId().equals(0)
                            && !itemsEntityTempList.get(i).getGroupId().equals(0)) {
                        counter++;
                        System.out.println(counter);
                    }
                }catch (RuntimeException e) {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setTitle("Informacja");
                    alert.setHeaderText("Któreś z pól jest puste");
                    alert.setContentText("Upewnij się, że wszystkie wymagane pola zostały uzupełnione");
                    alert.showAndWait();
                    break;
                }

            }

            if(counter == itemsEntityTempList.size()){
                for(int i=0;i<itemsEntityTempList.size();i++){
                    Items.insertFromItemsTableTemp(itemsEntityTempList.get(i));
                    ItemsTemp.deleteFromItemsTemp(itemsEntityTempList.get(i).getRowId());
                }

                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacja");
                alert.setHeaderText("Sukces");
                alert.setContentText("Przedmioty z tej faktury zostały przeniesione do tabeli głównej");
                alert.showAndWait();

                Stage stage = (Stage) confirmItemsButton.getScene().getWindow();
                stage.close();

                try {
                    StackPane test = FXMLLoader.load(getClass().getResource("MainWindow.fxml"));
                    pane.getChildren().add(test);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }





        }

    }

    public void setPaneMainWindow(StackPane pane){
        this.pane = pane;
    }


}
