package org.oiakushev.ghoblog.service;

import org.oiakushev.ghoblog.dao.ArticleRepository;
import org.oiakushev.ghoblog.dao.ProfileRepository;
import org.oiakushev.ghoblog.dto.ArticleRequest;
import org.oiakushev.ghoblog.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Page<Article> getAll(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public Optional<Article> getById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Article add(ArticleRequest value) {
        Article newArticle = new Article();
        newArticle.setTitle(value.getTitle());
        newArticle.setContent(value.getContent());
        newArticle.setAuthor(profileRepository.findByEmail(value.getAuthorEmail()));

        return articleRepository.save(newArticle);
    }
}
