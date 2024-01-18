package org.oiakushev.ghoblog.dto;

import lombok.Data;

@Data
public class CommentRequest {
    private String content;
    private Long articleId;
    private String authorEmail;
    private String authorName;
}
