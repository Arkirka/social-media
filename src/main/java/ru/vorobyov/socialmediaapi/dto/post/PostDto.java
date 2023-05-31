package ru.vorobyov.socialmediaapi.dto.post;

import java.util.LinkedHashMap;
import java.util.List;
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
 * DTO that represents post data for user.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "author",
        "title",
        "text",
        "images"
})
@Generated("jsonschema2pojo")
public class PostDto {

    /**
     * Instantiates a new Post dto.
     */
    public PostDto() {
    }

    /**
     * Instantiates a new Post dto.
     *
     * @param id        the id
     * @param author    the author
     * @param title     the title
     * @param text      the text
     * @param imageDtos the image dtos
     */
    public PostDto(Long id, String author, String title,
                   String text, List<ImageDto> imageDtos) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.text = text;
        this.imageDtos = imageDtos;
    }

    @JsonProperty("id")
    private Long id;
    @JsonProperty("author")
    private String author;
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
     * Gets id.
     *
     * @return the id
     */
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    @JsonProperty("id")
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets author.
     *
     * @return the author
     */
    @JsonProperty("author")
    public String getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author the author
     */
    @JsonProperty("author")
    public void setAuthor(String author) {
        this.author = author;
    }

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
