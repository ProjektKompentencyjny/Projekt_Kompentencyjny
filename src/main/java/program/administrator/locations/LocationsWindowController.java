package program.administrator.locations;

import com.jfoenix.controls.JFXButton;
import database.locationsTable.Locations;
import database.locationsTable.LocationsEntity;
import database.locationsTable.LocationsEntityHelp;
import database.roomTable.Room;
import database.roomTable.RoomEntity;
import database.roomTable.RoomEntityHelp;
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
import program.administrator.locations.AddLocationWindowController;
import program.administrator.locations.ManageLocationWindowController;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.List;

public class LocationsWindowController implements Initializable {

    @FXML
    JFXButton addButtonLoc, addButtonRoom,locButton, roomButton;

    @FXML
    Line locLine, roomLine;

    @FXML
    TableView tableViewLocations;

    @FXML
    StackPane stackPaneLocations;

    List<LocationsEntity> locationsEntityList = Locations.getAllFromLocations();
    List<RoomEntity> roomEntityList = Room.getAllFromLocations();

    static int flag;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(flag==0)
            setLocButton();
        else
            setRoomButton();
    }



    @FXML
    public void setRoomButton(){

        addButtonLoc.setDisable(true);
        addButtonLoc.setVisible(false);
        addButtonRoom.setVisible(true);
        addButtonRoom.setDisable(false);

        flag = 1;

        List<RoomEntityHelp> roomEntityHelps = new LinkedList<>();

        locLine.setVisible(false);
        roomLine.setVisible(true);

        locButton.setTextFill(Paint.valueOf("#576271"));
        roomButton.setTextFill(Paint.valueOf("#5fa1fc"));

        tableViewLocations.getColumns().clear();
        tableViewLocations.getItems().clear();


        TableColumn<RoomEntityHelp,Integer> column1 = new TableColumn <>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("idRoom"));

        TableColumn<RoomEntityHelp,String> column2 = new TableColumn <>("Nazwa pomieszczenia");
        column2.setCellValueFactory(new PropertyValueFactory<>("roomName"));

        TableColumn<RoomEntityHelp,Integer> column3 = new TableColumn <>("ID lokacji");
        column3.setCellValueFactory(new PropertyValueFactory<>("locId"));

        TableColumn<RoomEntityHelp,JFXButton> column4 = new TableColumn <>("");
        column4.setCellValueFactory(new PropertyValueFactory<>("actionButton"));



        tableViewLocations.getColumns().addAll(column1,column2,column3,column4);
        List<JFXButton> buttonList = new LinkedList<JFXButton>();

        for(int i=0;i<roomEntityList.size();i++){
            tableViewLocations.getItems().addAll(new RoomEntityHelp(roomEntityList.get(i).getIdRoom(),
                    roomEntityList.get(i).getRoomName(),
                    roomEntityList.get(i).getLocationsEntityId().getIdLocation(),
                    new JFXButton("Zarządzaj")));

            roomEntityHelps.add((RoomEntityHelp) tableViewLocations.getItems().get(i));
            buttonList.add(roomEntityHelps.get(i).getActionButton());
            int finalI = i;

            buttonList.get(i).setOnAction(actionEvent -> {

                try{
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("ManageRoomWindow.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());

                    ManageRoomWindowController manageRoomWindowController = fxmlLoader.getController();
                    manageRoomWindowController.initData(roomEntityList.get(finalI),stackPaneLocations);
                    manageRoomWindowController.initImageView(ImageFx.convertToJavaFXImage(roomEntityList.get(finalI).getRoomImage(),300,300),
                            ImageFx.convertToJavaFXImage(roomEntityList.get(finalI).getQrCode(),200,200));



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
    public void setAddButtonLoc(){

        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AddLocationWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            AddLocationWindowController addLocationWindowController = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.setTitle("Faktura");
            stage.setScene(scene);
            stage.show();
            addLocationWindowController.confirm(stackPaneLocations);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void setAddButtonRoom(){

        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AddRoomWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            AddRoomWindowController addRoomWindowController = fxmlLoader.getController();

            Stage stage = new Stage();
            stage.setTitle("Pomieszczenie");
            stage.setScene(scene);
            stage.show();
            addRoomWindowController.confirm(stackPaneLocations);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML void setLocButton(){


        addButtonLoc.setDisable(false);
        addButtonLoc.setVisible(true);
        addButtonRoom.setVisible(false);
        addButtonRoom.setDisable(true);

        flag = 0;
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
