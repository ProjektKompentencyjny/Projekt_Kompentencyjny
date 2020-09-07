package database.roomTable;

import com.jfoenix.controls.JFXButton;

public class RoomEntityHelp {

    private Integer idRoom;
    private String roomName;
    private Integer locId;
    private JFXButton actionButton= null;

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

    public Integer getLocId() {
        return locId;
    }

    public void setLocId(Integer locId) {
        this.locId = locId;
    }

    public JFXButton getActionButton() {
        return actionButton;
    }

    public void setActionButton(JFXButton actionButton) {
        this.actionButton = actionButton;
    }

    public RoomEntityHelp(Integer idRoom, String roomName, Integer locId, JFXButton actionButton) {
        this.idRoom = idRoom;
        this.roomName = roomName;
        this.locId = locId;
        this.actionButton = actionButton;
    }
}
