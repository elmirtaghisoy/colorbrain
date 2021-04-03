package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.CategoryEntity;
import az.webapp.colorbrain.model.entity.ContactEntity;
import az.webapp.colorbrain.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping(value = "/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/active")
    public String getAllContact(Model model) {
        model.addAttribute("contacts", contactService.getAllContact());
        return "admin/allContactPage";
    }

    @GetMapping("/{id}")
    public String getOneContactById(@PathVariable("id") ContactEntity contactEntity, Model model) {
        model.addAttribute("contact", contactEntity);
        return "admin/oneContactPage";
    }

    @PostMapping("/delete")
    public String deleteContact(ContactEntity contactEntity) {
        contactService.deleteContact(contactEntity);
        return "redirect:/contact/active";
    }

    @PostMapping("/update")
    public String updateContact(ContactEntity contactEntity) {
        contactService.updateContact(contactEntity);
        return "redirect:/contact/active";
    }

    @PostMapping("/create")
    public String saveContact(@Valid @ModelAttribute("contactEntity") ContactEntity contactEntity,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "admin/createContactPage";
        }
        contactService.saveContact(contactEntity);
        return "redirect:/contact/active";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model){
        model.addAttribute("contactEntity",new ContactEntity());
        return "admin/createContactPage";
    }
}
