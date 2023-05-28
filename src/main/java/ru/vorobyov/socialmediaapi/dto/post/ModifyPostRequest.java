package ru.vorobyov.socialmediaapi.dto.post;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.Valid;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "title",
        "text",
        "images"
})
@Generated("jsonschema2pojo")
public class ModifyPostRequest {

    public ModifyPostRequest() {
    }

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

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("text")
    public String getText() {
        return text;
    }

    @JsonProperty("text")
    public void setText(String text) {
        this.text = text;
    }

    @JsonProperty("images")
    public List<ImageDto> getImages() {
        return imageDtos;
    }

    @JsonProperty("images")
    public void setImages(List<ImageDto> imageDtos) {
        this.imageDtos = imageDtos;
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
