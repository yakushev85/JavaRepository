package org.yakushev.shopwebapp.service;

import org.yakushev.shopwebapp.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

	List<User> getAll();

	User getById(Long id);

	User add(User value);

	User update(User value);

	List<User> findByUsernameOrderByIdDesc(String username);

	User getUserFromRequest(HttpServletRequest request);

}
