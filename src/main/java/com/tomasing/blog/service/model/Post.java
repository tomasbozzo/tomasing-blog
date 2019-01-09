package com.tomasing.blog.service.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.ZonedDateTime;

@Getter
@Builder
@EqualsAndHashCode
public class Post {
    private final Long id;
    private final String title;
    private final String content;
    private final String slug;
    private final String category;
    private final String createdBy;
    private final ZonedDateTime createDate;
    private final String updatedBy;
    private final ZonedDateTime updateDate;
}