package database;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name ="users")
public class Helpusers implements Serializable{
    @Id
    @Column(name = "U_Id", unique = true)
    private int id;

    @Column(name = "UserLogin", unique = true)
    private String userLogin;

    @Column(name = "UserPassword")
    private  String userPassword;


    @Column(name = "User_Type")
    private String userType;



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
