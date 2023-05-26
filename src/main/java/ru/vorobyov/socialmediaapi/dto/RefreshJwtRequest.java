package ru.vorobyov.socialmediaapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshJwtRequest {
    private String refreshToken;
    private String accessToken;
}
