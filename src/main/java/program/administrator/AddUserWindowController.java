package program.administrator;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.usersTable.Users;
import database.usersTable.UsersEntity;
import database.usersTable.UsersEntityCopy;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import program.UserType;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.sql.Date;
import java.time.Period;

public class AddUserWindowController  implements Initializable {
    @FXML
    ChoiceBox<UserType> typeChoiceBox;
    @FXML
    JFXTextField nameField,
    surnameField,
    loginField,
    passwordField;

    List<UsersEntity> listUserEntity = Users.getAllfromUsers();

    @FXML
    JFXButton confirmButton,closeButton;
    @FXML
    DatePicker birthDatePicker;

    UsersEntityCopy usersEntity = null;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        typeChoiceBox.getItems().setAll(UserType.values());

        int id= listUserEntity.get(listUserEntity.size()-1).getId();

         Date date = new Date(Calendar.getInstance().getTime().getTime());


             confirmButton.setOnAction(actionEvent -> {

                 LocalDate localDate = birthDatePicker.getValue();
                 LocalDate today = LocalDate.now();

                 usersEntity = new UsersEntityCopy(id+1,
                         loginField.getText(),
                         passwordField.getText(),
                         typeChoiceBox.getValue().toString(),
                         nameField.getText(),
                         surnameField.getText(),
                         date,
                         Date.valueOf(localDate),
                         Period.between(localDate,today).getYears()
                 );

                 Users.insert(usersEntity);
             });

    }

    public void close(StackPane pane){
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

    }

}
