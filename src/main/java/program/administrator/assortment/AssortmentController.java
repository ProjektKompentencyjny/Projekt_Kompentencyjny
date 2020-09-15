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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import program.ImageFx;
import program.Qr;
import program.accountant.MainWindowController;
import program.accountant.ManageFinalItemWindowController;
import program.accountant.ManageItemGroupWindowController;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Stack;

public class AssortmentController implements Initializable {

    public static int flag;
    @FXML
    StackPane assortmentStackPane;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    @FXML
    TableView<ItemsEntityHelp> tableViewAssortment;
    List<ItemsEntity> itemsEntities = Items.getAllFromItems();
    List<ItemsEntityHelp> helpItemsList =  new LinkedList<ItemsEntityHelp>();


    @FXML
    JFXButton printButton;

    int checkBoxFlag =0;

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

        List<JFXButton> jfxButtonsManage = new LinkedList<JFXButton>();
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
            jfxButtonsManage.add(helpItemsList.get(i).getManageButton());

            printButton.setOnAction(actionEvent -> {
                int counter =0;
                for(CheckBox x :jfxCheckBoxes){
                    if(x.isSelected()){
                        checkBoxFlag=1;
                        counter++;
                    }
                }

                if(checkBoxFlag ==1) {

                    FileChooser fileChooser = new FileChooser();
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*pdf"));
                    File file = fileChooser.showSaveDialog(null);
                    OutputStream out = null;

                    if (file != null) {
                        try {
                            out = new FileOutputStream(file.getAbsolutePath() + ".pdf");
                        } catch (Exception e) {
                            System.err.println("Error: " + e.getMessage());
                        }
                        PdfPTable table;
                    if(counter%2==0) {
                         table = new PdfPTable(4);
                    }else {
                        table = new PdfPTable(2);
                    }

                    for (int j = 0; j < jfxCheckBoxes.size(); j++) {
                        if (jfxCheckBoxes.get(j).isSelected()) {
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
                        PdfWriter.getInstance(document, out);
                        document.open();
                        document.add(table);
                        document.close();

                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.setTitle("Informacja");
                        alert.setContentText("Stworzono pomyślnie plik z kodami QR");
                        alert.setHeaderText("Sukces!");
                        alert.showAndWait();

                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }


                    try {
                        out.close();
                    } catch (IOException exception) {
                        exception.printStackTrace();
                    }
                }
                }else {
                    alert.setAlertType(Alert.AlertType.WARNING);
                    alert.setTitle("Błąd");
                    alert.setContentText("Nie wybrano żadnego przedmiotu");
                    alert.setHeaderText("Błąd");
                    alert.showAndWait();
                }
            });

            int finalI = i;
            jfxButtonsManage.get(i).setOnAction(actionEvent -> {

                    if (flag == 0 || flag == 2) {
                        try {
                                FXMLLoader fxmlLoader = new FXMLLoader();
                                fxmlLoader.setLocation(getClass().getResource("ManageAssortmentWindow.fxml"));
                                Scene scene = new Scene(fxmlLoader.load());

                                ManageAssortmentWindowController manageAssortmentWindowController = fxmlLoader.getController();
                                manageAssortmentWindowController.initImageView((ImageFx.convertToJavaFXImage(itemsEntities.get(finalI).getItemImage(), 300, 300)),
                                        ImageFx.convertToJavaFXImage(itemsEntities.get(finalI).getQrCode(), 200, 200));
                                manageAssortmentWindowController.initData(itemsEntities.get(finalI), assortmentStackPane);
                                switch (flag) {
                                    case 0:
                                        ManageAssortmentWindowController.flagUser = 0;
                                        break;
                                    case 2:
                                        ManageAssortmentWindowController.flagUser = 2;
                                        break;
                                }

                                Stage stage = new Stage();
                                stage.setTitle("Zarządzanie");
                                stage.setScene(scene);
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.initStyle(StageStyle.UNDECORATED);
                                stage.show();

                            }catch(IOException e){
                                e.printStackTrace();
                            }
                    }else if(flag==1) {

                        try {

                            FXMLLoader fxmlLoader = new FXMLLoader();
                            fxmlLoader.setLocation((ManageFinalItemWindowController.class.getResource("ManageFinalItemWindow.fxml")));
                            Scene scene = new Scene(fxmlLoader.load());

                            ManageFinalItemWindowController manageFinalItemWindowController = fxmlLoader.getController();
                            manageFinalItemWindowController.initImageView((ImageFx.convertToJavaFXImage(itemsEntities.get(finalI).getItemImage(), 300, 300)),
                                    ImageFx.convertToJavaFXImage(itemsEntities.get(finalI).getQrCode(), 200, 200));
                            manageFinalItemWindowController.initData(itemsEntities.get(finalI), assortmentStackPane);

                            Stage stage = new Stage();
                            stage.setTitle("Zarządzanie");
                            stage.setScene(scene);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.initStyle(StageStyle.UNDECORATED);
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();

                        }
                    }


            });

            
        }


    }


}

