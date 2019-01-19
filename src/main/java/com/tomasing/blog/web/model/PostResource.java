package com.tomasing.blog.web.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.hateoas.ResourceSupport;

import java.time.ZonedDateTime;

@Getter
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString
public class PostResource extends ResourceSupport {
    private final String title;
    private final String content;
    private final String slug;
    private final String category;
    private final String publishedBy;
    private final ZonedDateTime publicationDate;
}
