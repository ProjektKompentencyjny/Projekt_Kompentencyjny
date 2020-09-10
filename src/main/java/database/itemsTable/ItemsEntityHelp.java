package database.itemsTable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;


public class ItemsEntityHelp {

    private String itemId;
    private String itemName;
    private String invoiceNumber;
    private Integer locID;
    private Integer roomID;
    private Float netValue;
    private Float grossValue;
    private Integer groupID;
    private Float amortyzationValue;
    private JFXButton manageButton;
    private JFXCheckBox printCheckBox;

    public ItemsEntityHelp(String itemId, String itemName, String invoiceNumber,
                           Integer locID, Integer groupID, Float netValue,
                           Float grossValue, Integer roomID, Float amortyzationValue,
                           JFXButton manageButton, JFXCheckBox printCheckBox) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.invoiceNumber = invoiceNumber;
        this.locID = locID;
        this.groupID = groupID;
        this.netValue = netValue;
        this.grossValue = grossValue;
        this.roomID = roomID;
        this.amortyzationValue = amortyzationValue;
        this.manageButton = manageButton;
        this.printCheckBox = printCheckBox;
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

    public Integer getLocID() {
        return locID;
    }

    public void setLocID(Integer locID) {
        this.locID = locID;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
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

    public Integer getRoomID() {
        return roomID;
    }

    public void setRoomID(Integer roomID) {
        this.roomID = roomID;
    }

    public Float getAmortyzationValue() {
        return amortyzationValue;
    }

    public void setAmortyzationValue(Float amortyzationValue) {
        this.amortyzationValue = amortyzationValue;
    }

    public JFXButton getManageButton() {
        return manageButton;
    }

    public void setManageButton(JFXButton manageButton) {
        this.manageButton = manageButton;
    }

    public JFXCheckBox getPrintCheckBox() {
        return printCheckBox;
    }

    public void setPrintCheckBox(JFXCheckBox printCheckBox) {
        this.printCheckBox = printCheckBox;
    }
}
