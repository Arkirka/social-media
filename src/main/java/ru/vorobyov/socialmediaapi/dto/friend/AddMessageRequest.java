package ru.vorobyov.socialmediaapi.dto.friend;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Request DTO with data for creating new message .
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "message"
})
public class AddMessageRequest {
    /**
     * Instantiates a new Add message request.
     */
    public AddMessageRequest() {
    }

    @JsonProperty("message")
    private String message;

    /**
     * Gets message.
     *
     * @return the message
     */
    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }
}
