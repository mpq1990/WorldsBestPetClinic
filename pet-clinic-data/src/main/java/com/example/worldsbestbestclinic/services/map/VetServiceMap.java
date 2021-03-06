package com.example.worldsbestbestclinic.services.map;

import com.example.worldsbestbestclinic.model.Speciality;
import com.example.worldsbestbestclinic.model.Vet;
import com.example.worldsbestbestclinic.services.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
public class VetServiceMap extends AbstractServiceMap<Vet, Long> implements VetService {
    private SpecialityServiceMap specialityServiceMap;

    public VetServiceMap(SpecialityServiceMap specialityServiceMap) {
        this.specialityServiceMap = specialityServiceMap;
    }

    @Override
    public Set<Vet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Vet object) {
        super.delete(object);
    }

    @Override
    public Vet save(Vet object) {
        if (object.getSpecialities().size() > 0) {
            object.getSpecialities().forEach(speciality -> {
                if (speciality.getId() == null) {
                    Speciality savedSpeciality = specialityServiceMap.save(speciality);
                    speciality.setId(savedSpeciality.getId());
                }
            });
        }
        return super.save(object);
    }

    @Override
    public Vet findById(Long id) {
        return super.findById(id);
    }
}
