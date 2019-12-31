package com.example.worldsbestbestclinic.services.springdatajpa;

import com.example.worldsbestbestclinic.model.Visit;
import com.example.worldsbestbestclinic.repositories.VisitRepository;
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
class VisitSdJpaServiceTest {
    private final Long ID = 1L;

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    VisitSdJpaService visitSdJpaService;

    Visit returnedVisit;

    @BeforeEach
    void setUp() {
        String DESCRIPTION = "Flu!";
        returnedVisit = Visit.builder().id(ID).description(DESCRIPTION).build();
    }

    @Test
    void findAll() {
        Set<Visit> visitSet = new HashSet<>();
        visitSet.add(Visit.builder().id(1L).build());
        visitSet.add(Visit.builder().id(2L).build());

        when(visitRepository.findAll()).thenReturn(visitSet);
        Set<Visit> returnedVet = visitSdJpaService.findAll();

        assertNotNull(returnedVet);
        assertEquals(2, returnedVet.size());
    }

    @Test
    void findById() {
        when(visitRepository.findById(any())).thenReturn(Optional.of(returnedVisit));
        Visit visit = visitSdJpaService.findById(1L);
        assertNotNull(visit);
        assertEquals(visit, returnedVisit);
    }

    @Test
    void findByIdNotFound() {
        when(visitRepository.findById(any())).thenReturn(Optional.empty());
        Visit visit = visitSdJpaService.findById(5L);
        assertNull(visit);
    }

    @Test
    void save() {
        Visit visit = Visit.builder().id(2L).build();
        when(visitRepository.save(any())).thenReturn(visit);

        Visit savedVet = visitSdJpaService.save(visit);
        assertNotNull(savedVet);
        assertEquals(savedVet, visit);
        verify(visitRepository).save(any());
    }

    @Test
    void delete() {
        visitSdJpaService.delete(visitRepository.findById(1L).orElse(null));
        verify(visitRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        visitSdJpaService.deleteById(1L);
        verify(visitRepository, times(1)).deleteById(any());
    }
}