package org.oiakushev.ghoblog.controller;

import org.oiakushev.ghoblog.dto.ProfileRequest;
import org.oiakushev.ghoblog.model.Profile;
import org.oiakushev.ghoblog.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/private/api/profiles")
public class UserProfileController {
    @Autowired
    private ProfileService profileService;

    @GetMapping("/email/{email}")
    public Profile getByEmail(@PathVariable String email) {
        return profileService.getByEmail(email);
    }

    @PostMapping("")
    public Profile add(@RequestBody ProfileRequest value) {
        return profileService.add(value);
    }
}
