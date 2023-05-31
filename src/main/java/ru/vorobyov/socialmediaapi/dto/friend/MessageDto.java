package ru.vorobyov.socialmediaapi.dto.friend;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * DTO that represents message data for user.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "email",
        "text"
})
public class MessageDto {
    /**
     * Instantiates a new Message dto.
     */
    public MessageDto() {
    }

    /**
     * Instantiates a new Message dto.
     *
     * @param email the email
     * @param text  the text
     */
    public MessageDto(String email, String text) {
        this.email = email;
        this.text = text;
    }

    @JsonProperty("email")
    private String email;
    @JsonProperty("text")
    private String text;

    /**
     * Gets email.
     *
     * @return the email
     */
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets text.
     *
     * @return the text
     */
    @JsonProperty("text")
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }
}
