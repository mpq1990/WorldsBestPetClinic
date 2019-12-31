package com.example.worldsbestbestclinic.services.map;

import com.example.worldsbestbestclinic.model.Speciality;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SpecialityServiceMapTest {
    private SpecialityServiceMap specialityServiceMap;
    private final Long ID = 1L;
    private final String DESCRIPTION = "Orthopedics";

    @BeforeEach
    void setUp() {
        specialityServiceMap = new SpecialityServiceMap();
        specialityServiceMap.save(Speciality.builder().id(ID).description(DESCRIPTION).build());
    }

    @Test
    void findAll() {
        Set<Speciality> specialitySet = specialityServiceMap.findAll();
        assertEquals(1, specialitySet.size());
    }

    @Test
    void deleteById() {
        specialityServiceMap.deleteById(specialityServiceMap.findById(ID).getId());
        assertEquals(0, specialityServiceMap.findAll().size());
    }

    @Test
    void delete() {
        specialityServiceMap.delete(specialityServiceMap.findById(ID));
        assertEquals(0, specialityServiceMap.findAll().size());
    }

    @Test
    void save() {
        Speciality specialityTwo = Speciality.builder().id(2L).build();
        Speciality savedPetType = specialityServiceMap.save(specialityTwo);
        assertEquals(savedPetType.getId(), 2L);
    }

    @Test
    void findById() {
        Speciality pet = specialityServiceMap.findById(ID);
        assertEquals(ID, pet.getId());
    }
}