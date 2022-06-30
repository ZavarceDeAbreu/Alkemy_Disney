package com.sgu.crudapirestfull.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sgu.crudapirestfull.models.UserModel;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Repository
@Transactional
public class UserDao implements IUserDao {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	@Transactional
	public List<UserModel> getUsers() {
		String query = "FROM UserModel";
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public void delete(Long id) {
		UserModel user = entityManager.find(UserModel.class, id);
		entityManager.remove(user);
	}

	@Override
	public void create(UserModel user) {
		entityManager.merge(user);
	}

	@Override
	public UserModel getUserByCredentials(UserModel user) {

		String query = "FROM UserModel WHERE email = :email";
		List<UserModel> list = entityManager.createQuery(query)
				.setParameter("email", user.getEmail())
				.getResultList();

		if (list.isEmpty()) {
			return null;
		}

		String passwordHashed = list.get(0).getPassword();

		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		if (argon2.verify(passwordHashed, user.getPassword())) {
			return list.get(0);
		}
		return null;
	}

}
