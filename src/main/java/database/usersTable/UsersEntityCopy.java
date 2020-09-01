package database.usersTable;

import program.UserType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.sql.Date;
import java.time.LocalDate;

public class UsersEntityCopy {

    private int id;
    private String userLogin;
    private  String userPassword;
    private String userType;
    private String name;
    private String surname;
    private Date creationDate;
    private Date birthDate;
    private int age;

    public UsersEntityCopy(int id, String userLogin, String userPassword, String userType, String name, String surname, Date creationDate, Date birthDate, int age){
        this.id = id;
        this.userLogin = userLogin;
        this.userPassword=userPassword;
        this.userType=userType;
        this.name= name;
        this.surname = surname;
        this.creationDate = creationDate;
        this.birthDate = birthDate;
        this.age = age;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
