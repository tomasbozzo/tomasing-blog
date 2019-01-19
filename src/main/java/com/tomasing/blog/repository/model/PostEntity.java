package com.tomasing.blog.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@Builder
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
