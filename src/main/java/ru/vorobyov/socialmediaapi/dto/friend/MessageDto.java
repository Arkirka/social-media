package ru.vorobyov.socialmediaapi.dto.friend;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "email",
        "text"
})
public class MessageDto {
    public MessageDto() {
    }

    public MessageDto(String email, String text) {
        this.email = email;
        this.text = text;
    }

    @JsonProperty("email")
    private String email;
    @JsonProperty("text")
    private String text;

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }
}
