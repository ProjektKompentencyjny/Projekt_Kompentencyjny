package program.administrator.Stocktaking;

import database.itemsTable.Items;
import database.itemsTable.ItemsEntity;
import database.itemsTableTemp.ItemsTemp;
import database.locationsTable.Locations;
import database.locationsTable.LocationsEntity;
import database.stocktaking.Stocktaking;
import database.stocktaking.StocktakingEntity;
import database.stocktakingItemsTable.StocktakingItems;
import database.stocktakingItemsTable.StocktakingItemsEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AddStockWindowController implements Initializable {

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    Button closeButton, saveButton;

    @FXML
    TextField stockNameTextField;

    @FXML
    ComboBox<LocationsEntity> locComboBox;

    @FXML
    DatePicker loadStockDatePicker;

    StackPane pane;

    StocktakingItemsEntity stocktakingItemsEntity;

    int counter=1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        locComboBox.getItems().addAll(Locations.getAllFromLocations());

    }

    @FXML
    private void setSaveButton()throws IOException{

        try {
            LocalDate localDate = loadStockDatePicker.getValue();
            LocalDate today = LocalDate.now();
            StocktakingEntity stocktakingEntity = new StocktakingEntity(
                    stockNameTextField.getText(),
                    locComboBox.getValue(),
                    Date.valueOf(today),
                    Date.valueOf(localDate)
            );
            Stocktaking.insertStockating(stocktakingEntity);

            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setTitle("Informacja");
            alert.setContentText("Dodano Pomyślnie");
            alert.setHeaderText("Sukces!");
            alert.showAndWait();

            List<ItemsEntity> itemsEntityList = Items.getAllByRoomId(locComboBox.getValue(),Date.valueOf(localDate));

            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();

            StackPane test = FXMLLoader.load(getClass().getResource("Stocktaking.fxml"));
            pane.getChildren().add(test);

            for(int i=0;i<itemsEntityList.size();i++){


                for(int j=i+1 ;j<itemsEntityList.size();j++){
                    if(itemsEntityList.get(i).getItemId().equals(itemsEntityList.get(j).getItemId())
                            && itemsEntityList.get(i).getRoomEntity().equals(itemsEntityList.get(j).getRoomEntity())){
                        counter++;
                        itemsEntityList.remove(j);
                        j--;
                    }
                }
                stocktakingItemsEntity = new StocktakingItemsEntity(itemsEntityList.get(i).getItemId(),
                        itemsEntityList.get(i).getItemName(),
                        itemsEntityList.get(i).getItemImage(),
                        itemsEntityList.get(i).getQrCode(),
                        itemsEntityList.get(i).getRoomEntity().getIdRoom(),
                        itemsEntityList.get(i).getRoomEntity().getRoomName(),
                        counter,
                        stocktakingEntity);

                StocktakingItems.insertStockItem(stocktakingItemsEntity);
                counter = 1;
            }



        }catch (RuntimeException e ){
           // alert.setAlertType(Alert.AlertType.WARNING);
           // alert.setTitle("Błąd");
           // alert.setHeaderText("Błąd");
           // alert.setContentText("Któreś z pól jest puste, lub zawiera niepoprawne dane !!");
           // alert.showAndWait();
            e.printStackTrace();

        }





    }

    public void setStackPane(StackPane stackPane){
        this.pane = stackPane;
    }

    @FXML
    private void setCloseButton(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


}
