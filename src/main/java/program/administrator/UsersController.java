package program.administrator;

import com.jfoenix.controls.JFXButton;
import database.HelpUsers;
import database.Users;
import database.UsersEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UsersController implements Initializable {

    @FXML
    StackPane usersStackPane;

    @FXML
    JFXButton usersButtonUsers;

    @FXML
    TableView tableViewUsers;
    
    @FXML
    JFXButton homeScreenButtonUsers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        //Helpusers helpusers = listawszystkiego.get(0);


        List<UsersEntity> listUserEntity = Users.getAllfromUsers();


        TableColumn<HelpUsers,String> column1 = new TableColumn <>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<HelpUsers,String>("id"));

        TableColumn<HelpUsers,String> column2 = new TableColumn<> ("UserType");
        column2.setCellValueFactory(new PropertyValueFactory<HelpUsers,String>("userType"));

        TableColumn <HelpUsers,String>column3 = new TableColumn<>("Name");
        column3.setCellValueFactory(new PropertyValueFactory<HelpUsers,String>("name"));

        TableColumn <HelpUsers,String>column4 = new TableColumn<>("Surname");
        column4.setCellValueFactory(new PropertyValueFactory<HelpUsers,String>("surname"));

        TableColumn <HelpUsers,String>column5 = new TableColumn<>("UserLogin");
        column5.setCellValueFactory(new PropertyValueFactory<HelpUsers,String>("userLogin"));

        TableColumn<HelpUsers,String> column6 = new TableColumn<>("UserPassword");
        column6.setCellValueFactory(new PropertyValueFactory<HelpUsers,String>("userPassword"));


        tableViewUsers.getColumns().add(column1);
        tableViewUsers.getColumns().add(column2);
        tableViewUsers.getColumns().add(column3);
        tableViewUsers.getColumns().add(column4);
        tableViewUsers.getColumns().add(column5);
        tableViewUsers.getColumns().add(column6);


        for(int j=0;j<listUserEntity.size();j++){
            tableViewUsers.getItems().addAll(new HelpUsers(listUserEntity.get(j).getId(),
                    listUserEntity.get(j).getUserType(),
                    listUserEntity.get(j).getName(),
                    listUserEntity.get(j).getSurname(),
                    listUserEntity.get(j).getUserLogin(),
                    listUserEntity.get(j).getUserPassword()));
        }

        //final ObservableList<KlasaPomocnicza> data =
        //FXCollections.observableArrayList(new KlasaPomocnicza(1,"test","siema","byq","co","tm"));

        //tableViewDashboard.setItems(data);

        //System.out.println(listawszystkiego.get(0).getName());
        //tableViewDashboard.getItems().add(new KlasaPomocnicza(1,"test","siema","byq","co","tm"));
        //tableViewDashboard.getItems().add(new KlasaPomocnicza(2,"test","siema","byq","co","tm"));

        /*
        TableColumn<String, String> tableColumn = new TableColumn<>("Name");
        tableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        tableViewDashboard.getColumns().add(tableColumn);
        ObservableList<String> items = FXCollections.singletonObservableList(lista.get(0).getName());
        tableViewDashboard.setItems(items);
        */

        //List<String> listColNames = Users.getColumnNames();
    }

}
