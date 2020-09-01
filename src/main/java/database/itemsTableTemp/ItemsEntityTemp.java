package database.itemsTableTemp;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "items_temp")
public class ItemsEntityTemp implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Row_ID")
    private Integer rowId;

    @Column(name = "Item_ID")
    private String itemId;

    @Column(name = "Item_Name")
    private String itemName;

    @Column(name = "Invoice_Number")
    private String invoiceNumber;

    @Column(name = "Location_ID")
    private Integer locationID;

    @Column(name = "Group_ID")
    private Integer groupId;

    @Column(name = "NetValue")
    private Float netValue;

    @Column(name = "GrossValue")
    private Float grossValue;

    @Column(name = "Room_ID")
    private Integer roomId;

    @Lob
    @Column(name = "Item_Image")
    private byte[] itemImage;

    @Lob
    @Column(name = "Qr_Code")
    private byte[] qrCode;

    public ItemsEntityTemp(){}

    public ItemsEntityTemp(String itemId, String itemName, String invoiceNumber, int locationID, Integer groupId, float netValue, float grossValue, int roomId, byte[] itemImage, byte[] qrCode) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.invoiceNumber = invoiceNumber;
        this.locationID = locationID;
        this.groupId = groupId;
        this.netValue = netValue;
        this.grossValue = grossValue;
        this.roomId = roomId;
        this.itemImage = itemImage;
        this.qrCode = qrCode;
    }
    public ItemsEntityTemp(String itemId, String itemName, String invoiceNumber, float netValue, float grossValue, byte[] itemImage, byte[] qrCode) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.invoiceNumber = invoiceNumber;
        this.netValue = netValue;
        this.grossValue = grossValue;
        this.itemImage = itemImage;
        this.qrCode = qrCode;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
        this.rowId = rowId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public Integer getLocationID() {
        return locationID;
    }

    public void setLocationID(Integer locationID) {
        this.locationID = locationID;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Float getNetValue() {
        return netValue;
    }

    public void setNetValue(Float netValue) {
        this.netValue = netValue;
    }

    public Float getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(Float grossValue) {
        this.grossValue = grossValue;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }

    public byte[] getQrCode() {
        return qrCode;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }



}
