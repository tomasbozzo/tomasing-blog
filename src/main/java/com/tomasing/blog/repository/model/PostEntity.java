package com.tomasing.blog.repository.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class PostEntity {
    private final String id;
    private final String title;
    private final String content;
    private final String slug;
    private final String category;
    private final String createdBy;
    private final String publishedBy;
    private final ZonedDateTime publicationDate;
    private final ZonedDateTime createDate;
    private final String updatedBy;
    private final ZonedDateTime updateDate;
}
