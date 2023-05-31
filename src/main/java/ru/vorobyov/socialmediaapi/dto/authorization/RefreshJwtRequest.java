package ru.vorobyov.socialmediaapi.dto.authorization;

/**
 * Request DTO for getting new access token.
 */
public class RefreshJwtRequest {
    /**
     * Instantiates a new Refresh jwt request.
     */
    public RefreshJwtRequest() {
    }

    /**
     * Instantiates a new Refresh jwt request.
     *
     * @param refreshToken the refresh token
     * @param accessToken  the access token
     */
    public RefreshJwtRequest(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }

    private String refreshToken;
    private String accessToken;

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
}
