package nl.thijs.dea.services.models;

import java.util.Random;

public class TokenModel {
    private static final int TOKENLENGTH = 15;
    private static final String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    /**
     * Generates a string with the length of "tokenLength"
     * and containts the SALTCHARS-characters.
     *
     * @return random generated string with specific length
     */
    public String randomTokenGenerator() {
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();

        while (salt.length() < TOKENLENGTH) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }

        return salt.toString();
    }
}
