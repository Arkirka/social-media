package ru.vorobyov.socialmediaapi.dto.feed;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.Valid;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DTO with links for pagination.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "self",
        "first",
        "previous",
        "next",
        "last"
})
@Generated("jsonschema2pojo")
public class Links {

    @JsonProperty("self")
    private String self;
    @JsonProperty("first")
    private String first;
    @JsonProperty("previous")
    private String previous;
    @JsonProperty("next")
    private String next;
    @JsonProperty("last")
    private String last;
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * Gets self.
     *
     * @return the self
     */
    @JsonProperty("self")
    public String getSelf() {
        return self;
    }

    /**
     * Sets self.
     *
     * @param self the self
     */
    @JsonProperty("self")
    public void setSelf(String self) {
        this.self = self;
    }

    /**
     * Gets first.
     *
     * @return the first
     */
    @JsonProperty("first")
    public String getFirst() {
        return first;
    }

    /**
     * Sets first.
     *
     * @param first the first
     */
    @JsonProperty("first")
    public void setFirst(String first) {
        this.first = first;
    }

    /**
     * Gets previous.
     *
     * @return the previous
     */
    @JsonProperty("previous")
    public String getPrevious() {
        return previous;
    }

    /**
     * Sets previous.
     *
     * @param previous the previous
     */
    @JsonProperty("previous")
    public void setPrevious(String previous) {
        this.previous = previous;
    }

    /**
     * Gets next.
     *
     * @return the next
     */
    @JsonProperty("next")
    public String getNext() {
        return next;
    }

    /**
     * Sets next.
     *
     * @param next the next
     */
    @JsonProperty("next")
    public void setNext(String next) {
        this.next = next;
    }

    /**
     * Gets last.
     *
     * @return the last
     */
    @JsonProperty("last")
    public String getLast() {
        return last;
    }

    /**
     * Sets last.
     *
     * @param last the last
     */
    @JsonProperty("last")
    public void setLast(String last) {
        this.last = last;
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
