package program;


import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.Users;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class OknoLogowaniaController {

    @FXML
    JFXTextField pole_Login;
    @FXML
    JFXPasswordField pole_Haslo;
    @FXML
    Label loginSuccess;

    @FXML
    private void test() throws IOException {
        String temp = pole_Login.getText();
        String temp2 = pole_Haslo.getText();


        if(Users.checkUserLoginPassword(pole_Login.getText(),pole_Haslo.getText()))
            loginSuccess.setVisible(true);

    }
}
