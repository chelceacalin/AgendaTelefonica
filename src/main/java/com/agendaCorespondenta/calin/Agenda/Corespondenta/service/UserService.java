package com.agendaCorespondenta.calin.Agenda.Corespondenta.service;

import com.agendaCorespondenta.calin.Agenda.Corespondenta.model.UserEntity;
import com.agendaCorespondenta.calin.Agenda.Corespondenta.repository.UserRepository;
import jakarta.transaction.Transactional;
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
@Transactional
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

	public UserEntity createUserIfNotExists(UserEntity userEntity) {
		Optional<UserEntity> userEntityOptional = userRepository.findByEmail(userEntity.getEmail());
		if (userEntityOptional.isPresent()) {
			return userEntityOptional.get();
		} else {
			userEntity.setId(UUID.randomUUID());
			userRepository.save(userEntity);
			return userEntity;
		}
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

	public UserEntity getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof OAuth2User oauth2User) {
			String email = oauth2User.getAttribute("email");
			String avatarUrl = oauth2User.getAttribute("picture");
			String name = oauth2User.getAttribute("name");
			UserEntity user = new UserEntity()
					.setName(name)
					.setEmail(email)
					.setAvatar_url(avatarUrl);
			return createUserIfNotExists(user);
		}
		return null;
	}

	public void updateUser(UserEntity updatedUser) {
		UserEntity user = findByEmail(updatedUser.getEmail());
		if (user != null) {
			user.setName(updatedUser.getName());
			userRepository.save(user);
		}
	}
}
