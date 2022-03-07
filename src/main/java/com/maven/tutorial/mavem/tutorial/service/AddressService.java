package com.maven.tutorial.mavem.tutorial.service;

import com.maven.tutorial.mavem.tutorial.model.entity.Address;
import com.maven.tutorial.mavem.tutorial.model.entity.Social;
import com.maven.tutorial.mavem.tutorial.model.entity.User;
import com.maven.tutorial.mavem.tutorial.model.request.UserRequest;
import com.maven.tutorial.mavem.tutorial.repository.AddressRepository;
import com.maven.tutorial.mavem.tutorial.repository.SocialRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AddressService {
    private final AddressRepository repository;

    public void create(UserRequest userRequest, User user) {
        Address address = new Address();
        address.setUser(user);
        address.setLine1(userRequest.getLine1());
        address.setLine2(userRequest.getLine2());
        address.setZipcode(userRequest.getZipcode());
        repository.save(address);
    }

}
