package com.agendaCorespondenta.calin.Agenda.Corespondenta.controller;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.Contact;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.service.ContactService;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

	final UserService userService;
	final ContactService contactService;

	@GetMapping("/")
	public String index(Model model) {
		userService.getUserCredentials(model);
		String error = "";
		if (!model.containsAttribute("email") || model.getAttribute("email") == null) {
			error = "Make sure that your github account has a public email!";
			model.addAttribute("error", error);
			return login(model);
		}

		UserEntity user = userService.getCurrentUser();

		System.out.println(user);
		List<Contact> contactList = contactService.findAllyUserId(user.getId());
		model.addAttribute("contacts", contactList);
		System.out.println(contactList);
		model.addAttribute("user", user);
		return "index";
	}


	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("error", "");
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		new SecurityContextLogoutHandler().logout(request, null, null);
		return "redirect:/";
	}

	@GetMapping("*")
	public String notFound() {
		return "notFound";
	}

}
