package org.yakushev.shopwebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yakushev.shopwebapp.model.User;
import org.yakushev.shopwebapp.repository.UserRepository;
import org.yakushev.shopwebapp.security.JwtTokenRepository;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenRepository jwtTokenRepository;

	@Override
	public Page<User> getAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public User getById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public User add(User value) {
		value.setId(null);

		String rawPassword = value.getPassword();
		if (rawPassword != null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			value.setPassword(passwordEncoder.encode(rawPassword));
		}

		return userRepository.save(value);
	}

	@Override
	@Transactional
	public User update(User value) {
		User oldValue = getById(value.getId());

		String rawPassword = value.getPassword();
		if (rawPassword != null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

			oldValue.setPassword(passwordEncoder.encode(rawPassword));
		}

		if (value.getRole() != null) {
			oldValue.setRole(value.getRole());
		}

		return userRepository.save(oldValue);
	}

	@Override
	public User findByUsernameOrderByIdDesc(String username) {
		return userRepository.findByUsernameOrderByIdDesc(username);
	}

	@Override
	public User getUserFromRequest(HttpServletRequest request) {
		CsrfToken csrfToken = jwtTokenRepository.loadToken(request);

		if (csrfToken == null) {
			return null;
		}

		String username = jwtTokenRepository.getUsernameFromToken(csrfToken.getToken());
		return userRepository.findByUsernameOrderByIdDesc(username);
	}

	@Override
	public void checkAdminRole(HttpServletRequest request) {
		if (!isAdminRole(request)) {
			throw new IllegalArgumentException("User doesn't have access to the operation.");
		}
	}

	@Override
	public boolean isAdminRole(HttpServletRequest request) {
		User user = getUserFromRequest(request);

		return user != null && user.getRole() != null && user.getRole().equalsIgnoreCase("admin");
	}
}
