package com.agendaCorespondenta.calin.Agenda.Corespondenta.service;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

	final UserRepository userRepository;

	public UserEntity findByEmail(String email) {
		Optional<UserEntity> userEntity = userRepository.findByEmail(email);
		if (userEntity.isPresent()) {
			return userEntity.get();
		} else {
			throw new RuntimeException("User not found");
		}
	}

	public UserEntity saveUser(UserEntity userEntity) {
		return userRepository.save(userEntity);
	}

	public Boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	public UserEntity createUserIfNotExists(Model model) {
		String email = (String) model.getAttribute("email");
		Optional<UserEntity> userEntity = userRepository.findByEmail(email);
		if (userEntity.isEmpty()) {
			UserEntity user = new UserEntity()
					.setId(UUID.randomUUID())
					.setName((String) model.getAttribute("name"))
					.setEmail(email)
					.setAvatar_url((String) model.getAttribute("avatar_url"));
			userRepository.save(user);
			return user;
		}
		return userEntity.get();
	}

}
