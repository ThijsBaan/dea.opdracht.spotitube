package nl.thijs.dea.controllers.dto;

/**
 * LoginRequestDto --- class for making an request for LoginController
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
