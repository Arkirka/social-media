package ru.vorobyov.socialmediaapi.dto.post;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.Valid;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Request DTO for modifing post data.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "title",
        "text",
        "images"
})
@Generated("jsonschema2pojo")
public class ModifyPostRequest {

    /**
     * Instantiates a new Modify post request.
     */
    public ModifyPostRequest() {
    }

    /**
     * Instantiates a new Modify post request.
     *
     * @param title     the title
     * @param text      the text
     * @param imageDtos the image dtos
     */
    public ModifyPostRequest(String title, String text, List<ImageDto> imageDtos) {
        this.title = title;
        this.text = text;
        this.imageDtos = imageDtos;
    }

    @JsonProperty("title")
    private String title;
    @JsonProperty("text")
    private String text;
    @JsonProperty("images")
    @Valid
    private List<ImageDto> imageDtos;
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * Gets title.
     *
     * @return the title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
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

    /**
     * Gets images.
     *
     * @return the images
     */
    @JsonProperty("images")
    public List<ImageDto> getImages() {
        return imageDtos;
    }

    /**
     * Sets images.
     *
     * @param imageDtos the image dtos
     */
    @JsonProperty("images")
    public void setImages(List<ImageDto> imageDtos) {
        this.imageDtos = imageDtos;
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
