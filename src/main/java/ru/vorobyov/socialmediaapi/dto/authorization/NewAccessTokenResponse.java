package ru.vorobyov.socialmediaapi.dto.authorization;

/**
 * Response DTO for transferring new access token to authorized user.
 */
public class NewAccessTokenResponse {
    /**
     * Instantiates a new Get new access token response.
     */
    public NewAccessTokenResponse() {
    }

    /**
     * Instantiates a new Get new access token response.
     *
     * @param accessToken the access token
     */
    public NewAccessTokenResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    private final String type = "Bearer";
    private String accessToken;

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
}
