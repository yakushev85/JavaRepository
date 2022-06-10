package org.oiakushev.ghoblog.controller;

import org.oiakushev.ghoblog.dto.CommentRequest;
import org.oiakushev.ghoblog.model.Comment;
import org.oiakushev.ghoblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("")
    public Page<Comment> getAllByArticleId(@RequestParam(name="articleId") Long articleId,
                                           @RequestParam(name="page", defaultValue = "0") Integer page,
                                           @RequestParam(name="size", defaultValue = "10") Integer size) {

        return commentService.getAllByArticleId(articleId, PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public Comment getById(@PathVariable Long id) {
        return commentService.getById(id).orElseThrow();
    }

    @PostMapping("")
    public Comment add(@RequestBody CommentRequest value) {
        return commentService.add(value);
    }
}
