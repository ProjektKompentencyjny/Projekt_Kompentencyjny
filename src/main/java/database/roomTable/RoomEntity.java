package database.roomTable;

//import database.locationsTable.LocationsEntity;

import database.locationsTable.LocationsEntity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name= "room")
public class RoomEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Room_ID")
    private Integer idRoom;

    @Column(name = "Room_Name")
    private String roomName;

    @Lob
    @Column(name = "QrCode")
    private byte[] qrCode;

    @OneToOne
    @JoinColumn(name = "Location_ID")
    private LocationsEntity locationsEntityId;

    @Lob
    @Column(name="Room_Image")
    private byte[] roomImage;

    public Integer getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(Integer idRoom) {
        this.idRoom = idRoom;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public byte[] getQrCode() {
        return qrCode;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }

    public LocationsEntity getLocationsEntityId() {
        return locationsEntityId;
    }

    public void setLocationsEntityId(LocationsEntity locationsEntityId) {
        this.locationsEntityId = locationsEntityId;
    }

    public byte[] getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(byte[] roomImage) {
        this.roomImage = roomImage;
    }

    @Override
    public String toString() {
        return idRoom + " " + roomName;

    }
}
