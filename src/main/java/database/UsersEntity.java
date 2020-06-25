package database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name ="users")
public class UsersEntity implements Serializable{
    public UsersEntity(){}

    public UsersEntity(String userLogin, String userPassword){

    }

    @Id
    @Column(name = "U_Id", unique = true)
    private int id;

    @Column(name = "UserLogin", unique = true)
    private String userLogin;

    @Column(name = "UserPassword")
    private  String userPassword;


    @Column(name = "User_Type")
    private String userType;



    @Column(name = "Imie")
    private String name;

    @Column(name = "Nazwisko")
    private String surname;

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
}
