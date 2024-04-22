package com.agendatelefonica.calin.Agenda.Telefonica.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "phoneNumber")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneNumber {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	UUID id;

	String number;

	@ManyToOne
	@JoinColumn(name = "contact_id", referencedColumnName = "id")
	Contact contact;
}
