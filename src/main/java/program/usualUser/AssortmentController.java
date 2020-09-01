package program.usualUser;

import com.jfoenix.controls.JFXTreeTableView;
import database.invoicesTable.Invoices;
import database.invoicesTable.InvoicesEntity;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AssortmentController implements Initializable {




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void openAddInvoiceWindow(ActionEvent actionEvent){

        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AddInvoiceWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Stage stage = new Stage();
            stage.setTitle("Faktura");
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
