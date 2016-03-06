<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.io.*" %>
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<%@page import="ety.*" %>
<%@page import="control.*" %>

<%
	Control control = new Control();
	
	boolean isOk = false;
	
	int id = Integer.parseInt(request.getParameter("id"));

    isOk = control.deleteChapter(id);
	
	if( isOk ) {
%>
<script type="text/javascript">
	alert("삭제되었습니다.");
	location.href="./chapterList.jsp";
</script>
<%	} else {	%>
<script type="text/javascript">
	alert("실패했습니다.");
	history.back();
</script>
<%	}			%>
