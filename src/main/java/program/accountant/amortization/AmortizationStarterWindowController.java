package program.accountant.amortization;

import database.amortizationTable.Amortization;
import database.amortizationTable.AmortizationEntity;
import database.itemsTable.Items;
import database.itemsTable.ItemsEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess;
import program.accountant.ManageFinalItemWindowController;
import program.administrator.assortment.AssortmentController;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.ResourceBundle;

public class AmortizationStarterWindowController implements Initializable {

    @FXML
    TextField netValueTextField,percentTextField;

    @FXML
    DatePicker startDatePicker;

    @FXML
    Button closeButton,saveButton;

    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void initData(ItemsEntity itemsEntity, AnchorPane pane){
        netValueTextField.setText(itemsEntity.getNetValue().toString());
        percentTextField.setText(itemsEntity.getGroupsEntity().getRateGroup().toString());


        closeButton.setOnAction(actionEvent -> {
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        });

        saveButton.setOnAction(actionEvent -> {

            LocalDate date = startDatePicker.getValue();
            LocalDate today = LocalDate.now();


            try {
                if(today.isBefore(date)){
                    throw new IllegalArgumentException();
                }else if(startDatePicker.getValue()==null){
                    throw new NullPointerException();
                }
                Double kwotaOdpisowtemp = itemsEntity.getNetValue() * ((itemsEntity.getGroupsEntity().getRateGroup()/12)*0.01);
                Double kwotaOdpisow = Math.round(kwotaOdpisowtemp*100)/100.00;

                Double kwotaPozostalatemp = itemsEntity.getNetValue()-kwotaOdpisow;
                Double kwotaPozostala = Math.round(kwotaPozostalatemp*100)/100.00;

                Double kwotaNarastajacoTemp;
                Double kwotaNarastajaco;


                AmortizationEntity amortizationEntity = new AmortizationEntity(itemsEntity, Date.valueOf(date),kwotaOdpisow,kwotaOdpisow,kwotaPozostala);
                Amortization.insertAmortization(amortizationEntity);

                List<AmortizationEntity> amortizationEntityList = Amortization.getAllFromAmortizationbyItemID(itemsEntity);

                Period diff = Period.between(
                        date, today);

                for(int i=0;i<diff.getMonths();i++){
                    LocalDate oneMonthLater = date.plusMonths(i+1);

                    kwotaPozostalatemp = amortizationEntityList.get(i).getKwotaPozostala()-amortizationEntityList.get(i).getKwotaOdpisow();
                    kwotaPozostala = Math.round(kwotaPozostalatemp*100)/100.00;

                    kwotaNarastajacoTemp = amortizationEntityList.get(i).getKwotaOdpisowNarastajaco()+kwotaOdpisow;
                    kwotaNarastajaco = Math.round(kwotaNarastajacoTemp*100)/100.00;

                    Amortization.insertAmortization(new AmortizationEntity(itemsEntity,
                            Date.valueOf(oneMonthLater),
                            kwotaOdpisow,
                            kwotaNarastajaco,
                            kwotaPozostala
                    ));
                    amortizationEntityList = Amortization.getAllFromAmortizationbyItemID(itemsEntity);
                }
                Items.updateAmortization(itemsEntity.getRowId(),amortizationEntityList.get(amortizationEntityList.size()-1).getKwotaOdpisowNarastajaco().floatValue());

                Stage stage = (Stage) closeButton.getScene().getWindow();
                stage.close();

                Stage stage1 = (Stage) pane.getScene().getWindow();
                stage1.close();

                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Informacja");
                alert.setContentText("Pomyślnie utworzono plan amortyzacyjny ");
                alert.setHeaderText("Sukces ");
                alert.showAndWait();

            }catch (IllegalArgumentException e){
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setContentText("Nie można ustawić daty późniejszej, niż data dzisiejsza");
                alert.setHeaderText("Błąd ");
                alert.showAndWait();
            }catch (NullPointerException x){
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Błąd");
                alert.setContentText("Pole daty nie może być puste !!");
                alert.setHeaderText("Błąd ");
                alert.showAndWait();
            }

        });
    }




}
