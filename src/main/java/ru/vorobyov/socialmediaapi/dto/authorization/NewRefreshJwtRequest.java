package ru.vorobyov.socialmediaapi.dto.authorization;

/**
 * DTO for transferring new refresh token.
 */
public class NewRefreshJwtRequest {
    /**
     * Instantiates a new New refresh jwt request.
     */
    public NewRefreshJwtRequest() {
    }

    /**
     * Instantiates a new New refresh jwt request.
     *
     * @param refreshToken the refresh token
     */
    public NewRefreshJwtRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    private String refreshToken;

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
