package program.usualUser;

import com.jfoenix.controls.JFXButton;
import database.itemsTableUsual.HelpItemsUsual;
import database.itemsTableUsual.ItemsUsual;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import program.ImageFx;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ManageWindowController implements Initializable {

    @FXML
    ImageView itemImage;
    @FXML
    TextField idNumberTxtField,
        nameProductTxtField,
        invoiceNumberTxtField,
        nettoValueTxtField,
        grossValueTxtField,
        imagePathTxtField
    ;
    @FXML
    JFXButton editButton,
        addButton
    ;
    @FXML
    Button closeButton,saveButton,deleteButton;
    @FXML
    Label imageLabel;

    String id ;
    ImageFx imageFx = new ImageFx();

    public void initImageView(Image image){

        itemImage.setImage(image);

    }
    public void initData(HelpItemsUsual helpItemsUsual, StackPane pane){

        idNumberTxtField.setText(helpItemsUsual.getItem_ID());
        nameProductTxtField.setText(helpItemsUsual.getItem_Name());
        invoiceNumberTxtField.setText(helpItemsUsual.getInv_Number());
        nettoValueTxtField.setText(helpItemsUsual.getNet_Value().toString());
        grossValueTxtField.setText(helpItemsUsual.getGross_Value().toString());

        id  = idNumberTxtField.getText();

        saveButton.setOnAction(actionEvent -> {
            ItemsUsual.update(idNumberTxtField.getText(),
                    nameProductTxtField.getText(),
                    Float.parseFloat(nettoValueTxtField.getText()),
                    Float.parseFloat(grossValueTxtField.getText()),
                    getimage(),
                    invoiceNumberTxtField.getText(),
                    id
            );
        }



        );

        closeButton.setOnAction(actionEvent -> {

            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

            try {
                StackPane test = FXMLLoader.load(getClass().getResource("AddProductWindow.fxml"));
                pane.getChildren().add(test);
            } catch (IOException e) {
                e.printStackTrace();
            }


        });

        deleteButton.setOnAction(actionEvent -> {
            ItemsUsual.deletefromInvoice(id,invoiceNumberTxtField.getText());
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

            try {
                StackPane test = FXMLLoader.load(getClass().getResource("AddProductWindow.fxml"));
                pane.getChildren().add(test);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
    public void edit(){

        editButton.setOnAction(actionEvent -> {

            idNumberTxtField.setEditable(true);
            nameProductTxtField.setEditable(true);
            nettoValueTxtField.setEditable(true);
            grossValueTxtField.setEditable(true);

            imageLabel.setVisible(true);
            imageLabel.setDisable(false);

            addButton.setVisible(true);
            addButton.setDisable(false);

            imagePathTxtField.setEditable(true);
            imagePathTxtField.setDisable(false);
            imagePathTxtField.setVisible(true);

            saveButton.setVisible(true);
            saveButton.setDisable(false);

            deleteButton.setVisible(true);
            deleteButton.setDisable(false);


        });

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        edit();

    }

    @FXML
    public void addImage() throws MalformedURLException {
        imageFx.addImage(imagePathTxtField,itemImage);
    }

    private byte[] getimage() {
        if (imagePathTxtField.getText().equals("")) {
            return ItemsUsual.getByteArrayImage(invoiceNumberTxtField.getText(),id);
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
