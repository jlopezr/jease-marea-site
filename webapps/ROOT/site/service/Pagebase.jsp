<%@page import="jfix.servlet.Servlets"%>
<%@page import="jease.cms.domain.Content"%>
<%@page import="jease.site.Navigations"%>
<base href="<%=Servlets.getContextURL(request) %><%=Navigations.getBasePath((Content) request.getAttribute("Node")) %>" />