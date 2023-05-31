package ru.vorobyov.socialmediaapi.dto.exception;

import java.util.Date;

/**
 * Api error response DTO for error representation for the user.
 */
public class ApiErrorResponse {
    /**
     * Instantiates a new Api error response.
     *
     * @param message    the message
     * @param statusCode the status code
     * @param timestamp  the timestamp
     */
    public ApiErrorResponse(String message, int statusCode, Date timestamp) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = timestamp;
    }

    private String message;
    private int statusCode;
    private Date timestamp;

    /**
     * Gets message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets status code.
     *
     * @return the status code
     */
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Sets status code.
     *
     * @param statusCode the status code
     */
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
