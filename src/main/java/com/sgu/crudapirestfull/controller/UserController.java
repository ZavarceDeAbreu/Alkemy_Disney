package com.sgu.crudapirestfull.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sgu.crudapirestfull.dao.IUserDao;
import com.sgu.crudapirestfull.models.UserModel;
import com.sgu.crudapirestfull.util.JWTUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;

@RestController
public class UserController {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private JWTUtil jwtUtil;

	private boolean validarToken(String token) {
		String usuarioId = jwtUtil.getKey(token);
		return usuarioId != null;
	}

	@RequestMapping(value = "api/users", method = RequestMethod.GET)
	public List<UserModel> getUser() {
		return userDao.getUsers();
	}

	@RequestMapping(value = "api/users", method = RequestMethod.POST)
	public void createUser(@RequestBody UserModel user) {
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		String hash = argon2.hash(1, 1024, 1, user.getPassword());
		user.setPassword(hash);

		userDao.create(user);
	}

	@RequestMapping(value = "api/users/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable Long id) {
		userDao.delete(id);
	}

//	@RequestMapping(value = "userEdit")
//	public UserModel edit () {
//		return user;
//	}

//	
//	@RequestMapping(value = "userSearch")
//	public UserModel search () {
//		UserModel user = new UserModel();
//		return user;
//	}

}
