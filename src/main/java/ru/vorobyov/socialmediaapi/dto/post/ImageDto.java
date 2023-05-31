package ru.vorobyov.socialmediaapi.dto.post;

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
 * DTO that represents image data for user.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "image_url"
})
@Generated("jsonschema2pojo")
public class ImageDto {

    /**
     * Instantiates a new Image dto.
     */
    public ImageDto() {
    }

    /**
     * Instantiates a new Image dto.
     *
     * @param imageUrl the image url
     */
    public ImageDto(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @JsonProperty("image_url")
    private String imageUrl;
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * Gets image url.
     *
     * @return the image url
     */
    @JsonProperty("image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Sets image url.
     *
     * @param imageUrl the image url
     */
    @JsonProperty("image_url")
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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