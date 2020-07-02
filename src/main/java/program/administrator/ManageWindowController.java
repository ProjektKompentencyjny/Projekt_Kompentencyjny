package program.administrator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.HelpUsers;
import database.Users;
import database.UsersEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.hibernate.usertype.UserCollectionType;
import program.DashboardControllerAdministrator;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class ManageWindowController implements Initializable {
    @FXML
    JFXTextField nameField,
            surnameField,
            loginField,
            passwordField;

    HelpUsers selectedUser;
    int id;

    @FXML
    JFXButton confirmButton,
            deleteButton,
            closeButton;

    public UsersController usersController;


    public void initData(HelpUsers helpUsers,StackPane pane) {

        selectedUser = helpUsers;
        nameField.setText(selectedUser.getName());
        surnameField.setText(selectedUser.getSurname());
        loginField.setText(selectedUser.getUserLogin());
        passwordField.setText(selectedUser.getUserPassword());

        confirmButton.setOnAction(actionEvents -> {
            Users.update(selectedUser.getId(), nameField.getText(), surnameField.getText(), passwordField.getText(), loginField.getText());
        });

        deleteButton.setOnAction(actionEvent -> {

            Users.delete(selectedUser.getId());
        });

        closeButton.setOnAction(actionEvent -> {

            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();

            try {
                StackPane test = FXMLLoader.load(getClass().getResource("Users.fxml"));
                pane.getChildren().add(test);
            } catch (IOException e) {
                e.printStackTrace();
            }


        });






                //UsersController usersController = loader.getController();
               // StackPane stackPane = usersController.stackPaneUsers;

               // StackPane test = FXMLLoader.load(getClass().getResource("Users.fxml"));
               // stackPane.getChildren().add(test);


                //Stage stage1 = new Stage();
                //stage1.setScene(new Scene(root));
                //stage1.show();




    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void close() {

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();









    }
}

