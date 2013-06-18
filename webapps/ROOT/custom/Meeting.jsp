<%@page import="custom.Meeting"%>
<%
 Meeting meeting =
   (Meeting) request.getAttribute("Node");
%>
<h1><%=meeting.getTitle()%></h1>
<table>
<tr>
 <td><b>Where:</b></td>
 <td><%=meeting.getLocation()%></td>
</tr>
<tr>
 <td><b>Start:</b></td>
 <td><%=String.format("%1$tF %1$tR", 
                  meeting.getStart())%></td>
</tr>
<tr>
 <td><b>Stop:</b></td>
 <td><%=String.format("%1$tF %1$tR",
                  meeting.getStop())%></td>
</tr>
</table>
<div><%=meeting.getTopic()%></div>
