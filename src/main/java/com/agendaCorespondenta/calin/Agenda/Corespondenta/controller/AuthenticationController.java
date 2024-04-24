package com.agendaCorespondenta.calin.Agenda.Corespondenta.controller;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

	final UserService userService;

	@GetMapping("/")
	public String index(Model model) {
		userService.getUserCredentials(model);
		String error = "";
		if (!model.containsAttribute("email") || model.getAttribute("email") == null) {
			error = "Make sure that your github account has a public email!";
			model.addAttribute("error", error);
			return login(model);
		}
		UserEntity user = userService.createUserIfNotExists(model);
		model.addAttribute("user", user);
		//https://stackoverflow.com/questions/33876699/how-to-make-a-model-attribute-global

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
