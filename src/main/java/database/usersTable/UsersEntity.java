package database.usersTable;

import program.UserType;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


@Entity
@Table(name ="users")
public class UsersEntity implements Serializable{
    public UsersEntity(){}

    @Id
    @Column(name = "U_Id", unique = true)
    private int id;

    @Column(name = "UserLogin", unique = true)
    private String userLogin;

    @Column(name = "UserPassword")
    private  String userPassword;


    @Column(name = "User_Type",columnDefinition ="ENUM('Administrator','Ksiegowa','Zwykly_Uzytkownik',Inwentaryzator')")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(name = "Imie")
    private String name;

    @Column(name = "Nazwisko")
    private String surname;

    @Column(name ="Data_Utworzenia")
    private Date creationDate;

    @Column(name = "Data_Urodzenia")
    private Date birthDate;

    @Column(name = "Wiek")
    private int age;

    public UsersEntity(int id, String userLogin, String userPassword, UserType userType, String name, String surname, Date creationDate, Date birthDate, int age){
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
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
