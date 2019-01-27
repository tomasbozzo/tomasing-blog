package com.tomasing.blog.service.mappers;

import com.tomasing.blog.repository.model.PostEntity;
import com.tomasing.blog.service.model.Post;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Maps to Post objects.
 */
public class PostMapper {

    /**
     * Maps PostEntity objects to Post.
     *
     * @param postEntity The PostEntity object. It can be null.
     * @return The mapped Post object or null.
     */
    public static Post toPost(@Nullable PostEntity postEntity) {
        return Optional.ofNullable(postEntity)
                .map(p -> Post.builder()
                        .id(p.getId())
                        .title(p.getTitle())
                        .content(p.getContent())
                        .category(p.getCategory())
                        .slug(p.getSlug())
                        .publishedBy(p.getPublishedBy())
                        .publicationDate(p.getPublicationDate())
                        .build())
                .orElse(null);
    }
}
