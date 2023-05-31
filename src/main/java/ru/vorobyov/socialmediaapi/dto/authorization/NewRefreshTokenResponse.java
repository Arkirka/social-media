package ru.vorobyov.socialmediaapi.dto.authorization;

/**
 * Response DTO for transferring new refresh token to authorized user.
 */
public class NewRefreshTokenResponse {
    /**
     * Instantiates a New refresh token response.
     */
    public NewRefreshTokenResponse() {
    }

    /**
     * Instantiates a New refresh token response.
     *
     * @param refreshToken the refresh token
     */
    public NewRefreshTokenResponse(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    private final String type = "Bearer";
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
