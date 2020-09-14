package program.accountant.amortization;

import com.jfoenix.controls.JFXButton;
import database.amortizationTable.Amortization;
import database.amortizationTable.AmortizationEntity;
import database.itemsTable.Items;
import database.itemsTable.ItemsEntity;
import database.itemsTableTemp.HelpItemsTemp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;

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

        if(diff.getMonths()!=0){
            LocalDate date =  amortizationEntityList.get(amortizationEntityList.size()-1).getDate_Amortization().toLocalDate();
            Double kwotaPozostala;
            Double kwotaOdpisow = amortizationEntityList.get(amortizationEntityList.size()-1).getKwotaOdpisow();
            for(int i=0;i<diff.getMonths();i++){
                LocalDate oneMonthLater = date.plusMonths(i+1);

                kwotaPozostala = amortizationEntityList.get(amortizationEntityList.size()-1).getKwotaPozostala()-kwotaOdpisow;
                Amortization.insertAmortization(new AmortizationEntity(itemsEntity,
                        Date.valueOf(oneMonthLater),
                        kwotaOdpisow,
                        amortizationEntityList.get(amortizationEntityList.size()-1).getKwotaOdpisowNarastajaco()+kwotaOdpisow,
                        kwotaPozostala


                ));
                amortizationEntityList = Amortization.getAllFromAmortizationbyItemID(itemsEntity);

            }

        }


        for(int i=0;i<amortizationEntityList.size();i++){

            tableViewAmortization.getItems().addAll(amortizationEntityList.get(i));


        }
        Items.updateAmortization(itemsEntity.getRowId(),amortizationEntityList.get(amortizationEntityList.size()-1).getKwotaOdpisowNarastajaco().floatValue());


// TWORZENIE EXCELA
        XSSFWorkbook workbook = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        XSSFSheet sheet = workbook.createSheet("Amortyzacja");

        Row headerRow = sheet.createRow(0);
        String[] columns = {"Lp.", "Miesiąc", "Kwota odpisów", "Kwota pozostała"};

        for(int i=0;i<columns.length;i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }

        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        int rowNum = 1;

        for(AmortizationEntity amortizationEntity : amortizationEntityList){

            Row row = sheet.createRow(rowNum++);
            row.createCell(0)
                    .setCellValue(rowNum-1);

            Cell date = row.createCell(1);
            date.setCellValue(amortizationEntity.getDate_Amortization());
            date.setCellStyle(dateCellStyle);

            row.createCell(2)
                    .setCellValue(amortizationEntity.getKwotaOdpisow());

            row.createCell(3)
                    .setCellValue(amortizationEntity.getKwotaPozostala());


        }

        for(int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("TESTOWY EXCEL.xlsx");
        workbook.write(fileOut);
        fileOut.close();

        // Closing the workbook
        workbook.close();





    }


}
