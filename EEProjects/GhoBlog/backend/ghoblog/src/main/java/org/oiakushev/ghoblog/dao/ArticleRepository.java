package org.oiakushev.ghoblog.dao;

import org.oiakushev.ghoblog.model.Article;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends PagingAndSortingRepository<Article, Long> {
}
