package com.tomasing.blog.repository;

import com.tomasing.blog.repository.model.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Long> {
}
