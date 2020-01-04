package com.example.worldsbestbestclinic.controllers;

import com.example.worldsbestbestclinic.model.Owner;
import com.example.worldsbestbestclinic.model.Pet;
import com.example.worldsbestbestclinic.model.PetType;
import com.example.worldsbestbestclinic.services.OwnerService;
import com.example.worldsbestbestclinic.services.PetService;
import com.example.worldsbestbestclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    PetService petService;

    @Mock
    OwnerService ownerService;

    @Mock
    PetTypeService petTypeService;

    @InjectMocks
    PetController petController;

    MockMvc mockMvc;

    Owner owner;
    Set<PetType> petTypeSet;

    @BeforeEach
    void setUp() {
        owner = Owner.builder().id(1L).build();

        petTypeSet = new HashSet<>();
        petTypeSet.add(PetType.builder().id(1L).name("Dog").build());
        petTypeSet.add(PetType.builder().id(2L).name("Cat").build());

        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    void initCreationForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypeSet);

        mockMvc.perform(get("/owners/1/pets/new")).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("owner")).
                andExpect(model().attributeExists("types")).
                andExpect(model().attributeExists("pet")).
                andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    void processCreationForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypeSet);

        mockMvc.perform(post("/owners/1/pets/new")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/owners/1"));

        verify(petService).save(any());
    }

    @Test
    void initUpdateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petService.findById(anyLong())).thenReturn(Pet.builder().id(1L).build());
        when(petTypeService.findAll()).thenReturn(petTypeSet);

        mockMvc.perform(get("/owners/1/pets/1/edit")).
                andExpect(status().isOk()).
                andExpect(model().attributeExists("owner")).
                andExpect(model().attributeExists("types")).
                andExpect(model().attributeExists("pet")).
                andExpect(view().name("pets/createOrUpdatePetForm"));
    }

    @Test
    void processUpdateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(petTypeService.findAll()).thenReturn(petTypeSet);

        mockMvc.perform(post("/owners/1/pets/1/edit")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/owners/1"));

        verify(petService, times(1)).save(any());
    }
}