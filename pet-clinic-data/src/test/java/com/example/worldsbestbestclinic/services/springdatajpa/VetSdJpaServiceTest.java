package com.example.worldsbestbestclinic.services.springdatajpa;

import com.example.worldsbestbestclinic.model.Speciality;
import com.example.worldsbestbestclinic.model.Vet;
import com.example.worldsbestbestclinic.repositories.VetRepository;
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
class VetSdJpaServiceTest {
    private final Long ID = 1L;
    private final String SPECIALITY_DESCRIPTION = "Orthopedics";
    private final String LAST_NAME = "George";

    @Mock
    VetRepository vetRepository;

    @InjectMocks
    VetSdJpaService vetSdJpaService;

    Vet returnedVet;

    @BeforeEach
    void setUp() {
        Speciality speciality = Speciality.builder().description(SPECIALITY_DESCRIPTION).build();
        Speciality specialityTwo = Speciality.builder().description(SPECIALITY_DESCRIPTION).build();
        Set<Speciality> specialities = new HashSet<>();
        specialities.add(speciality);
        specialities.add(specialityTwo);
        returnedVet = Vet.builder().
                id(ID).
                lastName(LAST_NAME).
                specialities(specialities).
                build();
    }

    @Test
    void findAll() {
        Set<Vet> vetSet = new HashSet<>();
        vetSet.add(Vet.builder().id(1L).build());
        vetSet.add(Vet.builder().id(2L).build());

        when(vetRepository.findAll()).thenReturn(vetSet);
        Set<Vet> returnedVet = vetSdJpaService.findAll();

        assertNotNull(returnedVet);
        assertEquals(2, returnedVet.size());
    }

    @Test
    void findById() {
        when(vetRepository.findById(any())).thenReturn(Optional.of(returnedVet));
        Vet vet = vetSdJpaService.findById(1L);
        assertNotNull(vet);
        assertEquals(vet, returnedVet);
    }

    @Test
    void findByIdNotFound() {
        when(vetRepository.findById(any())).thenReturn(Optional.empty());
        Vet vet = vetSdJpaService.findById(5L);
        assertNull(vet);
    }

    @Test
    void save() {
        Vet vet = Vet.builder().id(2L).build();
        when(vetRepository.save(any())).thenReturn(vet);

        Vet savedVet = vetSdJpaService.save(vet);
        assertNotNull(savedVet);
        assertEquals(savedVet, vet);
        verify(vetRepository).save(any());
    }

    @Test
    void delete() {
        vetSdJpaService.delete(vetRepository.findById(1L).orElse(null));
        verify(vetRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        vetSdJpaService.deleteById(1L);
        verify(vetRepository, times(1)).deleteById(any());
    }
}