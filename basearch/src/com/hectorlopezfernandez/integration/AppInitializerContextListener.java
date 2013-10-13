package com.hectorlopezfernandez.integration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hectorlopezfernandez.utils.Constants;

public class AppInitializerContextListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(AppInitializerContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.debug("Creando contexto de la aplicacion");
		ServletContext sc = sce.getServletContext();
		// se crea el entitymanager de jpa y se añade al contexto
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("localAppPersistenceUnit");
		sc.setAttribute(Constants.JPA_ENTITY_MANAGER_FACTORY_CONTEXT_ATTRIBUTE_NAME, emf);
		// se crea el inyector de Guice usado para construir los action e inyectar dependencias y se añade al contexto
		Injector i = Guice.createInjector(new BaseBindingModule(new EntityManagerProvider()));
		sc.setAttribute(Constants.ROOT_GUICE_INJECTOR_CONTEXT_ATTRIBUTE_NAME, i);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.debug("Destruyendo contexto de la aplicacion");
		ServletContext sc = sce.getServletContext();
		// se elimina el inyector de guice del contexto - modo paranoico
		sc.removeAttribute(Constants.ROOT_GUICE_INJECTOR_CONTEXT_ATTRIBUTE_NAME);
		// se elimina el entitymanager de jpa del contexto y se hace limpieza
		EntityManagerFactory emf = (EntityManagerFactory)sc.getAttribute(Constants.JPA_ENTITY_MANAGER_FACTORY_CONTEXT_ATTRIBUTE_NAME);
		if (emf != null) emf.close();
		sc.removeAttribute(Constants.JPA_ENTITY_MANAGER_FACTORY_CONTEXT_ATTRIBUTE_NAME);
	}

}