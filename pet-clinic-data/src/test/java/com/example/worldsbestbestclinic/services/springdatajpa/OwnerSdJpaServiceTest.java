package com.example.worldsbestbestclinic.services.springdatajpa;

import com.example.worldsbestbestclinic.model.Owner;
import com.example.worldsbestbestclinic.repositories.OwnerRepository;
import com.example.worldsbestbestclinic.repositories.PetRepository;
import com.example.worldsbestbestclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSdJpaServiceTest {
    public static final String LAST_NAME = "John";
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    PetRepository petRepository;
    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSdJpaService ownerSdJpaService;

    Owner returnedOwner;

    @BeforeEach
    void setUp() {
        returnedOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(returnedOwner);
        Owner owner = ownerSdJpaService.findByLastName(LAST_NAME);
        assertEquals(LAST_NAME, owner.getLastName());

        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = new HashSet<>();
        ownerSet.add(Owner.builder().id(1L).build());
        ownerSet.add(Owner.builder().id(2L).build());

        when(ownerRepository.findAll()).thenReturn(ownerSet);
        Set<Owner> returnedOwners = ownerSdJpaService.findAll();

        assertNotNull(returnedOwners);
        assertEquals(2, returnedOwners.size());

    }

    @Test
    void findById() {
        when(ownerRepository.findById(any())).thenReturn(Optional.of(returnedOwner));
        Owner owner = ownerSdJpaService.findById(1L);
        assertNotNull(owner);
        assertEquals(owner, returnedOwner);
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(any())).thenReturn(Optional.empty());
        Owner owner = ownerSdJpaService.findById(5L);
        assertNull(owner);
    }

    @Test
    void save() {

        Owner owner = Owner.builder().id(2L).build();
        when(ownerRepository.save(any())).thenReturn(owner);

        Owner savedOwner = ownerSdJpaService.save(owner);
        assertNotNull(savedOwner);
        assertEquals(savedOwner, owner);
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        ownerSdJpaService.delete(ownerRepository.findById(1L).orElse(null));
        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        ownerSdJpaService.deleteById(1L);
        verify(ownerRepository).deleteById(any());
    }
}