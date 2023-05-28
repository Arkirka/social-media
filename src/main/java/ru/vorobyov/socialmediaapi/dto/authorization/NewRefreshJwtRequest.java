package ru.vorobyov.socialmediaapi.dto.authorization;

public class NewRefreshJwtRequest {
    private String refreshToken;

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
