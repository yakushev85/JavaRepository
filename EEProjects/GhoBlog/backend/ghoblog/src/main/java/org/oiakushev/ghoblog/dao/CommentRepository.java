package org.oiakushev.ghoblog.dao;

import org.oiakushev.ghoblog.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    Page<Comment> findByArticleIdOrderByIdDesc(Long articleId, Pageable pageable);
}
