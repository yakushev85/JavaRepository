package org.oiakushev.ghoblog.service;

import org.oiakushev.ghoblog.dao.ArticleRepository;
import org.oiakushev.ghoblog.dao.CommentRepository;
import org.oiakushev.ghoblog.dao.ProfileRepository;
import org.oiakushev.ghoblog.dto.CommentRequest;
import org.oiakushev.ghoblog.dto.ProfileRequest;
import org.oiakushev.ghoblog.model.Comment;
import org.oiakushev.ghoblog.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Page<Comment> getAllByArticleId(Long articleId, Pageable pageable) {
        return commentRepository.findByArticleIdOrderByIdDesc(articleId, pageable);
    }

    @Override
    public Optional<Comment> getById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Comment add(CommentRequest value) {
        Comment newComment = new Comment();
        newComment.setContent(value.getContent());
        newComment.setArticle(articleRepository.findById(value.getArticleId()).orElseThrow());

        Profile authorProfile = profileRepository.findByEmail(value.getAuthorEmail());

        if (authorProfile == null) {
            Profile newProfile =
                    ProfileRequest.toProfile(new ProfileRequest(value.getAuthorEmail(), value.getAuthorName()));

            authorProfile = profileRepository.save(newProfile);
        }

        newComment.setAuthor(authorProfile);

        return commentRepository.save(newComment);
    }
}
