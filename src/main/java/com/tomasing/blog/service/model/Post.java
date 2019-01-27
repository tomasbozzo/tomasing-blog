package com.tomasing.blog.service.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@Builder
@EqualsAndHashCode
@ToString
public class Post {
    private final String id;
    private final String title;
    private final String content;
    private final String slug;
    private final String category;
    private final String publishedBy;
    private final ZonedDateTime publicationDate;
}
