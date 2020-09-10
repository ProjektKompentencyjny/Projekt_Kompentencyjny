package database.itemsTable;

import database.groupsTable.GroupsEntity;
import database.invoicesTable.InvoicesEntity;
import database.locationsTable.LocationsEntity;
import database.roomTable.RoomEntity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name= "items")
public class ItemsEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Row_ID")
    private Integer rowId;

    @Column(name = "Item_ID")
    private String itemId;

    @Column(name = "Item_Name")
    private String itemName;

    @OneToOne
    @JoinColumn(name = "Invoice_Number")
    private InvoicesEntity invoiceNumber;

    @OneToOne
    @JoinColumn(name = "Location_ID",nullable = false)
    private LocationsEntity locationsEntityId;

    @OneToOne
    @JoinColumn(name = "Group_ID",nullable = false)
    private GroupsEntity groupsEntity;

    @Column(name = "NetValue")
    private Float netValue;

    @Column(name = "GrossValue")
    private Float grossValue;

    @OneToOne
    @JoinColumn(name = "Room_ID",nullable = false)
    private RoomEntity roomEntity;

    @Lob
    @Column(name = "Item_Image")
    private byte[] itemImage;

    @Lob
    @Column(name = "Qr_Code")
    private byte[] qrCode;

    @Column(name = "Amortyzation_Value")
    private Float amortyzationValue;

    public ItemsEntity(){};

    public ItemsEntity(Integer rowId, String itemId, String itemName,
                       InvoicesEntity invoiceNumber, LocationsEntity locationsEntityId,
                       GroupsEntity groupsEntity, Float netValue, Float grossValue,
                       RoomEntity roomEntity, byte[] itemImage, byte[] qrCode,
                       Float amortyzationValue) {
        this.rowId = rowId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.invoiceNumber = invoiceNumber;
        this.locationsEntityId = locationsEntityId;
        this.groupsEntity = groupsEntity;
        this.netValue = netValue;
        this.grossValue = grossValue;
        this.roomEntity = roomEntity;
        this.itemImage = itemImage;
        this.qrCode = qrCode;
        this.amortyzationValue = amortyzationValue;
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

    public InvoicesEntity getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(InvoicesEntity invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocationsEntity getLocationsEntityId() {
        return locationsEntityId;
    }

    public void setLocationsEntityId(LocationsEntity locationsEntityId) {
        this.locationsEntityId = locationsEntityId;
    }

    public GroupsEntity getGroupsEntity() {
        return groupsEntity;
    }

    public void setGroupsEntity(GroupsEntity groupsEntity) {
        this.groupsEntity = groupsEntity;
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

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
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

    public Float getAmortyzationValue() {
        return amortyzationValue;
    }

    public void setAmortyzationValue(Float amortyzationValue) {
        this.amortyzationValue = amortyzationValue;
    }

}
