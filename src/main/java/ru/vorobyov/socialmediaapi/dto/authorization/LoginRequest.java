package ru.vorobyov.socialmediaapi.dto.authorization;

/**
 * Login request DTO for transferring user login data.
 */
public class LoginRequest {
    /**
     * Instantiates a new Login request.
     */
    public LoginRequest() {
    }

    /**
     * Instantiates a new Login request.
     *
     * @param email    the email
     * @param password the password
     */
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private String email;
    private String password;

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
