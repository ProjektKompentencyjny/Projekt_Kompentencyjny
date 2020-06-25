package program.administrator;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import program.LoginWindowController;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;


public class DashboardController implements Initializable {


    @FXML
    StackPane stackPanedashboard;
    @FXML
    JFXButton homeScreenButton;
    @FXML
    JFXButton usersButton;
    @FXML
    JFXButton stocktakingButton;
    @FXML
    JFXButton assortmentButton;

    @FXML
    StackPane stackPaneinside;

    @FXML
    Label welcomeLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        homeScreenButton.setDisableVisualFocus(true);

        //LoginWindowController loginWindowController = new LoginWindowController();
        //System.out.println(loginWindowController.getEssa());

    }


    @FXML
    private void openWindowUsers() throws IOException {
        StackPane pane =  FXMLLoader.load((getClass().getResource("Users.fxml")));
        stackPaneinside.getChildren().add(pane);

        usersButton.setTextFill(Color.web("#5fa1fc"));
        stocktakingButton.setTextFill(Color.web("#576271"));
        assortmentButton.setTextFill(Color.web("#576271"));
        homeScreenButton.setTextFill(Color.web("#576271"));
    }

    @FXML
    private void openWindowDashboard() throws IOException {

        StackPane pane =  FXMLLoader.load((getClass().getResource("MainWindow.fxml")));
        stackPaneinside.getChildren().add(pane);

        usersButton.setTextFill(Color.web("#576271"));
        stocktakingButton.setTextFill(Color.web("#576271"));
        assortmentButton.setTextFill(Color.web("#576271"));
        homeScreenButton.setTextFill(Color.web("#5fa1fc"));



    }

    @FXML
    private void openWindowStocktaking() throws IOException {
        StackPane pane =  FXMLLoader.load((getClass().getResource("Stocktaking.fxml")));
        stackPaneinside.getChildren().add(pane);

        stocktakingButton.setTextFill(Color.web("#5fa1fc"));
        homeScreenButton.setTextFill(Color.web("#576271"));
        usersButton.setTextFill(Color.web("#576271"));
        assortmentButton.setTextFill(Color.web("#576271"));
    }

    @FXML
    private void openWindowAssortment() throws IOException {
        StackPane pane =  FXMLLoader.load((getClass().getResource("Assortment.fxml")));
        stackPaneinside.getChildren().add(pane);

        stocktakingButton.setTextFill(Color.web("#576271"));
        homeScreenButton.setTextFill(Color.web("#576271"));
        usersButton.setTextFill(Color.web("#576271"));
        assortmentButton.setTextFill(Color.web("#5fa1fc"));
    }


}
