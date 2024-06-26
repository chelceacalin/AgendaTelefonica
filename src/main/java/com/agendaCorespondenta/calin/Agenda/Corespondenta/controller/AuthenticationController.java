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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

	final UserService userService;
	final ContactService contactService;

	@GetMapping("/")
	public String index(Model model, @RequestParam(required = false) String query) {
		userService.getUserCredentials(model);
		if (!model.containsAttribute("email") || model.getAttribute("email") == null) {
			model.addAttribute("error", "Make sure that your github account has a public email!");
			return login(model);
		}
		UserEntity user = userService.getCurrentUser();

		List<Contact> contactList;

		if (query != null && !query.isEmpty()) {
			contactList = contactService.findAllByUserIdAndNameOrNickName(user.getId(), query);
		} else {
			contactList = contactService.findAllyUserId(user.getId());
		}

		contactList.forEach(System.out::println);
		model.addAttribute("contacts", contactList);
		model.addAttribute("user", user);
		model.addAttribute("hasEmailPassword", user.getSmtpEmailPassword() != null && user.getSmtpEmailPassword().length() > 3);
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
