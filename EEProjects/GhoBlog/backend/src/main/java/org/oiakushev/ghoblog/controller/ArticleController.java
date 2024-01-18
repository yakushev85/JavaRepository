package org.oiakushev.ghoblog.controller;

import org.oiakushev.ghoblog.dto.ArticleRequest;
import org.oiakushev.ghoblog.model.Article;
import org.oiakushev.ghoblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @GetMapping("/public/api/articles")
    public Page<Article> getAll(@RequestParam(name="page", defaultValue = "0") Integer page,
                                @RequestParam(name="size", defaultValue = "10") Integer size) {
        return articleService.getAll(PageRequest.of(page, size));
    }

    @GetMapping("/public/api/articles/{id}")
    public Article getById(@PathVariable Long id) {
        return articleService.getById(id).orElseThrow();
    }

    @PostMapping("/private/api/articles")
    public Article add(@RequestBody ArticleRequest value) {
        return articleService.add(value);
    }
}
