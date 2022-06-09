package org.oiakushev.ghoblog.dao;

import org.oiakushev.ghoblog.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByEmail(@Param("email") String email);
}
