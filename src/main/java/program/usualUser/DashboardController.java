package program.usualUser;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    StackPane stackPaneinside;
    @FXML
    JFXButton homeScreenButton;
    @FXML
    JFXButton assortmentButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void openMainWindow() throws IOException {

        StackPane pane =  FXMLLoader.load((getClass().getResource("MainWindow.fxml")));
        stackPaneinside.getChildren().add(pane);
        homeScreenButton.setTextFill(Color.web("#5fa1fc"));
        assortmentButton.setTextFill(Color.web("#576271"));
    }

    @FXML
    private void openAssortmentWindow() throws IOException {

        StackPane pane =  FXMLLoader.load((getClass().getResource("Assortment.fxml")));
        stackPaneinside.getChildren().add(pane);
        homeScreenButton.setTextFill(Color.web("#576271"));
        assortmentButton.setTextFill(Color.web("#5fa1fc"));
    }

}
