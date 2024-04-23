package com.agendaCorespondenta.calin.Agenda.Corespondenta.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "UserEntity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	String email;

	String name;

	@OneToMany(mappedBy = "user")
	List<Contact> contacts;
}
