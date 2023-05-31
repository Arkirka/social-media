package ru.vorobyov.socialmediaapi.dto.authorization;


/**
 * Login response DTO for transferring tokens to authorized user.
 */
public class LoginResponse {

    /**
     * Instantiates a new Login response.
     */
    public LoginResponse() {
    }

    /**
     * Instantiates a new Login response.
     *
     * @param accessToken  the access token
     * @param refreshToken the refresh token
     */
    public LoginResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    private final String type = "Bearer";
    private String accessToken;
    private String refreshToken;

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Gets access token.
     *
     * @return the access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Sets access token.
     *
     * @param accessToken the access token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * Gets refresh token.
     *
     * @return the refresh token
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * Sets refresh token.
     *
     * @param refreshToken the refresh token
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
