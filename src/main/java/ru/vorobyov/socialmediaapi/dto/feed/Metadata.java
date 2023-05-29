package ru.vorobyov.socialmediaapi.dto.feed;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.Valid;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.Map;

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

    public Metadata() {
    }

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

    @JsonProperty("page")
    public int getPage() {
        return page;
    }

    @JsonProperty("page")
    public void setPage(int page) {
        this.page = page;
    }

    @JsonProperty("per_page")
    public int getPerPage() {
        return perPage;
    }

    @JsonProperty("per_page")
    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    @JsonProperty("page_count")
    public int getPageCount() {
        return pageCount;
    }

    @JsonProperty("page_count")
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    @JsonProperty("total_count")
    public long getTotalCount() {
        return totalCount;
    }

    @JsonProperty("total_count")
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    @JsonProperty("links")
    public Links getLinks() {
        return links;
    }

    @JsonProperty("links")
    public void setLinks(Links links) {
        this.links = links;
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