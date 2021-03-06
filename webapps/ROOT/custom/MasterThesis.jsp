<%@page import="jfix.servlet.*,jfix.util.*,custom.*"%>
<%
 MasterThesis masterThesis =
   (MasterThesis) request.getAttribute("Node");
   
    if (session.getAttribute(masterThesis.getPath()) != null) {
        masterThesis = (MasterThesis) session.getAttribute(masterThesis.getPath());
    }
    if (request.getParameter("file") != null) {
         if (request.getParameter("scale") != null) {
                int scale = Integer.parseInt(request.getParameter("scale"));
                Servlets.write(Images.scale(masterThesis.getAuthorPhoto(), scale), "image/jpeg", response);
        } else {
                Servlets.write(masterThesis.getAuthorPhoto(), "image/jpeg", response);
        }
        return;
    }
%>
<div>
  <h1><%=masterThesis.getTitle()%></h1>
  <div>
    <p style="text-align: justify;">
      <% if(masterThesis.getAuthorPhoto().exists()) { %>
        <img src="<%=request.getContextPath()%><%=masterThesis.getPath()%>?file&scale=100" alt="<%=masterThesis.getTitle()%>" title="<%=masterThesis.getTitle()%>" width="100" height="100" align="right" style="margin:0 0 10px 10px;"/>
        <% } %>    
    <strong>Author:</strong> <a href="<%=masterThesis.getAuthorUrl()%>"><strong> <%=masterThesis.getAuthor()%> </strong></a><br>    
    <% if(!masterThesis.getBio().equals("")) { %>
      <strong>Bio:</strong> <%=masterThesis.getBio()%>
    <% } %>
    </p>  
  </div>
  <h2>Summary</h2>
  <p style="text-align: justify;">
    <a href="<%=masterThesis.getSmallPhoto()%>">
      <img src="<%=masterThesis.getSmallPhoto()%>" width="125" align="left" style="margin:0 10px 0 0;" alt="<%=MasterThesis.getTitleByPath(masterThesis.getSmallPhoto())%>"/>
    </a>
    <%=masterThesis.getDescription()%>
  </p>
  
  <div align="middle">
    <a href="<%=masterThesis.getBigPhoto()%>">
      <img src="<%=masterThesis.getBigPhoto()%>" width="350" alt="<%=MasterThesis.getTitleByPath(masterThesis.getBigPhoto())%>">
    </a>
    <p>Fig. 1 – <%=MasterThesis.getTitleByPath(masterThesis.getBigPhoto())%></p>
  </div>
  
  <p style="text-align: justify;">
    <%=masterThesis.getTopic()%>
  </p>
  <h2>Want to know more?</h2>
  <div>
    <% if(!masterThesis.getDirector().equals("")) { %>
    <strong>Directed by:</strong> <a href="#" title="director"><strong> <%=masterThesis.getDirector()%> </strong></a><br>
    <% } %>
      
    <% if(masterThesis.getQualification()!=0) { %>
    <strong>Qualification:</strong> <a href="#" title="Qualification"><strong> <%=masterThesis.getQualification()%> </strong></a><br>
    <% } %>
      
    <% if(!masterThesis.getUrl().equals("")) { %>  
    <strong>Read more at:</strong> <a href="<%=masterThesis.getUrl()%>" title="<%=MasterThesis.getTitleByPath(masterThesis.getUrl())%>"><strong><%=MasterThesis.getTitleByPath(masterThesis.getUrl())%></strong></a><br>
    <% } %>
      
    <% if(!masterThesis.getDownloadUrl().equals("")) { %>
    <strong>Download at:</strong> <a href="<%=masterThesis.getDownloadUrl()%>" title="<%=MasterThesis.getTitleByPath(masterThesis.getDownloadUrl())%>"><strong> <%=MasterThesis.getTitleByPath(masterThesis.getDownloadUrl())%> </strong></a>
    <% } %>
    </div>
  </div>
