package nl.thijs.dea.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import nl.thijs.dea.controllers.dto.LoginRequestDto;
import nl.thijs.dea.datasources.dao.LoginDAO;
import nl.thijs.dea.datasources.dao.TokenDAO;
import nl.thijs.dea.models.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

class LoginControllerTest {
    private static final String USERNAME = "Test";
    private static final String PASSWORD = "Test123";

    private LoginController sut;
    private LoginDAO loginDAOMock;
    private TokenDAO tokenDAOMock;
    private LoginRequestDto request;
    private UserModel user;

    @BeforeEach
    void setup() {
        loginDAOMock = mock(LoginDAO.class);
        tokenDAOMock = mock(TokenDAO.class);
        sut = new LoginController();
        sut.setDAO(loginDAOMock, tokenDAOMock);

        request = new LoginRequestDto();
        request.setUser(USERNAME);
        request.setPassword(PASSWORD);

        user = new UserModel(USERNAME, PASSWORD);
    }

    @Test
    void doesEndpointDelegateCorrectWorkToDAO() {
        // Setup
        when(loginDAOMock.login(USERNAME, PASSWORD)).thenReturn(user);

        // Test
        Response result = sut.login(request);

        // Verify
        verify(loginDAOMock).login(USERNAME, PASSWORD);
    }

    @Test
    void checkIfRespondLevelIs200() {
        // Setup
        when(loginDAOMock.login(USERNAME, PASSWORD)).thenReturn(user);

        // Test
        Response result = sut.login(request);

        // Verify
        assertEquals(200, result.getStatus());
        assertTrue(result.hasEntity());
    }

    @Test
    void checkIfLoginWentWrongAndResponseStatus401IsGiven() {
        // Setup
        when(loginDAOMock.login(USERNAME, PASSWORD)).thenReturn(null);

        // Test
        Response result = sut.login(request);

        // Verify
        assertEquals(401, result.getStatus());
    }

}
