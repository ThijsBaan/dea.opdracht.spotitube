//package nl.thijs.dea;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//class template {
//
//    public static final String USERNAME = "Test";
//    public static final String PASSWORD = "Test123";
//    LoginController sut;
//
//    LoginDAO loginDAOMock;
//
//    @BeforeEach
//    void setupTest(){
//        loginDAOMock = Mockito.mock(LoginDAO.class);
//        sut = new LoginController();
//    }
//
//    @Test
//    void doesEndpointDelegateCorrectWorkToDAO() throws SQLException {
//        // Setup
//        var request = new LoginRequest();
//        request.setUser(USERNAME);
//        request.setPassword(PASSWORD);
//
//        var userModel = new UserModel(USERNAME, PASSWORD, "TestNaam");
//        //set username
//        //set token
//        Mockito.when(loginDAOMock.login(USERNAME, PASSWORD)).thenReturn(userModel);
//
//        // Test
//        Response result = sut.loginUser(request);
//
//        // Verify
//        Mockito.verify(loginDAOMock).login(USERNAME, PASSWORD);
//        assertEquals(200, result.getStatus());
//    }
//}