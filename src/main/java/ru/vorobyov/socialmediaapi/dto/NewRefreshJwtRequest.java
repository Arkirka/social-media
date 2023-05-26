package ru.vorobyov.socialmediaapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewRefreshJwtRequest {
    private String refreshToken;
}
