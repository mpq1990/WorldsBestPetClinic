package com.example.worldsbestbestclinic.repositories;

import com.example.worldsbestbestclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
    Owner findByLastName(String lastName);
    List<Owner> findAllByLastNameContains(String lastName);
}
