package nl.thijs.dea.models;

public class UserModel {
    private String fullName;
    private String username;
    private String password;

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
        this.fullName = maakEersteLetterHoofdletter(username) + " " + maakEersteLetterHoofdletter(password);
    }

    private String maakEersteLetterHoofdletter(String name){
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
