package database.usersTable;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import program.UserType;

import java.util.Date;

public class HelpUsers {

    private Integer id = 0;
    private UserType userType = null;
    private String name = null;
    private String surname = null;
    private String userLogin = null;
    private String userPassword = null;
    private Date creationDate = null;
    private Date birthDate = null;
    private Integer age = null;
    private JFXButton actionButton= null;

    public JFXButton getActionButton() {
        return actionButton;
    }

    public void setActionButton(JFXButton actionButton) {
        this.actionButton = actionButton;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public HelpUsers(){}


    public HelpUsers(Integer id, UserType userType, String name, String surname, String userLogin, String userPassword,Date creationDate,
                     Date birthDate,
                     Integer age,
                     JFXButton actionButton){
        this.id = id;
        this.userType = userType;
        this.name = name;
        this.surname = surname;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
        this.creationDate = creationDate;
        this.birthDate = birthDate;
        this.age= age;
        this.actionButton = actionButton;

    }




}
