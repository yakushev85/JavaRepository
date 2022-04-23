package org.yakushev.shopwebapp.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yakushev.shopwebapp.model.User;
import org.yakushev.shopwebapp.repository.UserRepository;
import org.yakushev.shopwebapp.security.JwtTokenRepository;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
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
		return userRepository.save(value);
	}

	@Override
	@Transactional
	public User update(User value) {
		User oldValue = getById(value.getId());		value.setCreatedAt(null);
		try {
			User updatedValue = (new User()).merge(getById(value.getId()), value);
			return userRepository.save(updatedValue);
		} catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
			return oldValue;
		}
	}

	@Override
	public List<User> findByUsernameOrderByIdDesc(String username) {
		return userRepository.findByUsernameOrderByIdDesc(username);
	}

	@Override
	public User getUserFromRequest(HttpServletRequest request) {
		CsrfToken csrfToken = jwtTokenRepository.loadToken(request);
		String username = jwtTokenRepository.getUsernameFromToken(csrfToken.getToken());
		List<User> userList = userRepository.findByUsernameOrderByIdDesc(username);

		if (userList.isEmpty()) {
			return null;
		} else {
			return userList.get(0);
		}
	}

}
