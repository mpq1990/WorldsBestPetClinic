package com.example.worldsbestbestclinic.bootstrap;

import com.example.worldsbestbestclinic.model.Owner;
import com.example.worldsbestbestclinic.model.Vet;
import com.example.worldsbestbestclinic.services.OwnerService;
import com.example.worldsbestbestclinic.services.VetService;
import com.example.worldsbestbestclinic.services.map.OwnerServiceMap;
import com.example.worldsbestbestclinic.services.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader() {
        ownerService = new OwnerServiceMap();
        vetService = new VetServiceMap();
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner();
        owner1.setId(1L);
        owner1.setFirstName("Conan");
        owner1.setLastName("O'brain");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Jordan");
        owner2.setLastName("Schlansky");

        ownerService.save(owner2);

        System.out.println("Loaded the owners...");

        Vet vet1 = new Vet();
        vet1.setId(1L);
        vet1.setFirstName("Micheal");
        vet1.setLastName("Jordan");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setId(2L);
        vet2.setFirstName("James");
        vet2.setLastName("May");

        vetService.save(vet2);

        System.out.println("Loaded the vets...");

    }
}
