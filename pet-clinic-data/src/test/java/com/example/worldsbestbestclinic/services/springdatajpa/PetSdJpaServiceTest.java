package com.example.worldsbestbestclinic.services.springdatajpa;

import com.example.worldsbestbestclinic.model.Pet;
import com.example.worldsbestbestclinic.repositories.PetRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetSdJpaServiceTest {

    private final Long ID = 1L;
    private final String NAME = "Scooby";

    @Mock
    PetRepository petRepository;

    @InjectMocks
    PetSdJpaService petSdJpaService;

    Pet returnedPet;

    @BeforeEach
    void setUp() {
        returnedPet = Pet.builder().id(ID).name(NAME).build();
    }

    @Test
    void findAll() {
        Set<Pet> petSet = new HashSet<>();
        petSet.add(Pet.builder().id(1L).build());
        petSet.add(Pet.builder().id(2L).build());

        when(petRepository.findAll()).thenReturn(petSet);
        Set<Pet> returnedPets = petSdJpaService.findAll();

        assertNotNull(returnedPets);
        assertEquals(2, returnedPets.size());
    }

    @Test
    void findById() {
        when(petRepository.findById(any())).thenReturn(Optional.of(returnedPet));
        Pet pet = petSdJpaService.findById(1L);
        assertNotNull(pet);
        assertEquals(pet, returnedPet);
    }

    @Test
    void findByIdNotFound() {
        when(petRepository.findById(any())).thenReturn(Optional.empty());
        Pet pet = petSdJpaService.findById(5L);
        assertNull(pet);
    }

    @Test
    void save() {
        Pet pet = Pet.builder().id(2L).build();
        when(petRepository.save(any())).thenReturn(pet);

        Pet savedPet = petSdJpaService.save(pet);
        assertNotNull(savedPet);
        assertEquals(savedPet, pet);
        verify(petRepository).save(any());
    }

    @Test
    void delete() {
        petSdJpaService.delete(petRepository.findById(1L).orElse(null));
        verify(petRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        petSdJpaService.deleteById(1L);
        verify(petRepository, times(1)).deleteById(any());
    }
}