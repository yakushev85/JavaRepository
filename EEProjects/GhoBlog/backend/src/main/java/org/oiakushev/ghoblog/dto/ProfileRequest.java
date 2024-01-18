package org.oiakushev.ghoblog.dto;

import lombok.Data;
import org.oiakushev.ghoblog.model.Profile;

@Data
public class ProfileRequest {
    private String email;
    private String name;

    public ProfileRequest(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public static Profile toProfile(ProfileRequest value) {
        Profile newProfile = new Profile();
        newProfile.setEmail(value.getEmail());

        if (value.getName() == null || value.getName().trim().length() == 0) {
            newProfile.setName(value.getEmail().substring(0, value.getEmail().indexOf('@')));
        } else {
            newProfile.setName(value.getName());
        }

        return newProfile;
    }
}
