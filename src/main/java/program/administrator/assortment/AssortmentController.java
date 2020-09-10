package program.administrator.assortment;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DrawInterface;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import database.itemsTable.Items;
import database.itemsTable.ItemsEntity;
import database.itemsTable.ItemsEntityHelp;
import database.itemsTableTemp.HelpItemsTemp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import program.ImageFx;
import program.Qr;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class AssortmentController implements Initializable {

    @FXML
    StackPane assortmentStackPane;

    @FXML
    TableView<ItemsEntityHelp> tableViewAssortment;
    List<ItemsEntity> itemsEntities = Items.getAllFromItems();
    List<ItemsEntityHelp> helpItemsList =  new LinkedList<ItemsEntityHelp>();


    @FXML
    JFXButton printButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TableColumn<ItemsEntityHelp,String> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("itemId"));

        TableColumn<ItemsEntityHelp,String> column2 = new TableColumn<>("Nazwa przedmiotu");
        column2.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<ItemsEntityHelp,String> column3 = new TableColumn<>("Numer Faktury");
        column3.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));

        TableColumn<ItemsEntityHelp,Integer> column4 = new TableColumn<>("Id lokacji");
        column4.setCellValueFactory(new PropertyValueFactory<>("locID"));

        TableColumn<ItemsEntityHelp,Integer> column5 = new TableColumn<>("Id pokoju");
        column5.setCellValueFactory(new PropertyValueFactory<>("roomID"));

        TableColumn<ItemsEntityHelp,Float> column6 = new TableColumn<>("Wartość Netto");
        column6.setCellValueFactory(new PropertyValueFactory<>("netValue"));

        TableColumn<ItemsEntityHelp,Float> column7 = new TableColumn<>("Wartość Brutto");
        column7.setCellValueFactory(new PropertyValueFactory<>("grossValue"));

        TableColumn<ItemsEntityHelp,Integer> column8 = new TableColumn<>("Id grupy");
        column8.setCellValueFactory(new PropertyValueFactory<>("groupID"));

        TableColumn<ItemsEntityHelp,Integer> column9 = new TableColumn<>("Zamortyzowano");
        column9.setCellValueFactory(new PropertyValueFactory<>("amortyzationValue"));

        TableColumn<ItemsEntityHelp,JFXButton> column10 = new TableColumn<>("Zarządzanie");
        column10.setCellValueFactory(new PropertyValueFactory<>("manageButton"));

        TableColumn<ItemsEntityHelp, JFXCheckBox> column11 = new TableColumn<>("Drukowanie");
        column11.setCellValueFactory(new PropertyValueFactory<>("printCheckBox"));

        tableViewAssortment.getColumns().addAll(column1,column2,column3,column4,column5,column6,column7,column8,column9,column10,column11);

        List<JFXButton> jfxButtons = new LinkedList<JFXButton>();
        List<JFXCheckBox> jfxCheckBoxes = new LinkedList<JFXCheckBox>();

        for(int i=0;i<itemsEntities.size();i++){

            tableViewAssortment.getItems().addAll(new ItemsEntityHelp(itemsEntities.get(i).getItemId(),
                    itemsEntities.get(i).getItemName(),
                    itemsEntities.get(i).getInvoiceNumber().getInvoiceNumber(),
                    itemsEntities.get(i).getLocationsEntityId().getIdLocation(),
                    itemsEntities.get(i).getGroupsEntity().getGroupId(),
                    itemsEntities.get(i).getNetValue(),
                    itemsEntities.get(i).getGrossValue(),
                    itemsEntities.get(i).getRoomEntity().getIdRoom(),
                    itemsEntities.get(i).getAmortyzationValue(),
                    new JFXButton("Zarządzaj"),
                    new JFXCheckBox()
                    ));

            helpItemsList.add(tableViewAssortment.getItems().get(i));
            jfxCheckBoxes.add(helpItemsList.get(i).getPrintCheckBox());

            printButton.setOnAction(actionEvent -> {
                    com.itextpdf.text.List idList = new com.itextpdf.text.List(true, 20);
                    PdfPTable table = new PdfPTable(1);
                    for (int j = 0; j < jfxCheckBoxes.size(); j++) {
                        if (jfxCheckBoxes.get(j).isSelected()) {
                            //idList.add(new ListItem(tableViewAssortment.getItems().get(j).getItemId()));
                            try {
                                table.addCell(Image.getInstance(itemsEntities.get(j).getQrCode()));
                                table.addCell(itemsEntities.get(j).getItemId() + " " + itemsEntities.get(j).getItemName());
                            } catch (IOException | BadElementException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    try {
                        Document document = new Document();
                        PdfWriter.getInstance(document, new FileOutputStream("sample1.pdf"));
                        document.open();
                        document.add(table);
                        //Image img = Image.getInstance(itemsEntities.get(j).getQrCode());
                        //img.setAbsolutePosition(10f, 10f);
                        //img.scaleAbsolute(250, 250);
                        // document.add(img);
                        //document.add(Image.getInstance(itemsEntities.get(j).getQrCode()));
                        document.close();
                        System.out.println("done");


                    } catch (DocumentException | IOException e) {
                        e.printStackTrace();
                    }




            });



        }












    }


}
