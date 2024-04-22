package com.agendatelefonica.calin.Agenda.Telefonica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {


	@GetMapping("/")
	public String index() {
		return "index";
	}



}
