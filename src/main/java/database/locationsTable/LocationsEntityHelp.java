package database.locationsTable;

import com.jfoenix.controls.JFXButton;

import javax.persistence.Column;
import javax.persistence.Lob;

public class LocationsEntityHelp {

    private Integer idLocation;
    private String nameLocation;
    private String streetAddres;
    private String postalCode;
    private String city;
    private JFXButton actionButton= null;

    public LocationsEntityHelp(Integer idLocation, String nameLocation, String streetAddres, String postalCode, String city, JFXButton actionButton) {
        this.idLocation = idLocation;
        this.nameLocation = nameLocation;
        this.streetAddres = streetAddres;
        this.postalCode = postalCode;
        this.city = city;
        this.actionButton = actionButton;
    }

    public Integer getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Integer idLocation) {
        this.idLocation = idLocation;
    }

    public String getNameLocation() {
        return nameLocation;
    }

    public void setNameLocation(String nameLocation) {
        this.nameLocation = nameLocation;
    }

    public String getStreetAddres() {
        return streetAddres;
    }

    public void setStreetAddres(String streetAddres) {
        this.streetAddres = streetAddres;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public JFXButton getActionButton() {
        return actionButton;
    }

    public void setActionButton(JFXButton actionButton) {
        this.actionButton = actionButton;
    }
}
