package com.example.worldsbestbestclinic.services.map;

import com.example.worldsbestbestclinic.model.Owner;
import com.example.worldsbestbestclinic.model.Pet;
import com.example.worldsbestbestclinic.model.PetType;
import com.example.worldsbestbestclinic.model.Visit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class VisitServiceMapTest {

    private VisitServiceMap visitServiceMap;
    private PetServiceMap petServiceMap;
    private final Long ID = 1L;

    @BeforeEach
    void setUp() {
        petServiceMap = new PetServiceMap();
        visitServiceMap = new VisitServiceMap();

        String LAST_NAME = "Jospeh";
        petServiceMap.save(Pet.builder().id(ID).name("Scoobs").
                petType(PetType.builder().id(ID).build()).
                owner(Owner.builder().id(ID).lastName(LAST_NAME).build())
                .build());
        String DESCRIPTION = "Flu!";
        visitServiceMap.save(Visit.builder().id(ID).pet(petServiceMap.findById(ID)).description(DESCRIPTION).build());
    }

    @Test
    void findAll() {
        Set<Visit> specialitySet = visitServiceMap.findAll();
        assertEquals(1, specialitySet.size());
    }

    @Test
    void deleteById() {
        visitServiceMap.deleteById(visitServiceMap.findById(ID).getId());
        assertEquals(0, visitServiceMap.findAll().size());
    }

    @Test
    void delete() {
        visitServiceMap.delete(visitServiceMap.findById(ID));
        assertEquals(0, visitServiceMap.findAll().size());
    }

    @Test
    void save() {
        Visit visitTwo = Visit.builder().id(2L).pet(petServiceMap.findById(ID)).build();
        Visit visitType = visitServiceMap.save(visitTwo);
        assertEquals(visitType.getId(), 2L);
    }

    @Test
    void findById() {
        Visit visit = visitServiceMap.findById(ID);
        assertEquals(ID, visit.getId());
    }
}