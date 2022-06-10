package org.oiakushev.ghoblog.service;

import org.oiakushev.ghoblog.dto.ArticleRequest;
import org.oiakushev.ghoblog.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ArticleService {
    Page<Article> getAll(Pageable pageable);

    Optional<Article> getById(Long id);

    Article add(ArticleRequest value);
}
