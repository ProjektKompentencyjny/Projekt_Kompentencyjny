package program.administrator;

import com.jfoenix.controls.JFXButton;
import database.locationsTable.Locations;
import database.locationsTable.LocationsEntity;
import database.locationsTable.LocationsEntityHelp;
import database.usersTable.HelpUsers;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import program.ImageFx;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.List;

public class LocationsWindowController implements Initializable {

    @FXML
    JFXButton addButton, locButton, roomButton;

    @FXML
    Line locLine, roomLine;

    @FXML
    TableView tableViewLocations;

    @FXML
    StackPane stackPaneLocations;

    List<LocationsEntity> locationsEntityList = Locations.getAllFromLocations();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setLocButton();
    }


    @FXML
    private void setRoomButton(){

        locLine.setVisible(false);
        roomLine.setVisible(true);

        locButton.setTextFill(Paint.valueOf("#576271"));
        roomButton.setTextFill(Paint.valueOf("#5fa1fc"));

    }

    @FXML void setLocButton(){

        List<LocationsEntityHelp> locationsEntityHelpList = new LinkedList<>();
        tableViewLocations.getColumns().clear();
        tableViewLocations.getItems().clear();

        locLine.setVisible(true);
        roomLine.setVisible(false);
        locButton.setTextFill(Paint.valueOf("#5fa1fc"));
        roomButton.setTextFill(Paint.valueOf("#576271"));


        TableColumn<LocationsEntityHelp,Integer> column1 = new TableColumn <>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("idLocation"));

        TableColumn<LocationsEntityHelp,String> column2 = new TableColumn <>("Nazwa lokacji");
        column2.setCellValueFactory(new PropertyValueFactory<>("nameLocation"));

        TableColumn<LocationsEntityHelp,String> column3 = new TableColumn <>("Adres");
        column3.setCellValueFactory(new PropertyValueFactory<>("streetAddres"));

        TableColumn<LocationsEntityHelp,String> column4 = new TableColumn <>("Kod pocztowy");
        column4.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        TableColumn<LocationsEntityHelp,String> column5 = new TableColumn <>("Miasto");
        column5.setCellValueFactory(new PropertyValueFactory<>("city"));

        TableColumn<LocationsEntityHelp,String> column6 = new TableColumn <>("");
        column6.setCellValueFactory(new PropertyValueFactory<>("actionButton"));

        tableViewLocations.getColumns().addAll(column1,column2,column3,column4,column5,column6);

        List<JFXButton> jfxButtons = new LinkedList<JFXButton>();

        for(int j =0; j<locationsEntityList.size();j++){
            tableViewLocations.getItems().addAll(new LocationsEntityHelp(locationsEntityList.get(j).getIdLocation(),
                    locationsEntityList.get(j).getNameLocation(),
                    locationsEntityList.get(j).getStreetAddres(),
                    locationsEntityList.get(j).getPostalCode(),
                    locationsEntityList.get(j).getCity(),
                    new JFXButton("Zarządzaj")));

            locationsEntityHelpList.add((LocationsEntityHelp) tableViewLocations.getItems().get(j));
            jfxButtons.add(locationsEntityHelpList.get(j).getActionButton());
            int finalJ = j;

            jfxButtons.get(j).setOnAction(actionEvent -> {

                try{
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("ManageLocationWindow.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    ManageLocationWindowController manageLocationWindowController = fxmlLoader.getController();
                    manageLocationWindowController.initData(locationsEntityList.get(finalJ),stackPaneLocations);
                    manageLocationWindowController.initImageView(ImageFx.convertToJavaFXImage(locationsEntityList.get(finalJ).getLocImage(),300,300),
                            ImageFx.convertToJavaFXImage(locationsEntityList.get(finalJ).getQrCode(),200,200));

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
}
