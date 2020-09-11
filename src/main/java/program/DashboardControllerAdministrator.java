package program;

import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import javafx.stage.Stage;
import program.administrator.Stocktaking.StocktakingController;
import program.administrator.assortment.AssortmentController;
import program.administrator.locations.AddLocationWindowController;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;


public class DashboardControllerAdministrator implements Initializable {
    @FXML
    StackPane stackPanedashboard;
    @FXML
    public JFXButton homeScreenButton,
            usersButton,
            stocktakingButton,
            assortmentButton,
            logoutButton,
            myAccountButton,
            locationButton;

    @FXML
    public StackPane stackPaneinside;

    @FXML
    Label welcomeLabel;

    public StackPane getStackPaneinside() {
        return stackPaneinside;
    }

    public void setStackPaneinside(StackPane stackPaneinside) {
        this.stackPaneinside = stackPaneinside;
    }

    @FXML
    ImageView userInfo;



    int temp = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Users.update();

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

        homeScreenButton.setDisableVisualFocus(true);

        //LoginWindowController loginWindowController = new LoginWindowController();
        //System.out.println(loginWindowController.getEssa());

    }

    @FXML
    private void openWindowUsers() throws IOException {
        StackPane pane =  FXMLLoader.load((getClass().getResource("administrator/Users.fxml")));
        stackPaneinside.getChildren().add(pane);

        usersButton.setTextFill(Color.web("#5fa1fc"));
        stocktakingButton.setTextFill(Color.web("#576271"));
        assortmentButton.setTextFill(Color.web("#576271"));
        homeScreenButton.setTextFill(Color.web("#576271"));
        locationButton.setTextFill(Color.web("#576271"));
    }

    @FXML
    private void openWindowDashboard() throws IOException {

        StackPane pane =  FXMLLoader.load((getClass().getResource("administrator/MainWindow.fxml")));
        stackPaneinside.getChildren().add(pane);

        usersButton.setTextFill(Color.web("#576271"));
        stocktakingButton.setTextFill(Color.web("#576271"));
        assortmentButton.setTextFill(Color.web("#576271"));
        locationButton.setTextFill(Color.web("#576271"));
        homeScreenButton.setTextFill(Color.web("#5fa1fc"));



    }

    @FXML
    private void openWindowStocktaking() throws IOException {
        StackPane pane =  FXMLLoader.load((StocktakingController.class.getResource("Stocktaking.fxml")));
        stackPaneinside.getChildren().add(pane);

        stocktakingButton.setTextFill(Color.web("#5fa1fc"));
        homeScreenButton.setTextFill(Color.web("#576271"));
        usersButton.setTextFill(Color.web("#576271"));
        assortmentButton.setTextFill(Color.web("#576271"));
        locationButton.setTextFill(Color.web("#576271"));
    }

    @FXML
    private void openWindowAssortment() throws IOException {
        AssortmentController.flag=2;
        StackPane pane =  FXMLLoader.load((AssortmentController.class.getResource("Assortment.fxml")));
        stackPaneinside.getChildren().add(pane);

        stocktakingButton.setTextFill(Color.web("#576271"));
        homeScreenButton.setTextFill(Color.web("#576271"));
        usersButton.setTextFill(Color.web("#576271"));
        locationButton.setTextFill(Color.web("#576271"));
        assortmentButton.setTextFill(Color.web("#5fa1fc"));
    }

    @FXML
    private void openWindowLocation() throws IOException{
        StackPane pane =  FXMLLoader.load((AddLocationWindowController.class.getResource("LocationsWindow.fxml")));
        stackPaneinside.getChildren().add(pane);

        stocktakingButton.setTextFill(Color.web("#576271"));
        homeScreenButton.setTextFill(Color.web("#576271"));
        usersButton.setTextFill(Color.web("#576271"));
        assortmentButton.setTextFill(Color.web("#576271"));
        locationButton.setTextFill(Color.web("#5fa1fc"));

    }

    @FXML
    private void logout() throws  IOException{

        Stage stage;
        stage = (Stage) usersButton.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load((getClass().getResource("loginWindow/loginWindow.fxml")));
        Scene scene  = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(true );
        stage.show();

    }


}
