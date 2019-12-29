package com.example.worldsbestbestclinic.bootstrap;

import com.example.worldsbestbestclinic.model.Owner;
import com.example.worldsbestbestclinic.model.PetType;
import com.example.worldsbestbestclinic.model.Vet;
import com.example.worldsbestbestclinic.services.OwnerService;
import com.example.worldsbestbestclinic.services.PetTypeService;
import com.example.worldsbestbestclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
    }

    @Override
    public void run(String... args) throws Exception {

        PetType dogType = new PetType();
        dogType.setName("Dog");
        PetType savedDogType = petTypeService.save(dogType);

        PetType petType2 = new PetType();
        petType2.setName("Cat");
        PetType savedCatType = petTypeService.save(petType2);

        System.out.println("Loaded the pet types...");

        Owner owner1 = new Owner();
        owner1.setFirstName("Conan");
        owner1.setLastName("O'brain");
        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jordan");
        owner2.setLastName("Schlansky");
        ownerService.save(owner2);

        System.out.println("Loaded the owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Micheal");
        vet1.setLastName("Jordan");
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("James");
        vet2.setLastName("May");
        vetService.save(vet2);

        System.out.println("Loaded the vets...");

    }
}
