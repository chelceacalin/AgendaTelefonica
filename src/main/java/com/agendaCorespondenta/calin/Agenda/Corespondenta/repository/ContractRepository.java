package com.agendaCorespondenta.calin.Agenda.Corespondenta.repository;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contact, UUID> {
}
