package com.agendatelefonica.calin.Agenda.Telefonica.repository;

import com.agendatelefonica.calin.Agenda.Telefonica.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
