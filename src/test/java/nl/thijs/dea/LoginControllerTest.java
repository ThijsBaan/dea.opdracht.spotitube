package nl.thijs.dea;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import nl.thijs.dea.controllers.LoginController;
import nl.thijs.dea.controllers.dto.LoginRequestDto;
import nl.thijs.dea.datasources.doa.LoginDAO;
import nl.thijs.dea.models.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

class LoginControllerTest {
    private static final String USERNAME = "Test";
    private static final String PASSWORD = "Test123";

    private LoginController sut;
    private LoginDAO loginDAOMock;

    @BeforeEach
    void setup(){
        loginDAOMock = mock(LoginDAO.class);
        sut = new LoginController();
        sut.setLoginDAO(loginDAOMock);
    }

    @Test
    void doesEndpointDelegateCorrectWorkToDAO() {
        // Setup
        var request = new LoginRequestDto();
        request.setUser(USERNAME);
        request.setPassword(PASSWORD);

        var user = new UserModel(USERNAME, PASSWORD);
        when(loginDAOMock.login(USERNAME, PASSWORD)).thenReturn(user);

        // Test
        Response result = sut.login(request);

        // Verify
        verify(loginDAOMock).login(USERNAME, PASSWORD);
        //assertEquals(200, result.getStatus());
    }

    @Test
    void checkIfRespondLevelIs200() {
        // Setup
        var request = new LoginRequestDto();
        request.setUser(USERNAME);
        request.setPassword(PASSWORD);

        var user = new UserModel(USERNAME, PASSWORD);
        when(loginDAOMock.login(USERNAME, PASSWORD)).thenReturn(user);

        // Test
        Response result = sut.login(request);

        // Verify
        assertEquals(200, result.getStatus());
    }
}
