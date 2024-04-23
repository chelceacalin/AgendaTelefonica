package com.agendaCorespondenta.calin.Agenda.Corespondenta.repository;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
