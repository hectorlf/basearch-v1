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

@UrlBinding("/sitemap.xml")
public class SitemapAction implements ActionBean {

	private final static Logger logger = LoggerFactory.getLogger(SitemapAction.class);

	private CustomActionBeanContext ctx;
//	@Inject private SampleService sampleService;
	
	
	@DefaultHandler
	public Resolution execute() {
		logger.debug("Entrando a SitemapAction.execute");
		return new ForwardResolution("/WEB-INF/jsp/sitemap.jsp");
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