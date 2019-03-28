package nl.thijs.dea.controllers;

import nl.thijs.dea.services.LoginService;
import nl.thijs.dea.services.dto.LoginRequestDto;
import nl.thijs.dea.services.dto.LoginResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class LoginControllerTest {
    private static final String USERNAME = "Test";
    private static final String PASSWORD = "Test123";
    private static final String TOKEN = "123456789";

    private LoginController sut;
    private LoginRequestDto request;
    private LoginService loginServiceMock;
    private LoginResponseDto userResponse;

    @BeforeEach
    void setup() {
        sut = new LoginController();
        loginServiceMock = mock(LoginService.class);
        sut.setService(loginServiceMock);

        request = new LoginRequestDto();
        request.setUser(USERNAME);
        request.setPassword(PASSWORD);

        userResponse = new LoginResponseDto();
        userResponse.setToken(TOKEN);
        userResponse.setUser(USERNAME);
    }

    @Test
    void doesEndpointDelegateCorrectWorkToDAO() {
        // Setup
        when(loginServiceMock.login(request)).thenReturn(userResponse);

        // Test
        Response result = sut.login(request);

        // Verify
        verify(loginServiceMock).login(request);
    }

    @Test
    void checkIfRespondLevelIs200() {
        // Setup
        when(loginServiceMock.login(request)).thenReturn(userResponse);

        // Test
        Response result = sut.login(request);

        // Verify
        assertEquals(200, result.getStatus());
        assertTrue(result.hasEntity());
    }

    @Test
    void checkIfLoginWentWrongAndResponseStatus401IsGiven() {
        // Setup
        when(loginServiceMock.login(request)).thenReturn(null);

        // Test
        Response result = sut.login(request);

        // Verify
        assertEquals(401, result.getStatus());
    }

}
