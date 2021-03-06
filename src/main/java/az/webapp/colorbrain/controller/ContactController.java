package az.webapp.colorbrain.controller;

import az.webapp.colorbrain.model.entity.ContactEntity;
import az.webapp.colorbrain.service.ContactService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/contact")
@AllArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping("/all")
    public String getAllContact(Model model) {
        model.addAttribute("contacts", contactService.getAllContact());
        return "admin/allContactPage";
    }

    @GetMapping("/allf")
    public String getAllContactF(Model model) {
        model.addAttribute("contacts", contactService.getAllContact());
        return "client/cb_contactUs";
    }

    @GetMapping("/{id}")
    public String getOneContactById(
            @PathVariable("id") ContactEntity contactEntity,
            Model model
    ) {
        model.addAttribute("contact", contactEntity);
        return "admin/oneContactPage";
    }

    @PostMapping("/create")
    public String saveContact(
            @Valid @ModelAttribute("contactEntity") ContactEntity contactEntity,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/createContactPage";
        }
        contactService.saveContact(contactEntity);
        return "redirect:/contact/all";
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("contactEntity", new ContactEntity());
        return "admin/createContactPage";
    }

    @PostMapping("/update")
    public String updateContact(ContactEntity contactEntity) {
        contactService.updateContact(contactEntity);
        return "redirect:/contact/all";
    }

    @PostMapping("/delete")
    public String deleteContact(
            @RequestParam("id") Long id
    ) {
        contactService.deleteContact(id);
        return "redirect:/contact/all";
    }
}
