package ru.vorobyov.socialmediaapi.dto.feed;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.Valid;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DTO with metadata for pagination.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "page",
        "per_page",
        "page_count",
        "total_count",
        "links"
})
@Generated("jsonschema2pojo")
public class Metadata {

    /**
     * Instantiates a new Metadata.
     */
    public Metadata() {
    }

    /**
     * Instantiates a new Metadata.
     *
     * @param page       the page
     * @param perPage    the per page
     * @param pageCount  the page count
     * @param totalCount the total count
     * @param links      the links
     */
    public Metadata(int page, int perPage, int pageCount, long totalCount, Links links) {
        this.page = page;
        this.perPage = perPage;
        this.pageCount = pageCount;
        this.totalCount = totalCount;
        this.links = links;
    }

    @JsonProperty("page")
    private int page;
    @JsonProperty("per_page")
    private int perPage;
    @JsonProperty("page_count")
    private int pageCount;
    @JsonProperty("total_count")
    private long totalCount;
    @JsonProperty("links")
    @Valid
    private Links links;
    @JsonIgnore
    @Valid
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * Gets page.
     *
     * @return the page
     */
    @JsonProperty("page")
    public int getPage() {
        return page;
    }

    /**
     * Sets page.
     *
     * @param page the page
     */
    @JsonProperty("page")
    public void setPage(int page) {
        this.page = page;
    }

    /**
     * Gets per page.
     *
     * @return the per page
     */
    @JsonProperty("per_page")
    public int getPerPage() {
        return perPage;
    }

    /**
     * Sets per page.
     *
     * @param perPage the per page
     */
    @JsonProperty("per_page")
    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    /**
     * Gets page count.
     *
     * @return the page count
     */
    @JsonProperty("page_count")
    public int getPageCount() {
        return pageCount;
    }

    /**
     * Sets page count.
     *
     * @param pageCount the page count
     */
    @JsonProperty("page_count")
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * Gets total count.
     *
     * @return the total count
     */
    @JsonProperty("total_count")
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * Sets total count.
     *
     * @param totalCount the total count
     */
    @JsonProperty("total_count")
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * Gets links.
     *
     * @return the links
     */
    @JsonProperty("links")
    public Links getLinks() {
        return links;
    }

    /**
     * Sets links.
     *
     * @param links the links
     */
    @JsonProperty("links")
    public void setLinks(Links links) {
        this.links = links;
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