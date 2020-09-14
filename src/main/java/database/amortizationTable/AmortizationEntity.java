package database.amortizationTable;

import database.groupsTable.GroupsEntity;
import database.itemsTable.ItemsEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "amortization")
public class AmortizationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Row_ID")
    private Integer rowId;

    @OneToOne
    @JoinColumn(name = "Item_ID")
    private ItemsEntity itemID;

    @Column(name = "Date_Amortization")
    private Date date_Amortization;

    @Column(name = "Kwota_Odpisow")
    private Double kwotaOdpisow;

    @Column(name = "Kwota_Odpisów_Narastajaco")
    private Double kwotaOdpisowNarastajaco ;

    @Column(name = "Kwota_Pozostala")
    private Double kwotaPozostala;

    public AmortizationEntity(){}

    public AmortizationEntity(ItemsEntity itemID, Date date_Amortization, Double kwotaOdpisow, Double kwotaOdpisowNarastajaco, Double kwotaPozostala) {
        this.itemID = itemID;
        this.date_Amortization = date_Amortization;
        this.kwotaOdpisow = kwotaOdpisow;
        this.kwotaOdpisowNarastajaco = kwotaOdpisowNarastajaco;
        this.kwotaPozostala = kwotaPozostala;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public ItemsEntity getItemID() {
        return itemID;
    }

    public void setItemID(ItemsEntity itemID) {
        this.itemID = itemID;
    }

    public Date getDate_Amortization() {
        return date_Amortization;
    }

    public void setDate_Amortization(Date date_Amortization) {
        this.date_Amortization = date_Amortization;
    }

    public Double getKwotaOdpisow() {
        return kwotaOdpisow;
    }

    public void setKwotaOdpisow(Double kwotaOdpisow) {
        this.kwotaOdpisow = kwotaOdpisow;
    }

    public Double getKwotaOdpisowNarastajaco() {
        return kwotaOdpisowNarastajaco;
    }

    public void setKwotaOdpisowNarastajaco(Double kwotaOdpisowNarastajaco) {
        this.kwotaOdpisowNarastajaco = kwotaOdpisowNarastajaco;
    }

    public Double getKwotaPozostala() {
        return kwotaPozostala;
    }

    public void setKwotaPozostala(Double kwotaPozostala) {
        this.kwotaPozostala = kwotaPozostala;
    }
}
