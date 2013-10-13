<%@include file="common-init-values.jsp"%><%@page contentType="text/xml"%><%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%><%@taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%><?xml version="1.0" encoding="UTF-8"?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9"><c:set var="hostname">http://${alias.name}</c:set>
	<url><stripes:url beanclass="com.hectorlopezfernandez.action.IndexAction" var="indexUrl"></stripes:url>
		<loc>${hostname}${indexUrl}</loc>
		<changefreq>daily</changefreq>
	</url>
	<url>
		<loc>${hostname}${pageUrl}</loc>
		<lastmod><joda:format value="${page.lastModificationDate}" pattern="yyyy-MM-dd"/></lastmod>
		<changefreq>monthly</changefreq>
	</url>
</urlset>