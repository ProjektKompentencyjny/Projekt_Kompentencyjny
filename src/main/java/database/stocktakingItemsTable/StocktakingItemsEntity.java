package database.stocktakingItemsTable;

import database.stocktaking.StocktakingEntity;
import org.hibernate.bytecode.enhance.spi.interceptor.AbstractLazyLoadInterceptor;

import javax.persistence.*;

@Entity
@Table(name = "stocktaking_items")
public class StocktakingItemsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Row_ID")
    private Integer rowId;

    @Column(name = "Item_ID")
    private String itemId;

    @Column(name = "Item_Name")
    private String itemName;

    @Lob
    @Column(name = "Item_Image")
    private byte [] itemImage;

    @Lob
    @Column(name = "Qr_Code_Item")
    private byte [] qrCodeItem;

    @Column(name = "Room_ID")
    private int roomId;

    @Column(name = "Room_Name")
    private String roomName;

    @Column(name = "Sys_Amount")
    private Integer sysAmount;

    @Column(name = "Real_Amount")
    private Integer realAmount;

    @Column(name = "IsChecked")
    private boolean isChecked;

    @Column(name= "Note")
    private String note;

    @OneToOne
    @JoinColumn(name = "Stock_ID")
    private StocktakingEntity stockId;

    public StocktakingItemsEntity(){}

    public StocktakingItemsEntity(String itemId, String itemName, byte[] itemImage, byte[] qrCodeItem, int roomId, String roomName, Integer sysAmount, StocktakingEntity stockId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.qrCodeItem = qrCodeItem;
        this.roomId = roomId;
        this.roomName = roomName;
        this.sysAmount = sysAmount;
        this.stockId = stockId;
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

    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }

    public byte[] getQrCodeItem() {
        return qrCodeItem;
    }

    public void setQrCodeItem(byte[] qrCodeItem) {
        this.qrCodeItem = qrCodeItem;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Integer getSysAmount() {
        return sysAmount;
    }

    public void setSysAmount(Integer sysAmount) {
        this.sysAmount = sysAmount;
    }

    public Integer getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(Integer realAmount) {
        this.realAmount = realAmount;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public StocktakingEntity getStockId() {
        return stockId;
    }

    public void setStockId(StocktakingEntity stockId) {
        this.stockId = stockId;
    }
}
