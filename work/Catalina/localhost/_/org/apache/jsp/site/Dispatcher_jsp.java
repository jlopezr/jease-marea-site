/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.39
 * Generated at: 2013-06-16 02:53:30 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.site;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.Collection;
import java.util.HashSet;
import jfix.servlet.Cookies;
import jease.Registry;
import jease.Names;
import jease.cms.domain.Access;
import jease.cms.domain.Content;
import jease.cms.domain.User;
import jease.site.Authorizations;
import jease.site.Templates;

public final class Dispatcher_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;


	// The current node is stored in request-attribute by JeaseServletFilter.
	Content node = (Content) request.getAttribute("Node");
	
	// Save original node as stable context, because "Node" is exchanged
	// in some templates on the fly (e.g. Folder, Reference, Composite).
	if (request.getAttribute("Context") == null) {
		request.setAttribute("Context", node);
	}
		
	// If an Access-Object is guarding the node, use it to force authorization.
	String authorization = request.getHeader("Authorization");
	Access guard = Authorizations.getGuard(node);
	if (guard != null) {
		if (Authorizations.isPermitted(authorization, guard)) {
			if (session.getAttribute(Names.JEASE_SITE_AUTHORIZATIONS) == null) {
				session.setAttribute(Names.JEASE_SITE_AUTHORIZATIONS, new HashSet());
			}
			((Collection) session.getAttribute(Names.JEASE_SITE_AUTHORIZATIONS)).add(guard);
		} else {
			// If current user can view content in the CMS, skip authorization.
			User user = (User) session.getAttribute(User.class.toString());
			if (user == null || !guard.isDescendant(user.getRoots())) { 
				request.setAttribute("Node", node = guard);
			}
		}
	}

	// Which template should be used to render the node?
	String pageTemplate = Templates.get(node);
	
	// If node is page-like content (e.g. text) and no file-parameter exists in request,
	// then include template, otherwise forward (e.g. to stream binary content).
	if (node.isPage() && request.getParameter("file") == null) {
		String template = request.getParameter("page");
		if (template != null && !template.startsWith("/WEB-INF") && !template.endsWith("Page.jsp") 
				&& !template.equals((String) request.getAttribute(Names.JEASE_SITE_DISPATCHER))) {
			pageTemplate = template;
		}
		request.setAttribute("Page.Template", pageTemplate);
		String design = Registry.getParameter(Names.JEASE_SITE_DESIGN);
		if (design != null) {
			if (design.startsWith("/")) {
				pageContext.forward(design);
			} else {
				// Check if user design is requested via cookie.
				String userDesign = Cookies.pick(request, response, "design", null);
				if (userDesign != null && !userDesign.contains("/")) {
					String userDesignPath = String.format("/site/web/%s/Page.jsp", userDesign);
					String userDesignRealPath = application.getRealPath(userDesignPath);
					if (userDesignRealPath != null && new java.io.File(userDesignRealPath).exists()) {
						pageContext.forward(userDesignPath);
						return;
					}
				}
				pageContext.forward(String.format("/site/web/%s/Page.jsp", design));
			}
		}
	} else {
		pageContext.forward(pageTemplate);
	}

    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
