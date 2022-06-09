package org.oiakushev.ghoblog.dao;

import org.oiakushev.ghoblog.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RepositoryRestResource
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByArticleIdOrderByIdDesc(@Param("articleId") Long articleId, Pageable pageable);
}
