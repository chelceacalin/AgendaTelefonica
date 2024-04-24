package com.agendaCorespondenta.calin.Agenda.Corespondenta.controller;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.Contact;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.service.ContactService;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contact")
@RequiredArgsConstructor
public class ContactController {
	final UserService userService;
	final ContactService contactService;

	@GetMapping("/add")
	public String addContact(Model model, @ModelAttribute("user") UserEntity user) {
		UserEntity realUser = userService.findByEmail(user.getEmail());
		model.addAttribute("user", realUser);
		return "fragments/addNewContactForm";
	}

	@PostMapping("/add")
	public String addContact(@ModelAttribute Contact contact, String userEmail, Model model) {

		System.out.println(contact);
		System.out.println(userEmail);
		UserEntity currentUser = userService.findByEmail(userEmail);

		if (contactService.existsByEmail(contact.getEmail())) {
			model.addAttribute("hasFormError", true);
			model.addAttribute("addFormError", "Contact with that email already exists!");
			return "redirect:/invalidAddForm";
		}

		contactService.saveContact(contact, currentUser);
		model.addAttribute("user", currentUser);
		model.addAttribute("isNewContact", true);
		model.addAttribute("contact", contact);
		return "redirect:/";
	}

	@GetMapping("/invalidAddForm")
	public String invalidAddForm(Model model) {
		return "fragments/addNewContactForm";
	}
}
