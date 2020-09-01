package program.usualUser;

import com.jfoenix.controls.JFXButton;
import database.invoicesTable.Invoices;
import database.invoicesTable.InvoicesEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.engine.jdbc.BlobProxy;
import org.hibernate.tool.schema.Action;
import program.administrator.ManageWindowController;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddInvoiceWindowController  implements Initializable {

    @FXML
    Button closeButton,saveButton;
    @FXML
    TextField invNumberTextField,
    netValueTextField,
    grossValueTextField,
    imagePathTextField;
    @FXML
    DatePicker invoiceDatePicker;
    @FXML
    JFXButton addButton;

    static String temp ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void close(ActionEvent actionEvent) throws IOException {

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    @FXML
    public void addImage(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files","*pdf"));
        File file = fileChooser.showOpenDialog(null);

        if(file!=null){
            imagePathTextField.setText(file.getAbsolutePath());
        }
    }

    public byte[] getimage(){
        Path path = Paths.get(imagePathTextField.getText());
        byte [] data = null;
        try {
            data = Files.readAllBytes(path);
        }catch (IOException e){
            e.printStackTrace();
        }
        return data;
    }

    @FXML
    public void confirm() throws IOException {

        LocalDate localDate = invoiceDatePicker.getValue();

        InvoicesEntity invoicesEntity = new InvoicesEntity(invNumberTextField.getText(),
                Date.valueOf(localDate),
                Float.parseFloat(netValueTextField.getText()),
                Float.parseFloat(grossValueTextField.getText()),
                getimage());

        Invoices.insert(invoicesEntity);

        temp= invNumberTextField.getText();

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();


        FXMLLoader fxmlLoaderr = new FXMLLoader();
        fxmlLoaderr.setLocation(getClass().getResource("AddProductWindow.fxml"));
        Scene scene = new Scene(fxmlLoaderr.load());

        AddProductWindowController addProductWindowController = fxmlLoaderr.getController();
        addProductWindowController.invoiceLabel.setText(invNumberTextField.getText());

        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.show();
    }

    public void connecting(AddProductWindowController controller){
        try{
            controller.setInvoiceLabel(temp);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

    }











}
