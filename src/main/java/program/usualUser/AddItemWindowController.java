package program.usualUser;

import com.jfoenix.controls.JFXButton;
import database.itemsTableUsual.ItemsUsual;
import database.itemsTableUsual.ItemsUsualEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class AddItemWindowController implements Initializable {

    @FXML
    TextField productNameTextField,
            netValueTextField,
            grossValueTextField,
            imagePathTextField,
            invoiceNumberTextField,
            amountTextField,
            idTextField        ;

    @FXML
    JFXButton addButton;
    @FXML
    Button saveButton;

    @FXML
    CheckBox idCheckBox;


    List<ItemsUsualEntity> itemsUsualEntityList = ItemsUsual.getAllFromItems();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        idTextField.setText(incrementNumberIn(itemsUsualEntityList.get(itemsUsualEntityList.size()-1).getId()));

    }
    public void confirm(StackPane pane){

        saveButton.setOnAction(actionEvent -> {
            for (int i = 0; i < Integer.parseInt(amountTextField.getText()); i++){

                ItemsUsualEntity itemsUsualEntity = new ItemsUsualEntity(idTextField.getText(), productNameTextField.getText(),
                        invoiceNumberTextField.getText(),
                        Float.parseFloat(netValueTextField.getText()),
                        Float.parseFloat(grossValueTextField.getText()),
                        getimage()
                );
                ItemsUsual.insert(itemsUsualEntity);
            }
            Stage stage = (Stage) addButton.getScene().getWindow();
            stage.close();

            try{

                StackPane test = FXMLLoader.load(getClass().getResource("AddProductWindow.fxml"));
                pane.getChildren().add(test);

            }catch (IOException e ){
                e.printStackTrace();
            }



        });



        AddProductWindowController addProductWindowController = new AddProductWindowController();


        //Stage stage1 = new Stage();
       // stage1.setScene(scene);
       // stage1.show();
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
    public void addImage(ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Files","*jpg"));
        File file = fileChooser.showOpenDialog(null);

        if(file!=null){
            imagePathTextField.setText(file.getAbsolutePath());
        }
    }

    @FXML
    public void idCheckBox(){

        if(idCheckBox.isSelected())
            idTextField.setEditable(true);
        else
            idTextField.setEditable(false);

    }

    private static String incrementNumberIn(String s) {

        String result = "";

        String numberStr = "";

        int i = s.length() - 1;
        for(; i > 0; i--) {

            char c = s.charAt(i);

            if(!Character.isDigit(c))
                break;

            numberStr = c + numberStr;
        }
        int number = Integer.parseInt(numberStr);
        number++;

        result += s.substring(0, i + 1);
        result += number < 10 ? "0" : "";
        result += number;

        return result;
    }

}

