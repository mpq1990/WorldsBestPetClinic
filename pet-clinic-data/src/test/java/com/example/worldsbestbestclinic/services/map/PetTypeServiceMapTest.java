package com.example.worldsbestbestclinic.services.map;

import com.example.worldsbestbestclinic.model.PetType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PetTypeServiceMapTest {
    private PetTypeServiceMap petTypeServiceMap;
    private final Long ID = 1L;
    private final String NAME = "Dog";

    @BeforeEach
    void setUp() {
        petTypeServiceMap = new PetTypeServiceMap();
        petTypeServiceMap.save(PetType.builder().id(ID).name(NAME).build());
    }

    @Test
    void findAll() {
        Set<PetType> petTypeSet = petTypeServiceMap.findAll();
        assertEquals(1, petTypeSet.size());
    }

    @Test
    void deleteById() {
        petTypeServiceMap.deleteById(petTypeServiceMap.findById(ID).getId());
        assertEquals(0, petTypeServiceMap.findAll().size());
    }

    @Test
    void delete() {
        petTypeServiceMap.delete(petTypeServiceMap.findById(ID));
        assertEquals(0, petTypeServiceMap.findAll().size());
    }

    @Test
    void save() {
        PetType petTypeTwo = PetType.builder().id(2L).build();
        PetType savedPetType = petTypeServiceMap.save(petTypeTwo);
        assertEquals(savedPetType.getId(), 2L);
    }

    @Test
    void findById() {
        PetType pet = petTypeServiceMap.findById(ID);
        assertEquals(ID, pet.getId());
    }
}