package program.usualUser;

import com.jfoenix.controls.JFXButton;
import database.itemsTableUsual.HelpItems;
import database.itemsTableUsual.Items;
import database.itemsTableUsual.ItemsEntity;
import database.usersTable.HelpUsers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;


import javafx.stage.StageStyle;

import program.ImageFx;
import program.Qr;

public class AddProductWindowController implements Initializable {
    @FXML
    JFXButton addButton, confirmInvButton;
    @FXML
    Label invoiceLabel;
    @FXML
    TableView tableViewAddProduct;

    @FXML
    StackPane AddProductWindowStackPane;

    List<HelpItems> helpItemsList = new LinkedList<HelpItems>();

    AddInvoiceWindowController addInvoiceWindowController = new AddInvoiceWindowController();

    List<ItemsEntity> itemsEntityList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addInvoiceWindowController.connecting(this);


        itemsEntityList = Items.getAllByInvNumber(invoiceLabel.getText());
        if(itemsEntityList.size()!=0) {

            TableColumn<HelpItems,String> column1 = new TableColumn <>("Numer ID");
            column1.setCellValueFactory(new PropertyValueFactory<>("item_ID"));

            TableColumn<HelpItems,String> column2 = new TableColumn<> ("Nazwa przedmiotu");
            column2.setCellValueFactory(new PropertyValueFactory<>("item_Name"));

            TableColumn <HelpItems,String>column3 = new TableColumn<>("Numer faktury");
            column3.setCellValueFactory(new PropertyValueFactory<>("inv_Number"));

            TableColumn <HelpItems,String>column4 = new TableColumn<>("Wartość netto");
            column4.setCellValueFactory(new PropertyValueFactory<>("net_Value"));

            TableColumn <HelpItems,String>column5 = new TableColumn<>("Wartość brutto");
            column5.setCellValueFactory(new PropertyValueFactory<>("gross_Value"));

            TableColumn<HelpUsers,String> column6 = new TableColumn<>("");
            column6.setCellValueFactory(new PropertyValueFactory<>("actionButton"));

            tableViewAddProduct.getColumns().addAll(column1,column2,column3,column4,column5,column6);

            List<JFXButton> jfxButtons = new LinkedList<JFXButton>();

            for(int j=0;j< itemsEntityList.size();j++){
                tableViewAddProduct.getItems().addAll(new HelpItems(itemsEntityList.get(j).getId(),
                    itemsEntityList.get(j).getItemName(),
                    itemsEntityList.get(j).getInvoiceNumber(),
                    itemsEntityList.get(j).getNetValue(),
                    itemsEntityList.get(j).getGrossValue(),
                    new JFXButton("Zarządzaj")
                ));

            helpItemsList.add((HelpItems) tableViewAddProduct.getItems().get(j));
            jfxButtons.add(helpItemsList.get(j).getActionButton());
            int finalJ= j ;

            jfxButtons.get(j).setOnAction(actionEvent -> {

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("ManageWindow.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    ManageWindowController manageWindowController = fxmlLoader.getController();
                    manageWindowController.initImageView(ImageFx.convertToJavaFXImage(itemsEntityList.get(finalJ).getItemImage(),300,300));
                    manageWindowController.initData(helpItemsList.get(finalJ),AddProductWindowStackPane);

                    Stage stage = new Stage();
                    stage.setTitle("Zarządzanie");
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.show();

                }catch (IOException e ){
                    e.printStackTrace();
                }
            });
            }
        }
    }

    @FXML
    public void setItemsTemp() throws IOException {
        if (itemsEntityList.size() != 0) {

            for (int i = 0; i < itemsEntityList.size(); i++) {
                database.itemsTableTemp.ItemsTemp.insertFromItemsTableUsual(itemsEntityList.get(i),ImageFx.getByteArrayImage(Qr.qrCodeImage(itemsEntityList.get(i).getId())));
            }
        }
        Stage stage = (Stage) confirmInvButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void openAddItemWindow() throws IOException {
        FXMLLoader fxmlLoaderr = new FXMLLoader();
        fxmlLoaderr.setLocation(getClass().getResource("AddItemWindow.fxml"));
        Scene scene = new Scene(fxmlLoaderr.load());
        AddItemWindowController addItemWindowController = fxmlLoaderr.getController();
        addItemWindowController.invoiceNumberTextField.setText(invoiceLabel.getText());
        Stage stage = new Stage();
        stage.setTitle("Dodaj użytkownika");
        stage.setScene(scene);
        stage.show();

        addItemWindowController.confirm(AddProductWindowStackPane);

    }
    @FXML
    public  void setInvoiceLabel(String invNumber) throws  IOException{

        this.invoiceLabel.setText(invNumber);
    }


}
