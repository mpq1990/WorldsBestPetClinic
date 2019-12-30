package com.example.worldsbestbestclinic.services.map;

import com.example.worldsbestbestclinic.model.Visit;
import com.example.worldsbestbestclinic.services.VisitService;

import java.util.Set;

public class VisitServiceMap extends AbstractServiceMap<Visit, Long> implements VisitService {
    @Override
    public Set<Visit> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Visit object) {
        super.delete(object);
    }

    @Override
    public Visit save(Visit object) {
        if (object.getPet() == null || object.getPet().getOwner().getId() == null || object.getPet().getId() == null
                || object.getPet().getOwner() == null) {
            throw new RuntimeException("Invalid Visit");
        }
        return super.save(object);
    }

    @Override
    public Visit findById(Long id) {
        return super.findById(id);
    }
}
