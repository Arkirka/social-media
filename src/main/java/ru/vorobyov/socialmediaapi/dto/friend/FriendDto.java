package ru.vorobyov.socialmediaapi.dto.friend;

import java.util.LinkedHashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.Valid;

import javax.annotation.processing.Generated;

/**
 * DTO that represents friend data for user.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "friendId",
        "email",
        "first_name",
        "second_name"
})
@Generated("jsonschema2pojo")
public class FriendDto {

    /**
     * Instantiates a new Friend dto.
     */
    public FriendDto() {
    }

    /**
     * Instantiates a new Friend dto.
     *
     * @param friendId   the friend id
     * @param email      the email
     * @param firstName  the first name
     * @param secondName the second name
     */
    public FriendDto(Long friendId, String email, String firstName, String secondName) {
        this.friendId = friendId;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
    }

    @JsonProperty("friendId")
    private Long friendId;
    @JsonProperty("email")
    private String email;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("second_name")
    private String secondName;
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * Gets friend id.
     *
     * @return the friend id
     */
    @JsonProperty("friendId")
    public Long getFriendId() {
        return friendId;
    }

    /**
     * Sets friend id.
     *
     * @param friendId the friend id
     */
    @JsonProperty("friendId")
    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

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
     * Gets first name.
     *
     * @return the first name
     */
    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets second name.
     *
     * @return the second name
     */
    @JsonProperty("second_name")
    public String getSecondName() {
        return secondName;
    }

    /**
     * Sets second name.
     *
     * @param secondName the second name
     */
    @JsonProperty("second_name")
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * Gets additional properties.
     *
     * @return the additional properties
     */
    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    /**
     * Sets additional property.
     *
     * @param name  the name
     * @param value the value
     */
    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
