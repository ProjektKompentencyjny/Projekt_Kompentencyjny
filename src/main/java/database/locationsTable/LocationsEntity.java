package database.locationsTable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;

@Entity
@Table(name= "locations")
public class LocationsEntity implements Serializable {

    @Id
    @Column(name = "Location_ID")
    private Integer idLocation;

    @Column(name = "Location_Name",nullable = false)
    private String nameLocation;

    @Column(name = "Street_Addres",nullable = false)
    private String streetAddres;

    @Column(name = "Postal_Code",nullable = false)
    private String postalCode;

    @Column(name = "City",nullable = false)
    private String city;

    @Lob
    @Column(name = "Location_Image",nullable = false)
    private byte[] locImage;

    @Lob
    @Column(name = "QrCode")
    private byte[] qrCode;


    public LocationsEntity(){}

    public LocationsEntity(Integer idLocation, String nameLocation, String streetAddres, String postalCode, String city, byte[] qrCode, byte[] locImage) {
        this.idLocation = idLocation;
        this.nameLocation = nameLocation;
        this.streetAddres = streetAddres;
        this.postalCode = postalCode;
        this.city = city;
        this.locImage = locImage;
        this.qrCode = qrCode;
    }
    public LocationsEntity(String nameLocation, String streetAddres, String postalCode, String city, byte[] locImage) {
        this.nameLocation = nameLocation;
        this.streetAddres = streetAddres;
        this.postalCode = postalCode;
        this.city = city;
        this.locImage = locImage;
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

    public byte[] getQrCode() {
        return qrCode;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }

    public byte[] getLocImage() {
        return locImage;
    }

    public void setLocImage(byte[] locImage) {
        this.locImage = locImage;
    }

    @Override
    public String toString() {
        return idLocation + " " + nameLocation  ;
    }
}
