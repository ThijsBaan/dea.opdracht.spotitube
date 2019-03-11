package nl.thijs.dea.dto;

/**
 * LoginRequestDto --- class for making an request for Login
 * @author Thijs
 */
public class LoginRequestDto {
    private String user;
    private String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}