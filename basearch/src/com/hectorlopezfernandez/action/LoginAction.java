package com.hectorlopezfernandez.action;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hectorlopezfernandez.integration.CustomActionBeanContext;

@UrlBinding("/login.action")
public class LoginAction implements ActionBean {

	private final static Logger logger = LoggerFactory.getLogger(LoginAction.class);

	private CustomActionBeanContext ctx;
	
	@DefaultHandler
	public Resolution execute() {
		logger.debug("Entrando a LoginAction.execute");
		return new ForwardResolution("WEB-INF/jsp/login.jsp");
	}
	
	// Getters y setters

	@Override
	public CustomActionBeanContext getContext() {
		return ctx;
	}
	@Override
	public void setContext(ActionBeanContext ctx) {
		this.ctx = (CustomActionBeanContext)ctx;
	}

}