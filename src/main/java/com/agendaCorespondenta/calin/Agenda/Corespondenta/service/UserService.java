package com.agendaCorespondenta.calin.Agenda.Corespondenta.service;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;
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


	public void getUserCredentials(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof OAuth2User principal) {
			Map<String, Object> attributes = principal.getAttributes();

			if (attributes.containsKey("iss")) {
				computeGoogleLogin(model, attributes);
			} else if (attributes.containsKey("gists_url")) {
				computeGithubLogin(model, attributes);
			}
		}
	}


	private static void computeGithubLogin(Model model, Map<String, Object> attributes) {
		model.addAttribute("name", attributes.get("name"));
		model.addAttribute("avatar_url", attributes.get("avatar_url"));
		model.addAttribute("email", attributes.getOrDefault("email", ""));
		model.addAttribute("githubLogin", true);
	}

	private static void computeGoogleLogin(Model model, Map<String, Object> attributes) {
		model.addAttribute("name", attributes.get("name"));
		model.addAttribute("avatar_url", attributes.get("picture"));
		model.addAttribute("email", attributes.get("email"));
		model.addAttribute("googleLogin", true);
	}

}
