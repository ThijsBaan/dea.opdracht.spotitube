package nl.thijs.dea.services.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginResponseDtoTest {

    @Test
    void checkIfMakeFullNameWork(){
        // setup
        var user = new LoginResponseDto();
        String voornaam = "Thijs";
        String achternaam = "Baan";

        // test
        String fullname = user.makeFullname(voornaam, achternaam);

        // verify
        assertEquals("Thijs Baan", fullname);
    }
}
