<%@page import="custom.MasterThesis"%>
<%
 MasterThesis masterThesis =
   (MasterThesis) request.getAttribute("Node");
%>
<h1><%=masterThesis.getTitle()%></h1>
<table>
<tr>
 <td><b>Author:</b></td>
 <td><%=masterThesis.getAuthor()%></td>
</tr>
<tr>
 <td><b>Bio:</b></td> 
 <td><%=masterThesis.getBio()%></td>
</tr>
<tr>
 <td><b>Director:</b></td>
 <td><%=masterThesis.getDirector()%></td>
</tr>
<tr>
 <td><b>Description:</b></td> 
 <td><%=masterThesis.getDescription()%></td>
</tr>
<tr>
 <td><b>Summary:</b></td> 
 <td><%=masterThesis.getTopic()%></td>
</tr>
<tr>
 <td><b>Qualification:</b></td>
 <td><%=masterThesis.getQualification()%></td>
</tr>
</table>
