package com.maven.tutorial.mavem.tutorial.repository;

import com.maven.tutorial.mavem.tutorial.model.entity.Address;
import com.maven.tutorial.mavem.tutorial.model.entity.Social;
import com.maven.tutorial.mavem.tutorial.model.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Integer> {
}
