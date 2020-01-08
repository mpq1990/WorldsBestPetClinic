package com.example.worldsbestbestclinic.controllers;

import com.example.worldsbestbestclinic.model.Vet;
import com.example.worldsbestbestclinic.services.VetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {

    @Mock
    VetService vetService;

    @InjectMocks
    VetController controller;

    MockMvc mockMvc;

    Set<Vet> vetSet;

    @BeforeEach
    void setUp() {
        vetSet = new HashSet<>();
        vetSet.add(Vet.builder().id(1L).build());
        vetSet.add(Vet.builder().id(2L).build());

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listVets() throws Exception {
        when(vetService.findAll()).thenReturn(vetSet);
        mockMvc.perform(get("/vets")).andExpect(status().isOk())
                .andExpect(view().name("vets/index"))
                .andExpect(model().attribute("vets", hasSize(2)));
    }

    @Test
    void getVetsJson() throws Exception {
        when(vetService.findAll()).thenReturn(vetSet);
        mockMvc.perform(get("/api/vets")).
                andExpect(status().isOk()).
                andExpect(content().string("[{\"id\":1,\"firstName\":null,\"lastName\":null,\"specialities\":null,\"new\":false},{\"id\":2,\"firstName\":null,\"lastName\":null,\"specialities\":null,\"new\":false}]"));
    }
}