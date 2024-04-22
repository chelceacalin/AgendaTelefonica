package com.agendatelefonica.calin.Agenda.Telefonica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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

	String email;

	@ManyToOne
	@JoinColumn(name = "userId", referencedColumnName = "id")
	@JsonIgnore
	UserEntity user;

	@OneToMany(mappedBy = "contact")
	List<PhoneNumber> phoneNumberList;
}
