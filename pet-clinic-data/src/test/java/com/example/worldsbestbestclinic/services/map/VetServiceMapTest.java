package com.example.worldsbestbestclinic.services.map;

import com.example.worldsbestbestclinic.model.Speciality;
import com.example.worldsbestbestclinic.model.Vet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VetServiceMapTest {

    private VetServiceMap vetServiceMap;
    private SpecialityServiceMap specialityServiceMap;
    private final Long ID = 1L;
    private final String SPECIALITY_DESCRIPTION = "Orthopedics";
    private final String LAST_NAME = "George";

    @BeforeEach
    void setUp() {
        specialityServiceMap = new SpecialityServiceMap();
        specialityServiceMap.save(Speciality.builder().description(SPECIALITY_DESCRIPTION).build());
        vetServiceMap = new VetServiceMap(new SpecialityServiceMap());
        vetServiceMap.save(Vet.builder().
                id(ID).
                lastName(LAST_NAME).
                specialities(specialityServiceMap.findAll()).
                build());
    }

    @Test
    void findAll() {
        Set<Vet> vetSet = vetServiceMap.findAll();
        assertEquals(1, vetSet.size());
    }

    @Test
    void deleteById() {
        vetServiceMap.deleteById(vetServiceMap.findById(ID).getId());
        assertEquals(0, vetServiceMap.findAll().size());
    }

    @Test
    void delete() {
        vetServiceMap.delete(vetServiceMap.findById(ID));
        assertEquals(0, vetServiceMap.findAll().size());
    }

    @Test
    void save() {
        Vet vetTwo = Vet.builder().id(2L).specialities(specialityServiceMap.findAll()).build();
        Vet savedVetType = vetServiceMap.save(vetTwo);
        assertEquals(savedVetType.getId(), 2L);
    }

    @Test
    void findById() {
        Vet vet = vetServiceMap.findById(ID);
        assertEquals(ID, vet.getId());
    }
}