package database;

public class HelpUsers {

    private Integer id = 0;
    private String userType = null;
    private String name = null;
    private String surname = null;
    private String userLogin = null;
    private String userPassword = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
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

    public HelpUsers(){}

    public HelpUsers(Integer id, String userType, String name, String surname, String userLogin, String userPassword){
        this.id = id;
        this.userType = userType;
        this.name = name;
        this.surname = surname;
        this.userLogin = userLogin;
        this.userPassword = userPassword;
    }




}
