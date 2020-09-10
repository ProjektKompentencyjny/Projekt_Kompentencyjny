package program.usualUser;

import com.jfoenix.controls.JFXButton;
import database.itemsTableUsual.HelpItemsUsual;
import database.itemsTableUsual.ItemsUsual;
import database.itemsTableUsual.ItemsUsualEntity;
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

    List<HelpItemsUsual> helpItemsUsualList = new LinkedList<HelpItemsUsual>();

    AddInvoiceWindowController addInvoiceWindowController = new AddInvoiceWindowController();

    List<ItemsUsualEntity> itemsUsualEntityList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addInvoiceWindowController.connecting(this);


        itemsUsualEntityList = ItemsUsual.getAllByInvNumber(invoiceLabel.getText());
        if(itemsUsualEntityList.size()!=0) {

            TableColumn<HelpItemsUsual,String> column1 = new TableColumn <>("Numer ID");
            column1.setCellValueFactory(new PropertyValueFactory<>("item_ID"));

            TableColumn<HelpItemsUsual,String> column2 = new TableColumn<> ("Nazwa przedmiotu");
            column2.setCellValueFactory(new PropertyValueFactory<>("item_Name"));

            TableColumn <HelpItemsUsual,String>column3 = new TableColumn<>("Numer faktury");
            column3.setCellValueFactory(new PropertyValueFactory<>("inv_Number"));

            TableColumn <HelpItemsUsual,String>column4 = new TableColumn<>("Wartość netto");
            column4.setCellValueFactory(new PropertyValueFactory<>("net_Value"));

            TableColumn <HelpItemsUsual,String>column5 = new TableColumn<>("Wartość brutto");
            column5.setCellValueFactory(new PropertyValueFactory<>("gross_Value"));

            TableColumn<HelpUsers,String> column6 = new TableColumn<>("");
            column6.setCellValueFactory(new PropertyValueFactory<>("actionButton"));

            tableViewAddProduct.getColumns().addAll(column1,column2,column3,column4,column5,column6);

            List<JFXButton> jfxButtons = new LinkedList<JFXButton>();

            for(int j = 0; j< itemsUsualEntityList.size(); j++){
                tableViewAddProduct.getItems().addAll(new HelpItemsUsual(itemsUsualEntityList.get(j).getId(),
                    itemsUsualEntityList.get(j).getItemName(),
                    itemsUsualEntityList.get(j).getInvoiceNumber(),
                    itemsUsualEntityList.get(j).getNetValue(),
                    itemsUsualEntityList.get(j).getGrossValue(),
                    new JFXButton("Zarządzaj")
                ));

            helpItemsUsualList.add((HelpItemsUsual) tableViewAddProduct.getItems().get(j));
            jfxButtons.add(helpItemsUsualList.get(j).getActionButton());
            int finalJ= j ;

            jfxButtons.get(j).setOnAction(actionEvent -> {

                try {
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("ManageWindow.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    ManageWindowController manageWindowController = fxmlLoader.getController();
                    manageWindowController.initImageView(ImageFx.convertToJavaFXImage(itemsUsualEntityList.get(finalJ).getItemImage(),300,300));
                    manageWindowController.initData(helpItemsUsualList.get(finalJ),AddProductWindowStackPane);

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
        if (itemsUsualEntityList.size() != 0) {

            for (int i = 0; i < itemsUsualEntityList.size(); i++) {
                database.itemsTableTemp.ItemsTemp.insertFromItemsTableUsual(itemsUsualEntityList.get(i),ImageFx.getByteArrayImage(Qr.qrCodeImage(itemsUsualEntityList.get(i).getId())));
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
