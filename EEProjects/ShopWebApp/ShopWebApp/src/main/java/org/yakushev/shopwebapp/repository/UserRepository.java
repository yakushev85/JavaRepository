package org.yakushev.shopwebapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.yakushev.shopwebapp.model.User;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	List<User> findByUsernameOrderByIdDesc(String username);
}
