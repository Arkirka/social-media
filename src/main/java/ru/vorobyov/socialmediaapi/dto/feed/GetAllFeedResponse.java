package ru.vorobyov.socialmediaapi.dto.feed;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.Valid;
import ru.vorobyov.socialmediaapi.dto.post.PostDto;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Request DTO for paginated feed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "_metadata",
        "posts"
})
@Generated("jsonschema2pojo")
public class GetAllFeedResponse {

    @JsonProperty("_metadata")
    @Valid
    private Metadata metadata;
    @JsonProperty("posts")
    @Valid
    private List<PostDto> posts;
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * Gets metadata.
     *
     * @return the metadata
     */
    @JsonProperty("_metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Sets metadata.
     *
     * @param metadata the metadata
     */
    @JsonProperty("_metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
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

    /**
     * Gets posts.
     *
     * @return the posts
     */
    @JsonProperty("posts")
    public List<PostDto> getPosts() {
        return posts;
    }

    /**
     * Sets posts.
     *
     * @param posts the posts
     */
    @JsonProperty("posts")
    public void setPosts(List<PostDto> posts) {
        this.posts = posts;
    }
}
