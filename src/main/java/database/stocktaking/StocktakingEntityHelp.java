package database.stocktaking;

import com.jfoenix.controls.JFXButton;

import java.sql.Date;

public class StocktakingEntityHelp {


    private Date dateCreation   = null;
    private Date dateLoadInv    = null;
    private String status       = null;;
    private String locName      = null;;
    private String stockName    = null;;
    private String userName     = null;;

    private JFXButton reportButton= null;


    public StocktakingEntityHelp(Date dateCreation, Date dateLoadInv,
                                 String status, String locName,
                                 String stockName, String userName,
                                 JFXButton reportButton) {
        this.dateCreation = dateCreation;
        this.dateLoadInv = dateLoadInv;
        this.status = status;
        this.locName = locName;
        this.stockName = stockName;
        this.userName = userName;
        this.reportButton = reportButton;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateLoadInv() {
        return dateLoadInv;
    }

    public void setDateLoadInv(Date dateLoadInv) {
        this.dateLoadInv = dateLoadInv;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public JFXButton getReportButton() {
        return reportButton;
    }

    public void setReportButton(JFXButton reportButton) {
        this.reportButton = reportButton;
    }

}
