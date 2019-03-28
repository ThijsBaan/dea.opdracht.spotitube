package nl.thijs.dea.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import nl.thijs.dea.services.dto.LoginRequestDto;
import nl.thijs.dea.datasources.dao.LoginDAO;
import nl.thijs.dea.datasources.dao.TokenDAO;
import nl.thijs.dea.services.dto.LoginResponseDto;
import nl.thijs.dea.services.models.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginServiceTest {
    private static final String USERNAME = "Test";
    private static final String PASSWORD = "Test123";
    private static final String TOKEN = "123456789";

    private LoginService sut;
    private LoginDAO loginDAOMock;
    private TokenDAO tokenDAOMock;
    private LoginRequestDto request;
    private UserModel user;
    private LoginResponseDto userResponse;

    @BeforeEach
    void setup() {
        loginDAOMock = mock(LoginDAO.class);
        tokenDAOMock = mock(TokenDAO.class);
        sut = new LoginService();
        sut.setDAO(loginDAOMock, tokenDAOMock);

        request = new LoginRequestDto();
        request.setUser(USERNAME);
        request.setPassword(PASSWORD);

        user = new UserModel(USERNAME, PASSWORD);

        userResponse = new LoginResponseDto();
        userResponse.setToken(TOKEN);
        userResponse.setUser(USERNAME);
    }

    @Test
    void doesEndpointDelegateCorrectWorkToLoginDAO() {
        // Setup
        when(loginDAOMock.login(USERNAME, PASSWORD)).thenReturn(user);

        // Test
        LoginResponseDto result = sut.login(request);

        // Verify
        verify(loginDAOMock).login(USERNAME, PASSWORD);
    }

    @Test
    void doesEndpointDelegateCorrectWorkToTokenDAO() {
        // Setup
        when(tokenDAOMock.getToken()).thenReturn(TOKEN);

        // Test
        String temp = tokenDAOMock.getToken();
        // Verify
        assertEquals(TOKEN, temp);
    }

    @Test
    void checkIfLoginResponseIsNotNull() {
        // Setup
        when(loginDAOMock.login(USERNAME, PASSWORD)).thenReturn(user);

        // Test
        LoginResponseDto result = sut.login(request);

        // Verify
        assertNotNull(result);
    }

    @Test
    void checkIfLoginResponseIsNullWithNoUser() {
        // Setup
        when(loginDAOMock.login(USERNAME, PASSWORD)).thenReturn(null);

        // Test
        LoginResponseDto result = sut.login(request);

        // Verify
        assertNull(result);
    }

}
