package com.example.worldsbestbestclinic.services.springdatajpa;

import com.example.worldsbestbestclinic.model.Speciality;
import com.example.worldsbestbestclinic.repositories.SpecialityRepository;
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
class SpecialitySdJpaServiceTest {

    private final Long ID = 1L;
    private final String DESCRIPTION = "Orthopedics";

    @Mock
    SpecialityRepository specialityRepository;

    @InjectMocks
    SpecialitySdJpaService specialitySdJpaService;

    Speciality returnedSpeciality;

    @BeforeEach
    void setUp() {
        returnedSpeciality = Speciality.builder().id(ID).description(DESCRIPTION).build();
    }

    @Test
    void findAll() {
        Set<Speciality> specialitySet = new HashSet<>();
        specialitySet.add(Speciality.builder().id(1L).build());
        specialitySet.add(Speciality.builder().id(2L).build());

        when(specialityRepository.findAll()).thenReturn(specialitySet);
        Set<Speciality> returnedPetTypes = specialitySdJpaService.findAll();

        assertNotNull(returnedPetTypes);
        assertEquals(2, returnedPetTypes.size());
    }

    @Test
    void findById() {
        when(specialityRepository.findById(any())).thenReturn(Optional.of(returnedSpeciality));
        Speciality petType = specialitySdJpaService.findById(1L);
        assertNotNull(petType);
        assertEquals(petType, returnedSpeciality);
    }

    @Test
    void findByIdNotFound() {
        when(specialityRepository.findById(any())).thenReturn(Optional.empty());
        Speciality speciality = specialitySdJpaService.findById(5L);
        assertNull(speciality);
    }

    @Test
    void save() {
        Speciality petType = Speciality.builder().id(2L).build();
        when(specialityRepository.save(any())).thenReturn(petType);

        Speciality savedSpeciality = specialitySdJpaService.save(petType);
        assertNotNull(savedSpeciality);
        assertEquals(savedSpeciality, petType);
        verify(specialityRepository).save(any());
    }
    @Test
    void delete() {
        specialitySdJpaService.delete(specialityRepository.findById(1L).orElse(null));
        verify(specialityRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        specialitySdJpaService.deleteById(1L);
        verify(specialityRepository, times(1)).deleteById(any());
    }
}