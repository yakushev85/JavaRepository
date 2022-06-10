package org.oiakushev.ghoblog.dto;

import lombok.Data;

@Data
public class ArticleRequest {
    private String title;
    private String content;
    private String authorEmail;
}
