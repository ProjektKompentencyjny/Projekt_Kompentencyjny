package database.itemsTableTemp;

import com.jfoenix.controls.JFXButton;

public class HelpItemsTemp {

    private String itemId = null;
    private String itemName = null;
    private String invNumber = null;
    private Integer locationID = null;
    private Integer groupId = null;
    private Float netValue = null;
    private Float grossValue = null;
    private Integer roomId = null;
    private byte[] itemImage = null;
    private byte[] qrCode = null;
    private JFXButton actionButton= null;
    private JFXButton actionButton2= null;



    public HelpItemsTemp(String invNumber, JFXButton actionButton, JFXButton actionButton2) {

        this.invNumber = invNumber;
        this.actionButton = actionButton;
        this.actionButton2 = actionButton2;
    }

    public HelpItemsTemp(String itemId, String itemName, String invNumber,
                         Integer locationID, Integer groupId, Float netValue,
                         Float grossValue, Integer roomId, JFXButton actionButton) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.invNumber = invNumber;
        this.locationID = locationID;
        this.groupId = groupId;
        this.netValue = netValue;
        this.grossValue = grossValue;
        this.roomId = roomId;
        this.actionButton = actionButton;
    }

    public HelpItemsTemp(){}

    public String getInvNumber() {
        return invNumber;
    }

    public void setInvNumber(String invNumber) {
        this.invNumber = invNumber;
    }

    public JFXButton getActionButton() {
        return actionButton;
    }

    public void setActionButton(JFXButton actionButton) {
        this.actionButton = actionButton;
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

    public JFXButton getActionButton2() {
        return actionButton2;
    }

    public void setActionButton2(JFXButton actionButton2) {
        this.actionButton2 = actionButton2;
    }
}
