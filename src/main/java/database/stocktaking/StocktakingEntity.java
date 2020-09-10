package database.stocktaking;

import database.locationsTable.LocationsEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


@Entity
@Table(name = "stocktaking")
public class StocktakingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Stock_ID")
    private Integer stockID;

    @Column(name = "User")
    private String userName;

    @Column(name = "Stock_Name")
    private String Stock_Name;

    @OneToOne
    @JoinColumn(name = "Location_ID")
    private LocationsEntity locationsEntityId;

    @Column(name = "Creation_Date")
    private Date dateCreation;

    @Column(name = "Date_Load_Inv")
    private Date dateLoadInv;

    @Column(name = "Is_Ended")
    private boolean isEnded;

    @Column(name = "Note")
    private String note;

    @Lob
    @Column(name = "Raport")
    private byte[] raport;

    public StocktakingEntity(){}

    public StocktakingEntity(String stock_Name, LocationsEntity locationsEntityId, Date dateCreation, Date dateLoadInv) {
        Stock_Name = stock_Name;
        this.locationsEntityId = locationsEntityId;
        this.dateCreation = dateCreation;
        this.dateLoadInv = dateLoadInv;
    }

    public Integer getStockID() {
        return stockID;
    }

    public void setStockID(Integer stockID) {
        this.stockID = stockID;
    }

    public byte[] getRaport() {
        return raport;
    }

    public void setRaport(byte[] raport) {
        this.raport = raport;
    }

    public Integer getRowId() {
        return stockID;
    }

    public void setRowId(Integer stockID) {
        this.stockID = stockID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStock_Name() {
        return Stock_Name;
    }

    public void setStock_Name(String stock_Name) {
        Stock_Name = stock_Name;
    }

    public LocationsEntity getLocationsEntityId() {
        return locationsEntityId;
    }

    public void setLocationsEntityId(LocationsEntity locationsEntityId) {
        this.locationsEntityId = locationsEntityId;
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

    public boolean isEnded() {
        return isEnded;
    }

    public void setEnded(boolean ended) {
        isEnded = ended;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
