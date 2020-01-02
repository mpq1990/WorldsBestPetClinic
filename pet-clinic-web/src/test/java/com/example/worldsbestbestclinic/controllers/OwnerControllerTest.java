package com.example.worldsbestbestclinic.controllers;

import com.example.worldsbestbestclinic.model.Owner;
import com.example.worldsbestbestclinic.services.OwnerService;
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

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController controller;

    Set<Owner> ownerSet;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        ownerSet = new HashSet<>();
        ownerSet.add(Owner.builder().id(1L).build());
        ownerSet.add(Owner.builder().id(2L).build());

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void listOwners() throws Exception {
        when(ownerService.findAll()).thenReturn(ownerSet);
        mockMvc.perform(get("/owners")).andExpect(status().isOk())
                .andExpect(view().name("owners/index"))
                .andExpect(model().attribute("owners", hasSize(2)));
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find")).andExpect(status().isOk()).
                andExpect(view().name("notImplemented"));
    }

    @Test
    void showOwner() throws Exception {
        Owner owner = Owner.builder().id(1L).build();

        when(ownerService.findById(anyLong())).thenReturn(owner);

        mockMvc.perform((get("/owners/1"))).
                andExpect(status().isOk()).
                andExpect(view().name("owners/ownerDetails")).
                andExpect(model().attributeExists("owner")).
                andExpect(model().attribute("owner", hasProperty("id", is(1L))));
    }
}