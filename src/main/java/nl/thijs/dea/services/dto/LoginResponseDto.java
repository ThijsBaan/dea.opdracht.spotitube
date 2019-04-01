package nl.thijs.dea.services.dto;

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

        public String makeFullname(String firstname, String surname){
            return firstname.substring(0,1).toUpperCase() + firstname.substring(1).toLowerCase() +
                    " " + surname.substring(0,1).toUpperCase() + surname.substring(1).toLowerCase();
        }

}
