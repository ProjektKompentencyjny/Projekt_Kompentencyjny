package program;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import program.administrator.assortment.AssortmentController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardControllerUsualUser implements Initializable {
    @FXML
    StackPane stackPaneinside;
    @FXML
    JFXButton assortmentButton,
            homeScreenButton,
            logoutButton,
            myAccountButton,
            assortmentButtonFinal;
    @FXML
    ImageView userInfo;
    int temp = 0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        userInfo.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            if(temp==0) {
                logoutButton.setDisable(false);
                logoutButton.setVisible(true);

                myAccountButton.setDisable(false);
                myAccountButton.setVisible(true);

                temp =1;
            }else {
                logoutButton.setDisable(true);
                logoutButton.setVisible(false);

                myAccountButton.setDisable(true);
                myAccountButton.setVisible(false);

                temp = 0;
            }
            event.consume();
        });

    }

    @FXML
    private void openMainWindow() throws IOException {

        StackPane pane =  FXMLLoader.load((getClass().getResource("usualUser/MainWindow.fxml")));
        stackPaneinside.getChildren().add(pane);
        homeScreenButton.setTextFill(Color.web("#5fa1fc"));
        assortmentButton.setTextFill(Color.web("#576271"));
        assortmentButtonFinal.setTextFill(Color.web("#576271"));
    }

    @FXML
    private void openAssortmentWindow() throws IOException {

        StackPane pane =  FXMLLoader.load((getClass().getResource("usualUser/Assortment.fxml")));
        stackPaneinside.getChildren().add(pane);
        homeScreenButton.setTextFill(Color.web("#576271"));
        assortmentButtonFinal.setTextFill(Color.web("#576271"));
        assortmentButton.setTextFill(Color.web("#5fa1fc"));
    }

    @FXML
    private void openAssortmentWindowFinal() throws IOException {

        AssortmentController.flag= 0;
        StackPane pane =  FXMLLoader.load((AssortmentController.class.getResource("Assortment.fxml")));
        stackPaneinside.getChildren().add(pane);
        homeScreenButton.setTextFill(Color.web("#576271"));
        assortmentButton.setTextFill(Color.web("#576271"));
        assortmentButtonFinal.setTextFill(Color.web("#5fa1fc"));
    }



    @FXML
    private void logout() throws  IOException{

        Stage stage;
        stage = (Stage) homeScreenButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load((getClass().getResource("loginWindow/loginWindow.fxml")));
        Scene scene  = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true );
        stage.show();

    }

}
