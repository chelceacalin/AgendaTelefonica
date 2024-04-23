package com.agendatelefonica.calin.Agenda.Telefonica.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Map;

@Controller
public class UserController {


	@GetMapping("/")
	public String index(Model model) {
		getUserCredentials(model);
		if (!model.containsAttribute("email")) {
			model.addAttribute("error", "Make sure that your github account has an public email!");
			return login();
		}
		return "index";
	}

	private static void getUserCredentials(Model model) {
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

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("A intrat");
		new SecurityContextLogoutHandler().logout(request, null, null);
		return "redirect:/";
	}

	@GetMapping("*")
	public String notFound() {
		return "notFound";
	}

}
