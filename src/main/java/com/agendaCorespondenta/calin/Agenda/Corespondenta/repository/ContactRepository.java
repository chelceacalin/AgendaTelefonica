package com.agendaCorespondenta.calin.Agenda.Corespondenta.repository;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {

	@Query("select count(c) from Contact c where c.email=?1 and c.user.id=?2")
	long countByEmailAndUserId(String email, UUID userId);

	@Query("select c from Contact c where c.user.id=?1 order by c.email")
	List<Contact> findAllByUserId(UUID userId);

	Contact findByEmail(String email);

	void deleteByEmail(String email);
}
