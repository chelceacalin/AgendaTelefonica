package com.agendatelefonica.calin.Agenda.Telefonica.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class UserController {


	@GetMapping("/")
	public String login() {
		return "login";
	}

	@GetMapping("/auth/{name}")
	public String authPage(@PathVariable String name, Model model) {
		// Aici poți folosi datele din model pentru a afișa informațiile utilizatorului pe pagina de autentificare
		return "index";
	}


	private static String getUserCredentials(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof OAuth2User principal) {
			Map<String, Object> attributes = principal.getAttributes();

			if (attributes.containsKey("iss")) {
				computeGoogleLogin(model, attributes);
			} else if (attributes.containsKey("gists_url")) {
				computeGithubLogin(model, attributes);
			} else {
				// Form Login
			}
		}
		return (String) model.getAttribute("name");
	}


	private static void computeGithubLogin(Model model, Map<String, Object> attributes) {
		System.out.println(attributes);
		model.addAttribute("name", attributes.get("name"));
		model.addAttribute("bio", attributes.get("bio"));
		model.addAttribute("avatar_url", attributes.get("avatar_url"));
		model.addAttribute("githubLogin", true);
	}

	private static void computeGoogleLogin(Model model, Map<String, Object> attributes) {
		model.addAttribute("name", attributes.get("name"));
		model.addAttribute("avatar_url", attributes.get("picture"));
		model.addAttribute("email", attributes.get("email"));
		model.addAttribute("googleLogin", true);
	}




	@GetMapping("*")
	public String notFound() {
		return "notFound";
	}

	private record UserInfo(String username, String password) {
	}
}
