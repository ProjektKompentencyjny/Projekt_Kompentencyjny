package program;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import database.usersTable.Users;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;

public class LoginWindowController {

    @FXML
    public JFXTextField loginField;
    @FXML
    JFXPasswordField passwordField;
    @FXML
    Pane wrongPassPane;
    @FXML
    Button wrongPassButton;
    @FXML
    AnchorPane loginWindowPane;
    @FXML
    JFXButton loginButton;
    @FXML
    private void checkLogin() throws IOException {

        if(Users.checkUserLoginPassword(loginField.getText(),passwordField.getText())) {
            Stage stage;
            stage = (Stage) loginButton.getScene().getWindow();
            stage.close();

            UserType userType = Users.getUserType(loginField.getText());
            //UserType.fromString(userType);

            switch (userType){
                case Administrator:
                    Parent root = FXMLLoader.load((getClass().getResource("administrator/Dashboard.fxml")));
                    Scene scene  = new Scene(root);
                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.show();
                    break;
                case Ksiegowa:
                    Parent root1 = FXMLLoader.load((getClass().getResource("accountant/DashboardAccountant.fxml")));
                    Scene scene1  = new Scene(root1);
                    stage.setScene(scene1);
                    stage.setResizable(true);
                    stage.show();
                    break;
                case Zwykly_Uzytkownik:
                    Parent root2 = FXMLLoader.load((getClass().getResource("usualUser/Dashboard.fxml")));
                    Scene scene2  = new Scene(root2);
                    stage.setScene(scene2);
                    stage.setResizable(true);
                    stage.show();
                    break;
            }
        }else{
            wrongPassPane.setDisable(false);
            wrongPassPane.setVisible(true);
        }
    }



    @FXML
    private void closePane(){
        wrongPassPane.setVisible(false);
        wrongPassPane.setDisable(true);
    }







}
