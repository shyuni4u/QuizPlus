<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.io.*" %>
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<%@page import="ety.*" %>
<%@page import="control.*" %>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.Base64Encoder"%>
<%@page import="org.apache.commons.io.FileUtils" %>

<%
	Control control = new Control();
	QuestionEty questionEty = new QuestionEty();
	
	String savedPath = "/usr/share/tomcat6/webapps/QuizData/";
	//String savedPath = "D://";
	
	boolean isOk = false;
	
	//	multi 객체를 생성함과 동시에 업로드 끝
	MultipartRequest multi = new MultipartRequest(request, savedPath, 10 * 1024 * 1024, "utf-8", new DefaultFileRenamePolicy());

	int chapter_id = Integer.parseInt(multi.getParameter("chapter_id"));
	int type = Integer.parseInt(multi.getParameter("_type"));
	int answer = Integer.parseInt(multi.getParameter("answer"));
	int point = Integer.parseInt(multi.getParameter("point"));
	String content = multi.getParameter("content");
	String solution = multi.getParameter("solution");
	
	questionEty.setChapter_id(chapter_id);
	questionEty.setType(type);
	questionEty.setAnswer(answer);
	questionEty.setPoint(point);
	questionEty.setContent(content);
	questionEty.setSolution(solution);
	
	isOk = control.insertQuestion(questionEty);
	if( isOk ) {
%>
<script type="text/javascript">
	alert("추가되었습니다.");
	location.href="./questionList.jsp?id="+<%=chapter_id%>;
</script>
<%	} else {	%>
<script type="text/javascript">
	alert("입력에 실패했습니다.");
	history.back();
</script>
<%	}			%>