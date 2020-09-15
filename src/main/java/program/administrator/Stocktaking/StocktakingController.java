package program.administrator.Stocktaking;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import program.administrator.MainWindowController;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
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


            int finalI = i;
            jfxButtons.get(i).setOnAction(actionEvent -> {
                MainWindowController.byteArrayToFile(Stocktaking.getPdf(stocktakingEntities.get(finalI).getStockID()));
            });

        }

        List<StocktakingEntity> stocktakingEntities = Stocktaking.getAllFromStocktaking();

        for(StocktakingEntity x : stocktakingEntities){
            List<StocktakingItemsEntity> list = StocktakingItems.getAllByRoomId(x);
            if(x.isEnded() && x.getRaport()==null){


                //Generowanie PDF!!!
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    Document document = new Document();

                    PdfWriter.getInstance(document,baos);

                    document.open();

                    Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
                            Font.BOLD);
                    Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                            Font.NORMAL, BaseColor.RED);
                    Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
                            Font.BOLD);
                    Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
                            Font.BOLD);

                    //META DATA
                    document.addTitle("Raport Inwentaryzacyjny");


                    Paragraph preface = new Paragraph();
                    // We add one empty line
                    addEmptyLine(preface, 1);
                    // Lets write a big header
                    preface.add(new Paragraph("Raport Inwentaryzacji"+" "+x.getStock_Name(), catFont));

                    addEmptyLine(preface, 1);
                    // Will create: Report generated by: _name, _date
                    preface.add(new Paragraph(
                            "Inwentaryzacja przeprowadzona przez " + x.getUserName() + ", dnia " +  x.getDateLoadInv(),
                            smallBold));
                    addEmptyLine(preface, 3);
                    preface.add(new Paragraph("Lokalizacja inwentaryzacji: "+ x.getLocationsEntityId().getNameLocation()));
                    addEmptyLine(preface, 1);
                    preface.add(new Paragraph("Adres: "+ x.getLocationsEntityId().getStreetAddres()+", "+ x.getLocationsEntityId().getPostalCode()+", "+  x.getLocationsEntityId().getCity()));
                    addEmptyLine(preface, 3);

                    document.add(preface);


                    //CONTENT

                    PdfPTable table = new PdfPTable(6);


                    PdfPCell c1 = new PdfPCell(new Phrase("ID przedmiotu"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase("Nazwa przedmiotu"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase("Nazwa pomieszczenia"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase("Ilosc systemowa"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase("Ilosc rzeczywista"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);

                    c1 = new PdfPCell(new Phrase("Notatka"));
                    c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(c1);

                    table.setHeaderRows(1);

                    for(StocktakingItemsEntity y :list){
                        table.addCell(y.getItemId());
                        table.addCell(y.getItemName());
                        table.addCell(y.getRoomName());
                        table.addCell(Integer.toString(y.getSysAmount()));
                        table.addCell(Integer.toString(y.getRealAmount()));
                        table.addCell(y.getNote());
                    }
                    document.add(table);
                    document.close();
                    byte[] pdf = baos.toByteArray();
                    Stocktaking.updateReport(x.getStockID(),pdf);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                // wrzucenie do bazy danych jako blob
            }
        }

    }

    private  void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
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



