package com.maven.tutorial.mavem.tutorial.service;

import com.maven.tutorial.mavem.tutorial.model.PaginationCriteria;
import com.maven.tutorial.mavem.tutorial.model.entity.Social;
import com.maven.tutorial.mavem.tutorial.model.entity.User;
import com.maven.tutorial.mavem.tutorial.model.request.UserRequest;
import com.maven.tutorial.mavem.tutorial.model.response.SocialResponse;
import com.maven.tutorial.mavem.tutorial.repository.SocialRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Page<SocialResponse> findAll() {
        PaginationCriteria criteria = new PaginationCriteria();
        return  repository.findAll(criteria.genPageRequest()).map(this::mapSocialResponse);
    }

    private SocialResponse mapSocialResponse(Social entity) {
        return SocialResponse.builder()
                .created(entity.getCreated())
                .facebook(entity.getFacebook())
                .firstName(entity.getUser().getFirstName())
                .Id(entity.getId())
                .instagram(entity.getInstagram())
                .lastName(entity.getUser().getLastName())
                .twitter(entity.getTwitter())
                .build();
    }

    public boolean validParentheses(String parens)
    {
      List<String> strings = Arrays.stream(parens.split("")).collect(Collectors.toList());

        return strings.stream().filter(s -> s.equals("(") || s.equals(")")).count() == 1;
    }

}
