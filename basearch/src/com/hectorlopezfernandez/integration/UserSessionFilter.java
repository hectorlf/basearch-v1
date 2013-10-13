package com.hectorlopezfernandez.integration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;
import com.hectorlopezfernandez.utils.Constants;

public class UserSessionFilter implements Filter {

	private final static Logger logger = LoggerFactory.getLogger(UserSessionFilter.class);

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		logger.debug("Entrando en UserSessionFilter.doFilter");
		// se comprueba si ya se ha entrado a este filtro en esta request
		Object token = request.getAttribute(Constants.USER_SESSION_FILTER_TOKEN_REQUEST_ATTRIBUTE_NAME);
		if (token == null) doFirstTimeFilter(request, response, filterChain);
		else doRecurringFilter(request, response, filterChain);
		logger.debug("Saliendo de UserSessionFilter.doFilter");
	}

	private void doFirstTimeFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		logger.debug("No se ha detectado el token del filtro para esta request. Suponemos que es la primera vez que se pasa por el filtro.");
		// se marca la request con el token
		request.setAttribute(Constants.USER_SESSION_FILTER_TOKEN_REQUEST_ATTRIBUTE_NAME, Constants.USER_SESSION_FILTER_TOKEN_REQUEST_ATTRIBUTE_NAME);
		// se comprueba si hay un usuario logado en la aplicacion
		Subject currentSubject = SecurityUtils.getSubject();
		if (currentSubject.isAuthenticated()) {
			// se obtiene la informacion del usuario autenticado, para que esté disponible en los action
			Injector i = (Injector)request.getServletContext().getAttribute(Constants.ROOT_GUICE_INJECTOR_CONTEXT_ATTRIBUTE_NAME);
			if (i == null) throw new RuntimeException("No se ha encontrado el inyector de Guice en el contexto de servlet. Esto indica un fallo de configuracion y debe revisarse el log de arranque y el AppInitializerContextListener.");
			Long userId = (Long)currentSubject.getPrincipal();
			logger.debug("Recuperando usuario logado con id: {}", userId);
			//TODO este proceso es particular de cada aplicación
			/*
			UserService us = i.getInstance(UserService.class);
			User u = us.getAuthorById(userId);
			logger.debug("Usuario {} autenticado en el sistema", u.getUsername());
			// se guarda el usuario en la request para que los action puedan recogerlo
			request.setAttribute(Constants.LOGGED_USER_REQUEST_ATTRIBUTE_NAME, u);
			*/
		}
		// una vez gestionado el usuario, se continua con la cadena de filtros
		filterChain.doFilter(request, response);
		// una vez temina la peticion, se debe hacer limpieza
		request.removeAttribute(Constants.LOGGED_USER_REQUEST_ATTRIBUTE_NAME);
		if (!currentSubject.isAuthenticated() && request.getAttribute(Constants.STRIPES_FLASH_SCOPE_MARKER_REQUEST_ATTRIBUTE_NAME) == null) {
			// si no habia usuario logado ni flash scope, es necesario cerrar la sesion para evitar gastar memoria
			currentSubject.logout();
		}
		request.removeAttribute(Constants.USER_SESSION_FILTER_TOKEN_REQUEST_ATTRIBUTE_NAME);
	}

	private void doRecurringFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		logger.debug("Se ha detectado el token del filtro para esta request. El filtro ya ha sido inicializado.");
		filterChain.doFilter(request, response);
	}

}