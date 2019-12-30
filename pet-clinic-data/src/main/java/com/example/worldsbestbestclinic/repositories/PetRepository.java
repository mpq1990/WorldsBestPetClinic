package com.example.worldsbestbestclinic.repositories;

import com.example.worldsbestbestclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
