package com.agendaCorespondenta.calin.Agenda.Corespondenta.service;


import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.Contact;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.repository.ContractRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactService {

	final ContractRepository contractRepository;

	public void saveContact(Contact contact, UserEntity user) {
		if (!contractRepository.existsByEmail(contact.getEmail())) {
			contact.setId(UUID.randomUUID());
			contact.setUser(user);
			contractRepository.save(contact);
		}
	}

	public Boolean existsByEmail(String email) {
		return contractRepository.existsByEmail(email);
	}
}
