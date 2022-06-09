package org.oiakushev.ghoblog.config;

import org.oiakushev.ghoblog.model.Article;
import org.oiakushev.ghoblog.model.Comment;
import org.oiakushev.ghoblog.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class RepositoryRestConfigs implements RepositoryRestConfigurer {
    private final static HttpMethod[] DEFAULT_NOT_NEEDED_REQUESTS = {HttpMethod.PUT, HttpMethod.POST,
            HttpMethod.DELETE, HttpMethod.PATCH};

    @Value("${allowed.origins}")
    private String[] theAllowedOrigins;

    @Autowired
    private EntityManager entityManager;

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        disableHttpMethods(Article.class, config, DEFAULT_NOT_NEEDED_REQUESTS);
        disableHttpMethods(Comment.class, config, DEFAULT_NOT_NEEDED_REQUESTS);
        disableHttpMethods(Profile.class, config, DEFAULT_NOT_NEEDED_REQUESTS);

        exposeIds(config);

        cors.addMapping(config.getBasePath() + "/**").allowedOrigins(theAllowedOrigins);
    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnsupportedActions) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnsupportedActions));
    }

    private void exposeIds(RepositoryRestConfiguration config) {
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // - create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // - get the entity types for the entities
        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // - expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }
}
