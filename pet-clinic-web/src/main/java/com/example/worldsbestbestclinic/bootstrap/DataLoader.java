package com.example.worldsbestbestclinic.bootstrap;

import com.example.worldsbestbestclinic.model.*;
import com.example.worldsbestbestclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {
        int count = petTypeService.findAll().size();
        if (count == 0) {
            loadData();
        }

    }

    private void loadData() {
        PetType dogType = new PetType();
        dogType.setName("Dog");
        PetType savedDogType = petTypeService.save(dogType);

        PetType petType2 = new PetType();
        petType2.setName("Cat");
        PetType savedCatType = petTypeService.save(petType2);

        System.out.println("Loaded the pet types...");

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality savedRadiology = specialityService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        Speciality savedSurgery = specialityService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("Dentistry");
        Speciality savedDentistry = specialityService.save(dentistry);

        System.out.println("Loaded the vet specialities...");

        Owner owner1 = new Owner();
        owner1.setFirstName("Conan");
        owner1.setLastName("O'brain");
        owner1.setAddress("Kaiserstr 1");
        owner1.setCity("Frankfurt");
        owner1.setTelephone("0987890987");

        Pet conansPet = new Pet();
        conansPet.setPetType(savedDogType);
        conansPet.setOwner(owner1);
        conansPet.setBirthDate(LocalDate.now());
        conansPet.setName("Scooby");
        owner1.getPets().add(conansPet);
        ownerService.save(owner1);

        Visit conansPetVisit1 = new Visit();
        conansPetVisit1.setDescription("The cat has the flu!");
        conansPetVisit1.setPet(conansPet);
        conansPet.getVisits().add(conansPetVisit1);
        visitService.save(conansPetVisit1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Jordan");
        owner2.setLastName("Schlansky");
        owner2.setAddress("Gerhat hauptmann 1");
        owner2.setCity("Saarbrucken");
        owner2.setTelephone("098778908");

        Pet jordansPet = new Pet();
        jordansPet.setPetType(savedCatType);
        jordansPet.setOwner(owner2);
        jordansPet.setBirthDate(LocalDate.now());
        jordansPet.setName("Milo");
        owner2.getPets().add(jordansPet);
        ownerService.save(owner2);

        System.out.println("Loaded the owners...");

        Vet vet1 = new Vet();
        vet1.setFirstName("Micheal");
        vet1.setLastName("Jordan");
        vet1.getSpecialities().add(savedSurgery);
        vet1.getSpecialities().add(savedRadiology);
        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("James");
        vet2.setLastName("May");
        vet2.getSpecialities().add(savedDentistry);
        vet2.getSpecialities().add(savedRadiology);
        vetService.save(vet2);

        System.out.println("Loaded the vets...");
    }
}
