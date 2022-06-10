package org.oiakushev.ghoblog.service;

import org.oiakushev.ghoblog.dto.CommentRequest;
import org.oiakushev.ghoblog.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CommentService {
    Page<Comment> getAllByArticleId(Long articleId, Pageable pageable);

    Optional<Comment> getById(Long id);

    Comment add(CommentRequest value);
}
