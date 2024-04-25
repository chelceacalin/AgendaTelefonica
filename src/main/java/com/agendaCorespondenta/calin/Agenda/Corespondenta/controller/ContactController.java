package com.agendaCorespondenta.calin.Agenda.Corespondenta.controller;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.Contact;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.service.ContactService;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.service.EmailSenderService;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/contact")
@Slf4j
@RequiredArgsConstructor
public class ContactController {
	final UserService userService;
	final ContactService contactService;
	final EmailSenderService emailSenderService;

	@GetMapping("/add")
	public String addContact(Model model, @ModelAttribute("user") UserEntity user) {
		UserEntity realUser = userService.findByEmail(user.getEmail());
		model.addAttribute("user", realUser);
		return "fragments/addNewContactForm";
	}

	@PostMapping("/add")
	public String addContact(@ModelAttribute Contact contact, String userEmail, Model model, RedirectAttributes redirectAttributes) {
		UserEntity currentUser = userService.findByEmail(userEmail);

		if (contactService.countByEmailAndUserId(contact.getEmail(), currentUser.getId()) > 0) {
			redirectAttributes.addFlashAttribute("addFormError", "Contact with that email already exists!");
			redirectAttributes.addFlashAttribute("user", currentUser);
			return "redirect:/contact/invalidAddForm";
		}

		contactService.saveContact(contact, currentUser);
		model.addAttribute("user", currentUser);
		model.addAttribute("isNewContact", true);
		model.addAttribute("contact", contact);
		redirectAttributes.addFlashAttribute("successMessage", "Contact added successfully!");
		return "redirect:/";
	}

	@GetMapping("/invalidAddForm")
	public String invalidAddForm() {
		return "fragments/addNewContactForm";
	}

	@GetMapping("/edit/{email}")
	public String updateUser(@PathVariable String email, Model model) {
		Contact contact = contactService.findByEmail(email);
		model.addAttribute("contact", contact);
		return "fragments/editContact";
	}

	@PostMapping("/update")
	public String updateUser(Contact contact, RedirectAttributes redirectAttributes) {
		contactService.updateContact(contact);
		redirectAttributes.addFlashAttribute("successMessage", "Contact updated successfully!");
		return "redirect:/";
	}


	@PostMapping("/delete/{email}")
	public String deleteUser(@PathVariable(name = "email") String email, RedirectAttributes redirectAttributes) {
		Contact contact = contactService.findByEmail(email);
		if (contact != null) {
			contactService.deleteByEmail(email);
		}
		redirectAttributes.addFlashAttribute("successMessage", "User with email " + email + " deleted successfully!");
		return "redirect:/";
	}

	@GetMapping("/sendMail/{email}")
	public String sendMail(@PathVariable String email, ModelData modelData, Model model) {
		model.addAttribute("fromEmail", modelData.fromEmail());
		model.addAttribute("email", email);
		return "fragments/sendMail";
	}

	public record ModelData(String fromEmail, String toEmail) {
	}

	@PostMapping("/sendMail")
	public String sendMail(MailTemplate mailTemplate, RedirectAttributes redirectAttributes) {
		System.out.println(mailTemplate);

		try {
			UserEntity user = userService.findByEmail(mailTemplate.fromEmail());

			emailSenderService.sendEmail(mailTemplate, user);
			redirectAttributes.addFlashAttribute("successMessage", "Email sent successfully!");
		} catch (Exception e) {
			log.error(e.getMessage());
			redirectAttributes.addFlashAttribute("error", "There has been an error sending the email, try again later");
			redirectAttributes.addAttribute("fromEmail", mailTemplate.fromEmail());
			return "redirect:/contact/sendMail/" + mailTemplate.toEmail();
		}

		return "redirect:/";
	}

	public record MailTemplate(String fromEmail, String toEmail, String subject, String body) {

	}
}
