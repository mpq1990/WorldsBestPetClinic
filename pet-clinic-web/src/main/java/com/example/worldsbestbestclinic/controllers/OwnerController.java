package com.example.worldsbestbestclinic.controllers;

import com.example.worldsbestbestclinic.model.Owner;
import com.example.worldsbestbestclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }


//    @RequestMapping({"", "/", "/index", "/index.html"})
//    public String listOwners(Model model) {
//        model.addAttribute("owners", ownerService.findAll());
//        return "owners/index";
//    }

    @InitBinder
    public void setDisallowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }


    @GetMapping("/find")
    public String findOwners(Model model) {
        model.addAttribute("owner", Owner.builder().build());

        return "owners/findOwners";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult bindingResult, Model model) {
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }

        List<Owner> ownerResults = ownerService.findAllByLastNameContains(owner.getLastName());

        if (ownerResults.isEmpty()) {
            bindingResult.rejectValue("lastName", "notFound", "not found");
            return "owners/findOwners";
        } else if (ownerResults.size() == 1) {
            owner = ownerResults.get(0);
            return "redirect:/owners/" + owner.getId();
        } else {
            model.addAttribute("selections", ownerResults);
            return "owners/ownerList";
        }
    }

    @GetMapping("/{ownerId}")
    public ModelAndView showOwner(@PathVariable Long ownerId) {
//        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
//        modelAndView.addObject(ownerService.findById(ownerId));
//
//        return modelAndView;
        ModelAndView mav = new ModelAndView("owners/ownerDetails");
        mav.addObject(ownerService.findById(ownerId));
        return mav;

    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Owner owner, Model model, BindingResult result) {
        if (result.hasErrors()) {
            return "owners/createOrUpdateOwnerForm";
        }
        Owner createdOwner = ownerService.save(owner);
        model.addAttribute("owner", createdOwner);

        return "redirect:/owners/" + createdOwner.getId();
    }

    @GetMapping("/{id}/edit")
    public String initUpdateForm(@PathVariable String id, Model model) {
        Owner owner = ownerService.findById(Long.valueOf(id));
        model.addAttribute("owner", owner);
        return "owners/createOrUpdateOwnerForm";
    }

    @PostMapping("{id}/edit")
    public String processUpdateForm(@Valid Owner owner, @PathVariable String id, BindingResult result) {
        if (result.hasErrors()) {
            return "owners/createOrUpdateOwnerForm";
        }
        owner.setId(Long.valueOf(id));
        Owner savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }
}
