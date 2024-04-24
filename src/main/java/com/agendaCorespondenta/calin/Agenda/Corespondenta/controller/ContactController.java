package com.agendaCorespondenta.calin.Agenda.Corespondenta.controller;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.Contact;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
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

	@GetMapping("/add")
	public String addContact(Model model) {
		return "fragments/addNewContactForm";
	}

	@PostMapping("/add")
	public String addContact(@ModelAttribute Contact contact, Model model) {
		UserEntity user = userService.createUserIfNotExists(model);
		model.addAttribute("user", user);
		model.addAttribute("isNewContact", true);
		model.addAttribute("contact", contact);
		return "redirect:/";
	}

}
