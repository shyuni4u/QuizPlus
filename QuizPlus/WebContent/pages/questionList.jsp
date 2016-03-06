<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="control.*"%>
<%@page import="ety.*"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>내가 퀴즈를 알아</title>
<%
	int chapter_id = 0;
	try {
		chapter_id = Integer.parseInt(request.getParameter("id"));
	} catch(NumberFormatException e) {
%>
	<script type="text/javascript">
		alert("잘못된 접근입니다.");
		history.back();
	</script>
<%	}
		Control control = new Control();
		Vector <QuestionEty> questionList = control.getQuestionList(chapter_id);
%>
	<link rel="stylesheet" href="../css/form.css" type="text/css"/>
	<link rel="stylesheet" href="../css/button.css" type="text/css"/>
	<link rel="stylesheet" href="../css/common.css" type="text/css"/>
	<link rel="stylesheet" href="../css/question.css" type="text/css"/>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="../javascript/form.js"></script>
	<script type="text/javascript" src="../javascript/common.js"></script>
	<script type="text/javascript" src="../javascript/question.js"></script>
</head>
<body>

<section id="wrap">

<%@include file="../header.jsp"%> 
<section id="content">
	<table class="question_list">
	<caption>[ <%=chapter_id%> ] Question List</caption>
	<thead>
		<tr>
			<th class="question_id">ID</th>
			<th class="question_type">유형</th>
			<th class="question_content">문제</th>
			<th class="question_score">배점</th>
			<th class="question_btns">
				<span class="btn_pack small icon"><span class="add"></span><input type="submit" id="add_btn" value="추가" onclick="goQuestionInsert(<%=chapter_id%>);"></span>
			</th>
		</tr>
	</thead>
	<tbody>
<%
	for( int i = 0 ; i < questionList.size() ; i++ ) {
		QuestionEty questionEty = questionList.get(i);
%>
	<tr class="question_tr">
		<td class="question_id "><%=questionEty.getQuestion_id()%></td>
		<td class="question_type"><%=(questionEty.getType() == 0) ? "객관식" : "참/거짓"%></td>
		<td class="question_content"><div id="question_content_fot_dot"><%=questionEty.getContent()%></div></td>
		<td class="question_score"><%=questionEty.getPoint()%></td>
		<td class="question_btns">
			<span class="btn_pack small icon"><span class="calendar"></span><input type="button" value="수정" onclick="showUpdate(<%=questionEty.getQuestion_id()%>, <%=chapter_id%>)"/></span>
			<span class="btn_pack small icon"><span class="delete"></span><input type="button" value="삭제" onclick="goDelete(<%=questionEty.getQuestion_id()%>, <%=chapter_id%>)"/></span>
		</td>
	</tr>
<%	}	%>
	</tbody>
	</table>
</section>
<%@include file="../footer.jsp"%>

</section>

</body>
</html>