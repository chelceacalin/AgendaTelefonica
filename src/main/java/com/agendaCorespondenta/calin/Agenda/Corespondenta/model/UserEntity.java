package com.agendaCorespondenta.calin.Agenda.Corespondenta.model;


import jakarta.persistence.*;
import lombok.*;

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

	String avatar_url;

	@OneToMany(mappedBy = "user")
	@ToString.Exclude
	List<Contact> contacts;
}
