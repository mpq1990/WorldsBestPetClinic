package com.example.worldsbestbestclinic.services;

import com.example.worldsbestbestclinic.model.Pet;

import java.util.Set;

public interface PetService {
    Pet findById(Long id);

    Pet save(Pet pet);

    Set<Pet> findAll();
}
