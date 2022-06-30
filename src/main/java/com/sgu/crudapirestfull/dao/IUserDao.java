package com.sgu.crudapirestfull.dao;

import java.util.List;

import com.sgu.crudapirestfull.models.UserModel;

public interface IUserDao {

	List<UserModel> getUsers();

	void delete(Long id);

	void create(UserModel user);

	UserModel getUserByCredentials(UserModel user);	
}
