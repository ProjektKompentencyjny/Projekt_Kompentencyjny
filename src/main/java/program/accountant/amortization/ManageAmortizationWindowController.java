package program.accountant.amortization;

import com.jfoenix.controls.JFXButton;
import database.amortizationTable.Amortization;
import database.amortizationTable.AmortizationEntity;
import database.itemsTable.Items;
import database.itemsTable.ItemsEntity;
import database.itemsTableTemp.HelpItemsTemp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;

import javafx.stage.FileChooser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ManageAmortizationWindowController implements Initializable {

    @FXML
    StackPane manageAmortizationStackPane;

    @FXML
    TableView<AmortizationEntity> tableViewAmortization;

    @FXML
    JFXButton generateReportButton,deleteButton;

    @FXML
    Label itemNameLabel;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

    List<AmortizationEntity> amortizationEntityList ;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void initData(ItemsEntity itemsEntity, AnchorPane pane) throws IOException {

        itemNameLabel.setText(itemsEntity.getItemName()+" Wartość początkowa: "+itemsEntity.getNetValue());
        LocalDate today = LocalDate.now();
        amortizationEntityList = Amortization.getAllFromAmortizationbyItemID(itemsEntity);

        TableColumn<AmortizationEntity, Date> column1 = new TableColumn<>("Data amortyzacji");
        column1.setCellValueFactory(new PropertyValueFactory<>("date_Amortization"));

        TableColumn<AmortizationEntity,Double> column2 = new TableColumn<>("Kwota odpisów");
        column2.setCellValueFactory(new PropertyValueFactory<>("kwotaOdpisow"));

        TableColumn<AmortizationEntity,Double> column3 = new TableColumn<>("Kwota odpisów narastająco");
        column3.setCellValueFactory(new PropertyValueFactory<>("kwotaOdpisowNarastajaco"));

        TableColumn<AmortizationEntity,Double> column4 = new TableColumn<>("Kwota Pozostała");
        column4.setCellValueFactory(new PropertyValueFactory<>("kwotaPozostala"));

        tableViewAmortization.getColumns().addAll(column1,column2,column3,column4);

        Period diff = Period.between(
                amortizationEntityList.get(amortizationEntityList.size()-1).getDate_Amortization().toLocalDate()
                , today
        );



        if(diff.getMonths()!=0 && amortizationEntityList.get(amortizationEntityList.size()-1).getKwotaPozostala()!=0.0){
            LocalDate date =  amortizationEntityList.get(amortizationEntityList.size()-1).getDate_Amortization().toLocalDate();
            Double kwotaPozostala;
            Double kwotaPozostalaTemp;

            Double kwotaNarastajacoTemp;
            Double kwotaNarastajaco;

            Double kwotaOdpisow = amortizationEntityList.get(amortizationEntityList.size()-1).getKwotaOdpisow();


            for(int i=0;i<diff.getMonths();i++){
                LocalDate oneMonthLater = date.plusMonths(i+1);

                kwotaPozostalaTemp = amortizationEntityList.get(amortizationEntityList.size()-1).getKwotaPozostala()-kwotaOdpisow;
                kwotaPozostala = Math.round(kwotaPozostalaTemp*100)/100.00;

                kwotaNarastajacoTemp = amortizationEntityList.get(amortizationEntityList.size()-1).getKwotaOdpisowNarastajaco()+kwotaOdpisow;
                kwotaNarastajaco = Math.round(kwotaNarastajacoTemp*100)/100.00;

                if(kwotaPozostala<0 || kwotaPozostala==0) {
                    double temp = amortizationEntityList.get(amortizationEntityList.size()-1).getKwotaPozostala();
                    kwotaPozostala = 0.0;
                    Amortization.insertAmortization(new AmortizationEntity(itemsEntity,
                            Date.valueOf(oneMonthLater),
                            temp,
                            kwotaNarastajaco,
                            kwotaPozostala
                    ));
                    break;

                }

                Amortization.insertAmortization(new AmortizationEntity(itemsEntity,
                        Date.valueOf(oneMonthLater),
                        kwotaOdpisow,
                        kwotaNarastajaco,
                        kwotaPozostala
                ));

                    amortizationEntityList = Amortization.getAllFromAmortizationbyItemID(itemsEntity);

            }

        }

        amortizationEntityList = Amortization.getAllFromAmortizationbyItemID(itemsEntity);

        for(int i=0;i<amortizationEntityList.size();i++){

            tableViewAmortization.getItems().addAll(amortizationEntityList.get(i));


        }
        Items.updateAmortization(itemsEntity.getRowId(),amortizationEntityList.get(amortizationEntityList.size()-1).getKwotaOdpisowNarastajaco().floatValue());


// TWORZENIE EXCELA

        generateReportButton.setOnAction(actionEvent -> {

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*xlsx"));
            File file = fileChooser.showSaveDialog(null);
            OutputStream out = null;

            if (file != null) {
                try {
                    out = new FileOutputStream(file.getAbsolutePath() + ".xlsx");
                } catch (Exception e) {
                    System.err.println("Error: " + e.getMessage());
                }

                XSSFWorkbook workbook = new XSSFWorkbook();
                CreationHelper createHelper = workbook.getCreationHelper();
                XSSFSheet sheet = workbook.createSheet("Amortyzacja");

                CellStyle dateCellStyle = workbook.createCellStyle();
                dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

                Row headerRow = sheet.createRow(7);
                String[] columns = {"Lp.", "Miesiąc", "Kwota odpisów", "Kwota pozostała"};

                Row row = sheet.createRow(0);
                row.createCell(0).setCellValue("Wyniki - amortyzacja liniowa");


                row=sheet.createRow(1);
                row.createCell(0).setCellValue("Wartość Początkowa");
                row.createCell(1).setCellValue(amortizationEntityList.get(0).getItemID().getNetValue());

                row=sheet.createRow(2);
                row.createCell(0).setCellValue("Początek Amortyzacji");
                Cell startDate = row.createCell(1);
                startDate.setCellValue(amortizationEntityList.get(0).getDate_Amortization());
                startDate.setCellStyle(dateCellStyle);

                row= sheet.createRow(3);
                row.createCell(0).setCellValue("Stawka amortyzacyjna");
                row.createCell(1).setCellValue(amortizationEntityList.get(0).getItemID().getGroupsEntity().getRateGroup().toString()+"%");

                row=sheet.createRow(4);
                row.createCell(0).setCellValue("Roczny odpis");
                row.createCell(1).setCellValue(amortizationEntityList.get(0).getKwotaOdpisow()*12);

                row = sheet.createRow(5);
                row.createCell(0).setCellValue("Miesięczny odpis");
                row.createCell(1).setCellValue(amortizationEntityList.get(0).getKwotaOdpisow());


               for (int i = 0; i < columns.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columns[i]);
                }


                int rowNum = 8;
                int lpCounter = 1;

                for (AmortizationEntity amortizationEntity : amortizationEntityList) {

                    row = sheet.createRow(rowNum++);
                    row.createCell(0)
                            .setCellValue(lpCounter++);

                    Cell date = row.createCell(1);
                    date.setCellValue(amortizationEntity.getDate_Amortization());
                    date.setCellStyle(dateCellStyle);

                    row.createCell(2)
                            .setCellValue(amortizationEntity.getKwotaOdpisow());

                    row.createCell(3)
                           .setCellValue(amortizationEntity.getKwotaPozostala());


                }

                for (int i = 0; i < columns.length; i++) {
                    sheet.autoSizeColumn(i);
                }

                // Write the output to a file
                try {
                    workbook.write(out);
                    out.close();
                    workbook.close();

                    alert.setAlertType(Alert.AlertType.INFORMATION);
                    alert.setTitle("Informacja");
                    alert.setContentText("Stworzono pomyślnie plik z kodami QR");
                    alert.setHeaderText("Sukces!");
                    alert.showAndWait();

                } catch (IOException exception) {
                    exception.printStackTrace();
                }


                // Closing the workbook


            }


        });




    }


}
