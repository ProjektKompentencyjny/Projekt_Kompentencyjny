package program.accountant;

import com.jfoenix.controls.JFXButton;
import database.itemsTableTemp.HelpItemsTemp;
import database.itemsTableTemp.ItemsEntityTemp;
import database.itemsTableTemp.ItemsTemp;
import database.locationsTable.Locations;
import database.locationsTable.LocationsEntity;
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
import program.ImageFx;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ConfirmItemsWindowController implements Initializable {

    @FXML
    StackPane AddProductWindowStackPane;
    @FXML
    TableView<HelpItemsTemp> tableViewConfirmItems;

    int a=0;
    @FXML
    Label invoiceLabel;

    @FXML
    JFXButton addButton,confirmInvButton;

    List<ItemsEntityTemp> itemsEntityTempList;
    List<LocationsEntity> locationsEntityList;

    program.accountant.MainWindowController mainWindowController = new program.accountant.MainWindowController();

    List<HelpItemsTemp> helpItemsList =  new LinkedList<HelpItemsTemp>();
    Integer temp ;
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

        TableColumn<HelpItemsTemp,String> column4 = new TableColumn<>("Id lokacji");
        column4.setCellValueFactory(new PropertyValueFactory<>("locationID"));

        TableColumn<HelpItemsTemp,String> column5 = new TableColumn<>("Id pokoju");
        column5.setCellValueFactory(new PropertyValueFactory<>("roomId"));

        TableColumn<HelpItemsTemp,String> column6 = new TableColumn<>("Wartość Netto");
        column6.setCellValueFactory(new PropertyValueFactory<>("netValue"));

        TableColumn<HelpItemsTemp,String> column7 = new TableColumn<>("Wartość Brutto");
        column7.setCellValueFactory(new PropertyValueFactory<>("grossValue"));

        TableColumn<HelpItemsTemp,Integer> column8 = new TableColumn<>("Id grupy");
        column8.setCellValueFactory(new PropertyValueFactory<>("groupId"));

        TableColumn<HelpItemsTemp,String> column9 = new TableColumn<>("");
        column9.setCellValueFactory(new PropertyValueFactory<>("actionButton"));


        tableViewConfirmItems.getColumns().addAll(column1,column2,column3,column4,column5,column6,column7,column8,column9);

        List<JFXButton> jfxButtons = new LinkedList<JFXButton>();


        for(int j=0;j<itemsEntityTempList.size();j++) {

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
            int finalJ = j;

            helpItemsList.get(j).getActionButton().setOnAction(actionEvent -> {

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("ManageItemGroupWindow.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    ManageItemGroupWindowController manageItemGroupWindowController = fxmlLoader.getController();
                    manageItemGroupWindowController.initImageView(ImageFx.convertToJavaFXImage(itemsEntityTempList.get(finalJ).getItemImage(), 300, 300),
                            ImageFx.convertToJavaFXImage(itemsEntityTempList.get(finalJ).getQrCode(),200,200));
                    manageItemGroupWindowController.initData(itemsEntityTempList.get(finalJ), AddProductWindowStackPane);

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


        column8.setCellFactory(column ->{
            return new TableCell<HelpItemsTemp,Integer>(){
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty) { //If the cell is empty
                        setText("");
                        setStyle("");
                    }else {
                        if(item!=null) {
                            setText(String.valueOf(item));
                            setStyle("");
                        }
                        HelpItemsTemp helpItemsTemp = getTableView().getItems().get(getIndex());
                        if(helpItemsTemp.getGroupId()==null){
                            setStyle("-fx-border-color: #fd0000");
                            setText("Wprowadź Dane !");
                        }
                    }
                }
            };


        });






    }


    @FXML
    public  void setInvoiceLabel(String invNumber) throws  IOException{

        this.invoiceLabel.setText(invNumber);
    }



}
