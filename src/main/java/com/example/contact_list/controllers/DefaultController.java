package com.example.contact_list.controllers;

import com.example.contact_list.model.Contact;
import com.example.contact_list.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class DefaultController {

    private final ContactService contactService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("list", contactService.findAll());
        return "index";
    }

    @GetMapping("/contact/create")
    public String showCreateForm(Model model) {
        model.addAttribute("contact", new Contact());
        return "create";
    }

    @PostMapping("/contact/create")
    public String createContact(@ModelAttribute Contact contact) {
        contactService.save(contact);

        return "redirect:/";
    }

    @GetMapping("/contact/edit/{id}")
    public String showEditFrom(@PathVariable int id, Model model) {
        System.out.println("CONTACT edit GET");

        Contact contact = contactService.findById(id);
        if (contact != null) {
            model.addAttribute("contact", contact);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/contact/edit")
    public String editContact(@ModelAttribute Contact contact) {
        System.out.println("Edit Contact");
        contactService.update(contact);
        return "redirect:/";
    }

    @GetMapping("/contact/delete/{id}")
    public String deleteContact(@PathVariable int id) {
        contactService.deleteById(id);
        return "redirect:/";
    }

}
