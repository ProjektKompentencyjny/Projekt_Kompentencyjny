package program.accountant;

import com.jfoenix.controls.JFXButton;
import database.invoicesTable.Invoices;
import database.itemsTableTemp.HelpItemsTemp;
import database.itemsTableTemp.ItemsTemp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    List<HelpItemsTemp> helpItemsTemps = new LinkedList<>();
    List<String> test = ItemsTemp.getInvoiceNumber();
    static String temp ;

    @FXML
    TableView tableViewMainWindow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TableColumn<HelpItemsTemp,String> column1 = new TableColumn <>("Numer Faktury");
        column1.setCellValueFactory(new PropertyValueFactory<>("invNumber"));

        TableColumn<HelpItemsTemp,String> column2 = new TableColumn<>("");
        column2.setCellValueFactory(new PropertyValueFactory<>("actionButton"));

        TableColumn<HelpItemsTemp,String> column3 = new TableColumn<>("Pobierz pdf");
        column3.setCellValueFactory(new PropertyValueFactory<>("actionButton2"));

        tableViewMainWindow.getColumns().addAll(column1,column2,column3);

        List<JFXButton> jfxButtons = new LinkedList<JFXButton>();
        List<JFXButton> jfxButtons2 = new LinkedList<JFXButton>();
        for(int j=0;j<test.size();j++){
            tableViewMainWindow.getItems().addAll(new HelpItemsTemp(test.get(j),
                    new JFXButton("Zarządzaj"),
                    new JFXButton("Pobierz pdf")
            ));

            helpItemsTemps.add((HelpItemsTemp) tableViewMainWindow.getItems().get(j));
            jfxButtons.add(helpItemsTemps.get(j).getActionButton());
            jfxButtons2.add(helpItemsTemps.get(j).getActionButton2());
            int finalJ = j;


            jfxButtons.get(j).setOnAction(actionEvent -> {
                //List<ItemsEntityTemp> itemsEntityTemps = ItemsTemp.getAllByInvNumber(helpItemsTemps.get(finalJ).getInv_Number());

                try {
                    temp = helpItemsTemps.get(finalJ).getInvNumber();
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("ConfirmItemsWindow.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    //ConfirmItemsWindowController confirmItemsWindowController = fxmlLoader.getController();
                    //confirmItemsWindowController.invoiceLabel.setText(helpItemsTemps.get(finalJ).getInvNumber());
                    Stage stage = new Stage();
                    stage.setTitle("Zarządzanie");
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();

                }catch (IOException e){
                    e.printStackTrace();;
                }


            });

            jfxButtons2.get(j).setOnAction(actionEvent -> {

                program.administrator.MainWindowController.byteArrayToFile(Invoices.getPdf(helpItemsTemps.get(finalJ).getInvNumber()));

            });



            //helpItemsTemps.add((HelpItemsTemp) tableViewMainWindow.getItems().get(j));
            // jfxButtons.add(helpItemsTemps.get(j).getActionButton());
        }





    }

    public void connecting(ConfirmItemsWindowController controller){
        try{
            controller.setInvoiceLabel(temp);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

    }



}
