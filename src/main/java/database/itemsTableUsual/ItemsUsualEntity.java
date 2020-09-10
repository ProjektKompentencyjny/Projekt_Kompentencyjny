package database.itemsTableUsual;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name= "items_usual_user")
public class ItemsUsualEntity implements Serializable {

    @Id
    @Column(name = "Item_ID")
    private String id;

    @Column(name = "Item_Name")
    private String itemName;

    @Column(name = "Invoice_Number")
    private String invoiceNumber;

    @Column(name = "NetValue")
    private float netValue;

    @Column(name = "GrossValue")
    private float grossValue;

    @Lob
    @Column(name = "Item_Image")
    private byte[] itemImage;

    public ItemsUsualEntity(){}

    public ItemsUsualEntity(String id, String itemName, String invoiceNumber, float netValue, float grossValue, byte[] itemImage) {
        this.id = id;
        this.itemName = itemName;
        this.invoiceNumber = invoiceNumber;
        this.netValue = netValue;
        this.grossValue = grossValue;
        this.itemImage = itemImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public float getNetValue() {
        return netValue;
    }

    public void setNetValue(float netValue) {
        this.netValue = netValue;
    }

    public float getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(float grossValue) {
        this.grossValue = grossValue;
    }

    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }







}
