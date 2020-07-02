package program;

public enum UserType {
    Administrator("Administrator"),
    Ksiegowa("Ksiegowa"),
    Zwykly_Uzytkownik("Zwykly_Uzytkownik"),
    Inwentaryzator("Inwentaryzator");

    private String userType;

    UserType(String userType) {
        this.userType = userType;
    }

    public String getUserType(){
        return this.userType;

    }

    public static UserType fromString(String typeUser){
        for(UserType u : UserType.values()){
            if(u.userType.equalsIgnoreCase(typeUser)){
                return u;
            }
        }
        return null;
    }

}
