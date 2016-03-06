<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.io.*" %>
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<%@page import="ety.*" %>
<%@page import="control.*" %>

<%
	Control control = new Control();
	ChapterEty chapterEty = new ChapterEty();
	
	boolean isOk = false;
	
	String title = request.getParameter("title");
	String subtitle = request.getParameter("subtitle");
	
	title = StringConv.getFixedString(title);
	subtitle = StringConv.getFixedString(subtitle);
	
	if( title.equals("") || subtitle.equals("") ) isOk = false;
	else {
		chapterEty.setTitle(title);
		chapterEty.setSub_title(subtitle);

    	isOk = control.insertChapter(chapterEty);
	}
	if( isOk ) {
%>
<script type="text/javascript">
	alert("추가되었습니다.");
	location.href="./chapterList.jsp";
</script>
<%	} else {	%>
<script type="text/javascript">
	alert("추가 실패! 제목과 부제목을 확인해주세요..");
	history.back();
</script>
<%	}			%>
