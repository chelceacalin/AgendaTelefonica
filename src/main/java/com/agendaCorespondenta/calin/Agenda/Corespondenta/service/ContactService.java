package com.agendaCorespondenta.calin.Agenda.Corespondenta.service;


import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.Contact;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.repository.ContactRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactService {

	final ContactRepository contactRepository;

	public void saveContact(Contact contact, UserEntity user) {
		if (contactRepository.countByEmailAndUserId(contact.getEmail(), user.getId()) == 0) {
			contact.setId(UUID.randomUUID());
			contact.setUser(user);
			contactRepository.save(contact);
		}
	}

	public Contact findByEmail(String email) {
		return contactRepository.findByEmail(email);
	}

	public void deleteByEmail(String email) {
		contactRepository.deleteByEmail(email);
	}

	public long countByEmailAndUserId(String email, UUID userId) {
		return contactRepository.countByEmailAndUserId(email, userId);
	}

	public List<Contact> findAllByUserIdAndNameOrNickName(UUID userId,String query) {
		return contactRepository.findAllByUserIdAndNameOrNickName(userId, query);
	}

	public List<Contact> findAllyUserId(UUID userId) {
		return contactRepository.findAllByUserId(userId);
	}

	public void updateContact(Contact updatedContact){
		Contact contact = findByEmail(updatedContact.getEmail());
		if(contact != null){
			contact.setName(updatedContact.getName());
			contact.setNickName(updatedContact.getNickName());
			contactRepository.save(contact);
		}
	}
}
