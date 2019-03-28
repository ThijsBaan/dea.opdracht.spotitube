package nl.thijs.dea.services;

import nl.thijs.dea.datasources.dao.LoginDAO;
import nl.thijs.dea.datasources.dao.TokenDAO;
import nl.thijs.dea.services.dto.LoginRequestDto;
import nl.thijs.dea.services.dto.LoginResponseDto;
import nl.thijs.dea.services.models.UserModel;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

public class LoginService {
    private LoginDAO loginDAO;
    private TokenDAO tokenDAO;

    @Inject
    public void setDAO(LoginDAO loginDAO, TokenDAO tokenDAO) {
        this.loginDAO = loginDAO;
        this.tokenDAO = tokenDAO;
    }

    /**
     * Let the user login if the user is authorized.
     *
     * @param request an LoginRequestDto object that contains
     *                an Username and Password.
     * @return response with token and username
     */
    public LoginResponseDto login(LoginRequestDto request) {
        UserModel user = loginDAO.login(request.getUser(), request.getPassword());

        if(user == null){
            return null;
        }

        LoginResponseDto loginResponse = new LoginResponseDto();
        tokenDAO.insertTokenIfUsersFirstTime(user.getUsername());

        loginResponse.setToken(tokenDAO.getToken());
        loginResponse.setUser(loginResponse.makeFullname(user.getUsername(), user.getPassword()));

        return loginResponse;
    }
}
