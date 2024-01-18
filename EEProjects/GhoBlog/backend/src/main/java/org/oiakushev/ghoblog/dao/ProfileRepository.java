package org.oiakushev.ghoblog.dao;

import org.oiakushev.ghoblog.model.Profile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends PagingAndSortingRepository<Profile, Long> {
    Profile findByEmail(String email);
}
