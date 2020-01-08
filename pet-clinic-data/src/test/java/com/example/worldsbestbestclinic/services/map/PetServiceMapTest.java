package com.example.worldsbestbestclinic.services.map;

import com.example.worldsbestbestclinic.model.Pet;
import com.example.worldsbestbestclinic.services.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class PetServiceMapTest {
    PetServiceMap petServiceMap;
    private final Long ID = 1L;
    private final String NAME = "Scooby";
    @BeforeEach
    void setUp() {
        petServiceMap = new PetServiceMap();
        petServiceMap.save(Pet.builder().id(ID).name(NAME).build());
    }

    @Test
    void findAll() {
        Set<Pet> petSet = petServiceMap.findAll();
        assertEquals(1, petSet.size());

    }

    @Test
    void deleteById() {
        petServiceMap.deleteById(petServiceMap.findById(ID).getId());
        assertEquals(0, petServiceMap.findAll().size());
    }

    @Test
    void delete() {
        petServiceMap.delete(petServiceMap.findById(ID));
        assertEquals(0, petServiceMap.findAll().size());
    }

    @Test
    void save() {
        Pet petTwo = Pet.builder().id(2L).build();
        Pet savedPet = petServiceMap.save(petTwo);
        assertEquals(savedPet.getId(), 2L);
    }

    @Test
    void findById() {
        Pet pet = petServiceMap.findById(ID);
        assertEquals(ID, pet.getId());
    }
}