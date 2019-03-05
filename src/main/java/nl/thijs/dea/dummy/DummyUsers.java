package nl.thijs.dea.dummy;

public class DummyUsers {
    private String fullName;
    private String username;
    private String password;

    public DummyUsers(String username, String password) {
        this.username = username;
        this.password = password;
        this.fullName = maakEersteLetterHoofdletter(username) + " " + maakEersteLetterHoofdletter(password);
    }

    private String maakEersteLetterHoofdletter(String name){
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
