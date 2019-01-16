package com.tomasing.blog.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostEntity {
    private String id;
    private String title;
    private String content;
    private String slug;
    private String category;
    private String createdBy;
    private ZonedDateTime createDate;
    private String updatedBy;
    private ZonedDateTime updateDate;
}
