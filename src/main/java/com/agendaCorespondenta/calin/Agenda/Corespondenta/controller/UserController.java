package com.agendaCorespondenta.calin.Agenda.Corespondenta.controller;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	final UserService userService;

	@GetMapping("/updateProfile/{email}")
	public String updateProfile(@PathVariable String email, Model model) {
		UserEntity user = userService.findByEmail(email);
		model.addAttribute("user", user);
		return "fragments/editAccount";
	}

	@PostMapping("/updateProfile")
	public String updateProfile(UserEntity user, RedirectAttributes redirectAttributes) {
		userService.updateUser(user);
		redirectAttributes.addFlashAttribute("successMessage", "You have successfully updated your profile!");
		return "redirect:/";
	}
}
