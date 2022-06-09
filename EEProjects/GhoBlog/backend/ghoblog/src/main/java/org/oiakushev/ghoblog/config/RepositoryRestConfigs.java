package org.oiakushev.ghoblog.config;

import org.oiakushev.ghoblog.model.Article;
import org.oiakushev.ghoblog.model.Comment;
import org.oiakushev.ghoblog.model.Profile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RepositoryRestConfigs implements RepositoryRestConfigurer {
    private final static HttpMethod[] DEFAULT_NOT_NEEDED_REQUESTS =
            {HttpMethod.PUT, HttpMethod.POST, HttpMethod.DELETE, HttpMethod.PATCH};

    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        disableHttpMethods(Article.class, config, DEFAULT_NOT_NEEDED_REQUESTS);
        disableHttpMethods(Comment.class, config, DEFAULT_NOT_NEEDED_REQUESTS);
        disableHttpMethods(Profile.class, config, DEFAULT_NOT_NEEDED_REQUESTS);

        config.exposeIdsFor(Article.class);

        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metaData, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metaData, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }
}
