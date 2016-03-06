<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.io.*" %>
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<%@page import="ety.*" %>
<%@page import="control.*" %>

<%
	Control control = new Control();

	boolean isOk = false;

	int state = Integer.parseInt(request.getParameter("state"));
	int chapter_id = Integer.parseInt(request.getParameter("chapter_id"));
	
    isOk = control.changeState(state, chapter_id);
   
	if( isOk ) {
		if( state == 3 ) {
			 control.insertScore(chapter_id);
		}
%>
<script type="text/javascript">
	alert("수정되었습니다.");
	location.href="./stateList.jsp";
</script>
<%	} else {	%>
<script type="text/javascript">
	alert("실패했습니다.");
	history.back();
</script>
<%	}			%>
