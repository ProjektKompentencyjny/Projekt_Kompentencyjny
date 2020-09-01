package program.administrator;

import com.jfoenix.controls.JFXButton;
import database.usersTable.HelpUsers;
import database.usersTable.Users;
import database.usersTable.UsersEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class UsersController implements Initializable {

    @FXML
    TableView tableViewUsers;

    @FXML
    StackPane stackPaneUsers;

    List<UsersEntity> listUserEntity = Users.getAllfromUsers();
    List<HelpUsers> helpUsersList = new LinkedList<HelpUsers>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TableColumn<HelpUsers,String> column1 = new TableColumn <>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<HelpUsers,String> column2 = new TableColumn<> ("Typ użytkownika");
        column2.setCellValueFactory(new PropertyValueFactory<>("userType"));

        TableColumn <HelpUsers,String>column3 = new TableColumn<>("Imie");
        column3.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn <HelpUsers,String>column4 = new TableColumn<>("Nazwisko");
        column4.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn <HelpUsers,String>column5 = new TableColumn<>("Login");
        column5.setCellValueFactory(new PropertyValueFactory<>("userLogin"));

        TableColumn<HelpUsers,String> column6 = new TableColumn<>("Hasło");
        column6.setCellValueFactory(new PropertyValueFactory<>("userPassword"));

        TableColumn<HelpUsers,String> column7 = new TableColumn<>("Data utworzenia");
        column7.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

        TableColumn<HelpUsers,String> column8 = new TableColumn<>("Data urodzenia");
        column8.setCellValueFactory(new PropertyValueFactory<>("birthDate"));

        TableColumn<HelpUsers,String> column9 = new TableColumn<>("Wiek");
        column9.setCellValueFactory(new PropertyValueFactory<>("age"));

        TableColumn<HelpUsers,String> column10 = new TableColumn<>("");
        column10.setCellValueFactory(new PropertyValueFactory<>("actionButton"));

        tableViewUsers.getColumns().addAll(column1,column2,column3,column4,column5,column6,column7,column8,column9,column10);

        List<JFXButton> jfxButtons = new LinkedList<JFXButton>();


        for(int j=0;j<listUserEntity.size();j++){
            tableViewUsers.getItems().addAll(new HelpUsers(listUserEntity.get(j).getId(),
                    listUserEntity.get(j).getUserType(),
                    listUserEntity.get(j).getName(),
                    listUserEntity.get(j).getSurname(),
                    listUserEntity.get(j).getUserLogin(),
                    listUserEntity.get(j).getUserPassword(),
                    listUserEntity.get(j).getCreationDate(),
                    listUserEntity.get(j).getBirthDate(),
                    listUserEntity.get(j).getAge(),
                    new JFXButton(("Zarządzaj"))
                    ));

            helpUsersList.add((HelpUsers) tableViewUsers.getItems().get(j));
            jfxButtons.add(helpUsersList.get(j).getActionButton());
            int finalJ = j;

            jfxButtons.get(j).setOnAction(actionEvent ->{
                try{
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("manageWindow.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    ManageWindowController manageWindowController = fxmlLoader.getController();
                    manageWindowController.initData(helpUsersList.get(finalJ),stackPaneUsers);
                    Stage stage = new Stage();
                    stage.setTitle("Zarządzanie");
                    stage.setScene(scene);
                    stage.show();
                }catch (IOException e){
                    e.printStackTrace();
                }

            });
        }
    }

    @FXML
    private void openAddUserWindow() throws IOException{
        FXMLLoader fxmlLoaderr = new FXMLLoader();
        fxmlLoaderr.setLocation(getClass().getResource("AddUserWindow.fxml"));

        Scene scene = new Scene(fxmlLoaderr.load());
        Stage stage = new Stage();
        stage.setTitle("Dodaj użytkownika");
        stage.setScene(scene);
        stage.show();

        AddUserWindowController addUserWindowController = fxmlLoaderr.getController();
        addUserWindowController.close(stackPaneUsers);
    }
}
