package com.example.worldsbestbestclinic.services.springdatajpa;

import com.example.worldsbestbestclinic.model.PetType;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetTypeSdJpaServiceTest {

    private final Long ID = 1L;
    private final String NAME = "Dog";

    @Mock
    private PetTypeRepository petTypeRepository;


    @InjectMocks
    PetTypeSdJpaService petTypeSdJpaService;

    PetType returnedPetType;

    @BeforeEach
    void setUp() {
        returnedPetType = PetType.builder().id(ID).name(NAME).build();
    }

    @Test
    void findAll() {
        Set<PetType> petTypeSet = new HashSet<>();
        petTypeSet.add(PetType.builder().id(1L).build());
        petTypeSet.add(PetType.builder().id(2L).build());

        when(petTypeRepository.findAll()).thenReturn(petTypeSet);
        Set<PetType> returnedPetTypes = petTypeSdJpaService.findAll();

        assertNotNull(returnedPetTypes);
        assertEquals(2, returnedPetTypes.size());
    }

    @Test
    void findById() {
        when(petTypeRepository.findById(any())).thenReturn(Optional.of(returnedPetType));
        PetType petType = petTypeSdJpaService.findById(1L);
        assertNotNull(petType);
        assertEquals(petType, returnedPetType);
    }

    @Test
    void findByIdNotFound() {
        when(petTypeRepository.findById(any())).thenReturn(Optional.empty());
        PetType petType = petTypeSdJpaService.findById(5L);
        assertNull(petType);
    }

    @Test
    void save() {
        PetType petType = PetType.builder().id(2L).build();
        when(petTypeRepository.save(any())).thenReturn(petType);

        PetType savedPetType = petTypeSdJpaService.save(petType);
        assertNotNull(savedPetType);
        assertEquals(savedPetType, petType);
        verify(petTypeRepository).save(any());
    }

    @Test
    void delete() {
        petTypeSdJpaService.delete(petTypeRepository.findById(1L).orElse(null));
        verify(petTypeRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        petTypeSdJpaService.deleteById(1L);
        verify(petTypeRepository, times(1)).deleteById(any());
    }
}