package com.maven.tutorial.mavem.tutorial.service;

import com.maven.tutorial.mavem.tutorial.model.entity.Social;
import com.maven.tutorial.mavem.tutorial.model.entity.User;
import com.maven.tutorial.mavem.tutorial.model.request.UserRequest;
import com.maven.tutorial.mavem.tutorial.repository.SocialRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SocialService {
    private final SocialRepository repository;

    public Optional<Social> findByUser(User user) {
        return repository.findByUser(user);
    }

    public void create(UserRequest userRequest, User user) {
        Social social = new Social();
        social.setFacebook(userRequest.getFacebook());
        social.setInstagram(userRequest.getInstagram());
        social.setTwitter(userRequest.getTwitter());
        social.setUser(user);
        repository.save(social);
    }

}
