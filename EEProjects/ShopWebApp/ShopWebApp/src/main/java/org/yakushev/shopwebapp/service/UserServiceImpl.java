package org.yakushev.shopwebapp.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yakushev.shopwebapp.model.User;
import org.yakushev.shopwebapp.repository.UserRepository;
import org.yakushev.shopwebapp.security.JwtTokenRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtTokenRepository jwtTokenRepository;

	@Override
	public List<User> getAll() {
		return Lists.newArrayList(userRepository.findAll());
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
	public List<User> findByUsernameOrderByIdDesc(String username) {
		List<User> resultList = userRepository.findByUsernameOrderByIdDesc(username);
		return (resultList == null)? new ArrayList<>() : resultList;
	}

	@Override
	public User getUserFromRequest(HttpServletRequest request) {
		CsrfToken csrfToken = jwtTokenRepository.loadToken(request);

		if (csrfToken == null) {
			return null;
		}

		String username = jwtTokenRepository.getUsernameFromToken(csrfToken.getToken());
		List<User> userList = userRepository.findByUsernameOrderByIdDesc(username);

		if (userList.isEmpty()) {
			return null;
		} else {
			return userList.get(0);
		}
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
