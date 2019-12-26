package com.example.worldsbestbestclinic.services;

import com.example.worldsbestbestclinic.model.Vet;

import java.util.Set;

public interface VetService {
    Vet findById(Long id);

    Vet save(Vet pet);

    Set<Vet> findAll();
}
