package org.oiakushev.ghoblog.service;

import org.oiakushev.ghoblog.dao.ProfileRepository;
import org.oiakushev.ghoblog.dto.ProfileRequest;
import org.oiakushev.ghoblog.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile getByEmail(String email) {
        return profileRepository.findByEmail(email);
    }

    @Override
    public Profile add(ProfileRequest value) {
        Profile newProfile = new Profile();
        newProfile.setEmail(value.getEmail());
        newProfile.setName(value.getName());

        return profileRepository.save(newProfile);
    }
}
