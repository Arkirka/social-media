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

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "friendId",
        "email",
        "first_name",
        "second_name"
})
@Generated("jsonschema2pojo")
public class FriendDto {

    public FriendDto() {
    }

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

    @JsonProperty("friendId")
    public Long getFriendId() {
        return friendId;
    }

    @JsonProperty("friendId")
    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("first_name")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("first_name")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("second_name")
    public String getSecondName() {
        return secondName;
    }

    @JsonProperty("second_name")
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
