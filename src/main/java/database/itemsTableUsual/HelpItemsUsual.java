package database.itemsTableUsual;

import com.jfoenix.controls.JFXButton;

public class HelpItemsUsual {

    private String  item_ID = null;
    private String  item_Name = null;
    private String  inv_Number = null;
    private Float net_Value = null;
    private Float gross_Value = null;
    private byte[]  itemImage = null;
    private JFXButton actionButton= null;

    public HelpItemsUsual(){}

    public HelpItemsUsual(String item_ID, String item_Name, String inv_Number, Float net_Value, Float gross_Value, JFXButton actionButton) {
        this.item_ID = item_ID;
        this.item_Name = item_Name;
        this.inv_Number = inv_Number;
        this.net_Value = net_Value;
        this.gross_Value = gross_Value;
        this.actionButton = actionButton;
    }

    public String getItem_ID() {
        return item_ID;
    }

    public void setItem_ID(String item_ID) {
        this.item_ID = item_ID;
    }

    public String getItem_Name() {
        return item_Name;
    }

    public void setItem_Name(String item_Name) {
        this.item_Name = item_Name;
    }

    public String getInv_Number() {
        return inv_Number;
    }

    public void setInv_Number(String inv_Number) {
        this.inv_Number = inv_Number;
    }

    public Float getNet_Value() {
        return net_Value;
    }

    public void setNet_Value(Float net_Value) {
        this.net_Value = net_Value;
    }

    public Float getGross_Value() {
        return gross_Value;
    }

    public void setGross_Value(Float gross_Value) {
        this.gross_Value = gross_Value;
    }

    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }

    public JFXButton getActionButton() {
        return actionButton;
    }

    public void setActionButton(JFXButton actionButton) {
        this.actionButton = actionButton;
    }
}
