package com.sgu.crudapirestfull.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sgu.crudapirestfull.dao.IUserDao;
import com.sgu.crudapirestfull.models.UserModel;
import com.sgu.crudapirestfull.util.JWTUtil;

@RestController
public class AuthenController {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private JWTUtil jwtUtil;

	@RequestMapping(value = "api/login", method = RequestMethod.POST)
	public String logIn(@RequestBody UserModel user) {

		UserModel userLogged = userDao.getUserByCredentials(user);
		if (userLogged != null) {

			String tokenjwt = jwtUtil.create(String.valueOf(userLogged.getId()), userLogged.getEmail());
			return tokenjwt;
		}
		return "FAIL";
	}

}
