package nl.thijs.dea.dto;

/**
 * LoginResponseDto --- class for making an response to LoginController
 * @author Thijs
 */
public class LoginResponseDto {

    private String token;
    private String user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
