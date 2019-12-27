package com.example.worldsbestbestclinic.services;

import com.example.worldsbestbestclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);
}
