package program.administrator;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ErorWindowController implements Initializable {

    @FXML
    public Label errorTxtField;
    @FXML
    JFXButton confirmButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void setConfirmButton(){
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    public void initData(String info){
        errorTxtField.setText(info);
    }
}
