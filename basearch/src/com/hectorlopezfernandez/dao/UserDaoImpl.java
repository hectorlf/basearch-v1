package com.hectorlopezfernandez.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hectorlopezfernandez.model.User;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	private final static Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	/* Constructores */
	
	@Inject
	public UserDaoImpl(EntityManager em) {
		super(em);
	}
	
	/* Metodos */
	
	// recupera un usuario a partir del id
	@Override
	public User getUserById(Long id) {
		logger.debug("Recuperando usuario con id {}", id);
		return get(id, User.class);
	}

}