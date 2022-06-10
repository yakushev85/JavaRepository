package org.oiakushev.ghoblog.service;

import org.oiakushev.ghoblog.dto.ProfileRequest;
import org.oiakushev.ghoblog.model.Profile;

public interface ProfileService {
    Profile getByEmail(String email);

    Profile add(ProfileRequest value);
}
