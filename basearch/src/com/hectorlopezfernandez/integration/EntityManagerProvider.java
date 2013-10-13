package com.hectorlopezfernandez.integration;

import javax.persistence.EntityManager;

import com.google.inject.Provider;

public class EntityManagerProvider implements Provider<EntityManager> {

	@Override
	public EntityManager get() {
		EntityManager em = PersistenceThreadLocalHelper.get();
		return em;
	}

}
