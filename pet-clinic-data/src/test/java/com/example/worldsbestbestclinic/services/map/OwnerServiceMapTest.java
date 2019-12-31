package com.example.worldsbestbestclinic.services.map;

import com.example.worldsbestbestclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    private OwnerServiceMap ownerServiceMap;
    private final Long OWNER_ID = 1L;
    public final String LAST_NAME = "Jeff";

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());
        ownerServiceMap.save(Owner.builder().id(OWNER_ID).lastName(LAST_NAME).build());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerServiceMap.findAll();
        assertEquals(1, ownerSet.size());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(ownerServiceMap.findById(OWNER_ID).getId());
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(OWNER_ID));
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void save() {
        Owner ownerTwo = Owner.builder().id(2L).build();
        Owner savedOwner = ownerServiceMap.save(ownerTwo);
        assertEquals(savedOwner.getId(), 2L);
    }

    @Test
    void saveWithoutDefaultId() {
        Owner ownerTwo = Owner.builder().build();
        Owner savedOwner = ownerServiceMap.save(ownerTwo);
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void findById() {
        Owner owner = ownerServiceMap.findById(OWNER_ID);
        assertEquals(OWNER_ID, owner.getId());
    }

    @Test
    void findByLastName() {
        Owner owner = ownerServiceMap.findByLastName(LAST_NAME);
        assertNotNull(owner);
        assertEquals(LAST_NAME, owner.getLastName());
    }
}