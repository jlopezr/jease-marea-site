<%@page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page import="net.tanesha.recaptcha.ReCaptchaResponse"%>
<%@page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@page import="jfix.util.I18N"%>
<%@page import="jfix.util.Regexps"%>
<%@page import="jease.cms.domain.User"%>
<%@page import="jease.cms.domain.Discussion"%>
<%@page import="jease.site.Templates"%>
<%@page import="jease.site.Discussions"%>
<%@page import="jease.Registry"%>
<%@page import="jease.Names"%>
<%! 
	final String JEASE_DISCUSSION_RECURSION = "Jease.Discussion.Recursion";
%>
<%
	Discussion discussion = (Discussion) request.getAttribute("Node");
	String id = String.valueOf(discussion.getPath().hashCode());
	if (session.getAttribute(discussion.getPath()) != null) {
		discussion = (Discussion) session.getAttribute(discussion.getPath());
	}

		// If the user is logged into the CMS, use his name for discussion.
	if (session.getAttribute(Names.JEASE_DISCUSSION_AUTHOR) == null 
			&& session.getAttribute(User.class.toString()) != null) {
		session.setAttribute(Names.JEASE_DISCUSSION_AUTHOR, 
				((User) session.getAttribute(User.class.toString())).getName());
	}
	
	// Is the template called recursively?
	boolean toplevel = request.getAttribute(JEASE_DISCUSSION_RECURSION) == null ;
	boolean enabled = request.getAttribute(Names.JEASE_DISCUSSION_DISABLED) == null;
	String subject = request.getParameter("subject" + id);
	String author = request.getParameter("author" + id);
	String comment = request.getParameter("comment" + id);
	String submit = request.getParameter("submit" + id);
	String message = null;

	ReCaptcha recaptcha = null;
	if (StringUtils.isNotBlank(Registry.getParameter(Names.JEASE_RECAPTCHA_PUBLIC)) && StringUtils.isNotBlank(Registry.getParameter(Names.JEASE_RECAPTCHA_PRIVATE))) {
		recaptcha = ReCaptchaFactory.newReCaptcha(Registry.getParameter(Names.JEASE_RECAPTCHA_PUBLIC), Registry.getParameter(Names.JEASE_RECAPTCHA_PRIVATE), true);
	}

	if (enabled && toplevel && submit != null) {
		ReCaptchaResponse recaptchaResponse = null;
		if (recaptcha != null && session.getAttribute(Names.JEASE_DISCUSSION_AUTHOR) == null) {
			recaptchaResponse = recaptcha.checkAnswer(request.getRemoteAddr(), request.getParameter("recaptcha_challenge_field"), request.getParameter("recaptcha_response_field"));
		}
		if (session.getAttribute(Names.JEASE_DISCUSSION_AUTHOR) != null || recaptcha == null || (recaptchaResponse != null && recaptchaResponse.isValid())) {
			message = Discussions.addComment((Discussion) request.getAttribute("Node"), author, subject, comment, true);
			session.setAttribute(Names.JEASE_DISCUSSION_AUTHOR, author);
			if (message == null) {
				subject = author = comment = null;
			}
		} else {
			message = I18N.get("Code_is_not_correct");
			session.removeAttribute(Names.JEASE_DISCUSSION_AUTHOR);
		}
	}
%>
<div class="Discussion">

<%-- Show comment --%>
<div class="Comment">
	<span class="Title">
		<% if (toplevel) { %>
			<strong><%=discussion.getTitle()%></strong>
		<% } else { %>
			<% if (Registry.getParameter(Names.JEASE_DISCUSSION_PRESENTATION, "").toLowerCase().startsWith("thread")) { %>
				<a href="<%=request.getContextPath() %><%=((Discussion) request.getAttribute("Node")).getPath()%>"><%=discussion.getTitle()%></a>
			<% } else { %>
				<strong><%=discussion.getTitle()%></strong>
			<% } %>
		<% } %>
	</span>
	<% if (StringUtils.isNotEmpty(discussion.getAuthor())) {%>
		<span class="Author"><%= I18N.get("By") %><%= " "%><%= discussion.getAuthor() %></span>
		<span class="Date">(<%=String.format("%1$td %1$tb %1$tY", discussion.getLastModified())%>)</span>
	<% } %>	
	<div class="Text"><%=Regexps.convertTextToHtml(discussion.getComment())%></div>
</div>

<%-- Create threaded view of discussion via recursion --%>
<ul class="Thread">
	<% for (Discussion child : ((Discussion) request.getAttribute("Node")).getChildren(Discussion.class)) { %>
		<% if (child.isVisible()) { %>
		<li>
		<%
			request.setAttribute(JEASE_DISCUSSION_RECURSION, true);
			request.setAttribute("Node", child);
			pageContext.include(Templates.get(child));
			request.setAttribute("Node", discussion);
			request.removeAttribute(JEASE_DISCUSSION_RECURSION);
		%>
		</li>
		<% } %>
	<% } %>
</ul>

<%-- Form with captcha to add a comment --%>
<% if (enabled && toplevel) { %>
	<a name="discussion<%= id %>"></a>
	<form class="Submission" action="#discussion<%= id %>" method="post">
		<dl>
		<dt><%= I18N.get("Name") %>:</dt>
		<dd><input type="text" name="author<%=id %>" maxlength="60" value="<%=author != null ? StringEscapeUtils.escapeXml(author) : (session.getAttribute(Names.JEASE_DISCUSSION_AUTHOR) != null ? StringEscapeUtils.escapeXml((String) session.getAttribute(Names.JEASE_DISCUSSION_AUTHOR)) : I18N.get("Anonymous")) %>"<%= author == null ? " onFocus=\"this.value=''\"" :"" %>/></dd>
		<dt><%= I18N.get("Subject") %>:</dt>
		<dd><input type="text" name="subject<%=id %>" maxlength="60" value="<%=subject != null ? StringEscapeUtils.escapeXml(subject) : "" %>"/></dd>
		<dt><%= I18N.get("Comment") %>:</dt>
		<dd><textarea name="comment<%=id %>" rows="10"><%=comment != null ? StringEscapeUtils.escapeXml(comment) : "" %></textarea></dd>
		<% if (recaptcha != null && session.getAttribute(Names.JEASE_DISCUSSION_AUTHOR) == null) { %>
			<dt><%= I18N.get("Please_enter_the_code") %>:</dt>
			<dd><%= recaptcha.createRecaptchaHtml(request.getParameter("error"), "clean", 0) %></dd>
		<% } %>
		</dl>
		<p>
		<% if (StringUtils.isNotEmpty(message)) { %>
			<b><%=message %>!</b>
		<% } else if (submit != null) { %>
			<i><%=I18N.get("Thank_you_for_your_comment") %>.</i>
		<% } %>
		<input type="submit" name="submit<%=id %>" value="<%=I18N.get("Submit") %>" />
		</p>
	</form>
<% } %>
</div>