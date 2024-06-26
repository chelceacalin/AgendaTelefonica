package com.agendaCorespondenta.calin.Agenda.Corespondenta.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "contact")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	String name;

	String nickName;

	String email;

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "id")
	UserEntity user;
}
