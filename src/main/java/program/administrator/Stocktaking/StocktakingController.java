package program.administrator.Stocktaking;

import com.jfoenix.controls.JFXButton;
import database.itemsTableTemp.HelpItemsTemp;
import database.stocktaking.Stocktaking;
import database.stocktaking.StocktakingEntityHelp;
import database.stocktaking.StocktakingEntity;
import database.stocktakingItemsTable.StocktakingItems;
import database.stocktakingItemsTable.StocktakingItemsEntity;
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
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class StocktakingController implements Initializable {

    @FXML
    StackPane stackPaneStocktaking;

    @FXML
    JFXButton addButton;

    @FXML
    TableView tableViewStocktaking;

    List<StocktakingEntity> stocktakingEntities = Stocktaking.getAllFromStocktaking();
    List<StocktakingEntityHelp> stocktakingEntityHelps  = new LinkedList<>();


    String status ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TableColumn<StocktakingEntityHelp, Date> column1 = new TableColumn <>("Data utworzenia");
        column1.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));

        TableColumn<StocktakingEntityHelp,Date> column2 = new TableColumn <>("Data wczyt. danych magazynowych");
        column2.setCellValueFactory(new PropertyValueFactory<>("dateLoadInv"));

        TableColumn<StocktakingEntityHelp,String> column3 = new TableColumn <>("Status");
        column3.setCellValueFactory(new PropertyValueFactory<>("status"));

        TableColumn<StocktakingEntityHelp,String> column4 = new TableColumn <>("Lokalizacja");
        column4.setCellValueFactory(new PropertyValueFactory<>("locName"));

        TableColumn<StocktakingEntityHelp,String> column5 = new TableColumn <>("Nazwa inwentaryzacji");
        column5.setCellValueFactory(new PropertyValueFactory<>("stockName"));

        TableColumn<StocktakingEntityHelp,String> column6 = new TableColumn <>("Nazwa użytkownika");
        column6.setCellValueFactory(new PropertyValueFactory<>("userName"));

        TableColumn<StocktakingEntityHelp,JFXButton> column7 = new TableColumn <>("Raport");
        column7.setCellValueFactory(new PropertyValueFactory<>("reportButton"));

        tableViewStocktaking.getColumns().addAll(column1,column2,column3,column4,column5,column6,column7);
        List<JFXButton> jfxButtons = new LinkedList<JFXButton>();
        for(int i=0;i<stocktakingEntities.size();i++){

            if(stocktakingEntities.get(i).isEnded())
                status = "Zakończony";
            else
                status = "W trakcie";

            tableViewStocktaking.getItems().addAll(new StocktakingEntityHelp(stocktakingEntities.get(i).getDateCreation(),
                    stocktakingEntities.get(i).getDateLoadInv(),
                    status,
                    stocktakingEntities.get(i).getLocationsEntityId().getNameLocation(),
                    stocktakingEntities.get(i).getStock_Name(),
                    stocktakingEntities.get(i).getUserName(),
                    new JFXButton("PDF")
                    ));

            if(!stocktakingEntities.get(i).isEnded())
                ((StocktakingEntityHelp) tableViewStocktaking.getItems().get(i)).getReportButton().setVisible(false);

            stocktakingEntityHelps.add((StocktakingEntityHelp) tableViewStocktaking.getItems().get(i));
            jfxButtons.add(stocktakingEntityHelps.get(i).getReportButton());



            jfxButtons.get(i).setOnAction(actionEvent -> {
                System.out.println("test");
            });

        }

        List<StocktakingEntity> stocktakingEntities = Stocktaking.getAllFromStocktaking();

        for(StocktakingEntity x : stocktakingEntities){
            List<StocktakingItemsEntity> list = StocktakingItems.getAllByRoomId(x);
            if(x.isEnded() && x.getRaport().equals(null)){
                // tworzenie raportu

                for(StocktakingItemsEntity y :list){
                    // wszystkie itemy z tej inwentaryzacji

                }
                // wrzucenie do bazy danych jako blob
            }
        }

    }

    @FXML
    private void setAddButton(){

        try{
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AddStockWindow.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            AddStockWindowController addStockWindowController = fxmlLoader.getController();
            addStockWindowController.setStackPane(stackPaneStocktaking);

            Stage stage = new Stage();
            stage.setTitle("Pomieszczenie");
            stage.setScene(scene);
            stage.show();


        }catch (IOException e){
            e.printStackTrace();
        }
    }



    }



