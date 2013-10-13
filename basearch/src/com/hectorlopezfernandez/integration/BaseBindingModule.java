package com.hectorlopezfernandez.integration;

import javax.persistence.EntityManager;

import com.google.inject.AbstractModule;
import com.hectorlopezfernandez.dao.UserDao;
import com.hectorlopezfernandez.dao.UserDaoImpl;

public final class BaseBindingModule extends AbstractModule {

	private final EntityManagerProvider entityManagerProvider;

	// constructores
	
	public BaseBindingModule(EntityManagerProvider entityManagerProvider) {
		super();
		this.entityManagerProvider = entityManagerProvider;
	}

	@Override
	protected void configure() {
		bind(UserDao.class).to(UserDaoImpl.class);
		bind(EntityManager.class).toProvider(entityManagerProvider);
	}

}