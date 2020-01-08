package com.example.worldsbestbestclinic.controllers;

import com.example.worldsbestbestclinic.model.Owner;
import com.example.worldsbestbestclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

//    @Test
//    void listOwners() throws Exception {
//        when(ownerService.findAll()).thenReturn(ownerSet);
//        mockMvc.perform(get("/owners")).andExpect(status().isOk())
//                .andExpect(view().name("owners/index"))
//                .andExpect(model().attribute("owners", hasSize(2)));
//    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(get("/owners/find")).
                andExpect(status().isOk()).
                andExpect(view().name("owners/findOwners")).
                andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processFindFormReturnMany() throws Exception {
        when(ownerService.findAllByLastNameContains(anyString())).thenReturn(Arrays.asList(Owner.builder().id(1L).build(),
                Owner.builder().id(2L).build()));

        mockMvc.perform(get("/owners")).
                andExpect(status().isOk()).
                andExpect(view().name("owners/ownerList")).
                andExpect(model().attribute("selections", hasSize(2)));
    }

    @Test
    void processFindFormReturnOne() throws Exception {
        when(ownerService.findAllByLastNameContains(anyString())).thenReturn(Arrays.asList(Owner.builder().
                id(1L).build()));

        mockMvc.perform(get("/owners")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/owners/1"));
    }

    @Test
    void processFindFormReturnAllWhenEmpty() throws Exception {
        when(ownerService.findAllByLastNameContains(anyString())).thenReturn(Arrays.asList(Owner.builder().id(1L).build(),
                Owner.builder().id(2L).build()));

        mockMvc.perform(get("/owners").
                param("lastName", "")).
                andExpect(status().isOk()).
                andExpect(model().attribute("selections", hasSize(2))).
                andExpect(view().name("owners/ownerList"));
    }

    @Test
    void processFindFormReturnAllWhenNull() throws Exception {
        when(ownerService.findAllByLastNameContains(anyString())).thenReturn(Arrays.asList(Owner.builder().id(1L).build(),
                Owner.builder().id(2L).build()));

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);


        mockMvc.perform(get("/owners")).
                andExpect(status().isOk()).
                andExpect(model().attribute("selections", hasSize(2))).
                andExpect(view().name("owners/ownerList"));
        verify(ownerService, times(1)).findAllByLastNameContains(captor.capture());
        assertEquals("", captor.getValue());
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



    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(get("/owners/new")).
                andExpect(status().isOk()).
                andExpect(view().name("owners/createOrUpdateOwnerForm")).
                andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void processCreationForm() throws Exception {
        when(ownerService.save(any())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(post("/owners/new")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/owners/1")).
                andExpect(model().attributeExists("owner"));

        verify(ownerService, times(1)).save(any());
    }

    @Test
    void initUpdateForm() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());
        mockMvc.perform(get("/owners/1/edit")).
                andExpect(status().isOk()).
                andExpect(view().name("owners/createOrUpdateOwnerForm")).
                andExpect(model().attributeExists("owner"));

        verify(ownerService, times(1)).findById(anyLong());
    }

    @Test
    void processUpdateForm() throws Exception {
        when(ownerService.save(any())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(post("/owners/1/edit")).
                andExpect(status().is3xxRedirection()).
                andExpect(view().name("redirect:/owners/1")).
                andExpect(model().attributeExists("owner"));

        verify(ownerService, times(1)).save(any());
    }


}