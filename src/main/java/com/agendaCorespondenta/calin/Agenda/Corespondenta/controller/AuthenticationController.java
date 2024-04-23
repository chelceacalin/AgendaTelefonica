package com.agendaCorespondenta.calin.Agenda.Corespondenta.controller;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {

	final UserService userService;

	@GetMapping("/")
	public String index(Model model) {
		getUserCredentials(model);
		System.out.println(model);
		if (!model.containsAttribute("email") || model.getAttribute("email") == null) {
			model.addAttribute("error", "Make sure that your github account has a public email!");
			return login();
		}

		UserEntity user = userService.createUserIfNotExists(model);
		model.addAttribute("user", user);

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
			}
		}
	}


	private static void computeGithubLogin(Model model, Map<String, Object> attributes) {
		model.addAttribute("name", attributes.get("name"));
		model.addAttribute("avatar_url", attributes.get("avatar_url"));
		model.addAttribute("email", attributes.getOrDefault("email", ""));
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
	public String logout(HttpServletRequest request) {
		new SecurityContextLogoutHandler().logout(request, null, null);
		return "redirect:/";
	}

	@GetMapping("*")
	public String notFound() {
		return "notFound";
	}

}
