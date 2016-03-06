<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="control.*"%>
<%@page import="ety.*"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>내가 퀴즈를 알아</title>
<%
	Control control = new Control();
	Vector <ChapterEty> chapterList =  control.getChapterList();
%>
	<link rel="stylesheet" href="../css/progress.css" type="text/css"/>
	<link rel="stylesheet" href="../css/button.css" type="text/css"/>
	<link rel="stylesheet" href="../css/common.css" type="text/css"/>
	<link rel="stylesheet" href="../css/state.css" type="text/css"/>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="../javascript/form.js"></script>
	<script type="text/javascript" src="../javascript/common.js"></script>
	<script type="text/javascript" src="../javascript/state.js"></script>
</head>
<body>

<section id="wrap">

<%@include file="../header.jsp"%> 
<section id="content">
	<ul id="state_list">
<%
	for( int i = 0; i < chapterList.size(); i++ ) {
		ChapterEty chapterEty = chapterList.get(i);
%>
		<li>
		<div class="progress">
		    <strong class="tit"><%=chapterEty.getTitle()%> [<%=chapterEty.getSub_title()%>]</strong>
		    <ol>
			    <li <%=(chapterEty.getState() >= 0) ? "class=\"on\"" : ""%> onclick="changeState(0, <%=chapterEty.getId()%>)"><span>챕터 등록</span></li>
			    <li <%=(chapterEty.getState() >= 1) ? "class=\"on\"" : ""%> onclick="changeState(1, <%=chapterEty.getId()%>)"><span>시험 개시</span></li>
			    <li <%=(chapterEty.getState() >= 2) ? "class=\"on\"" : ""%> onclick="changeState(2, <%=chapterEty.getId()%>)"><span>시험 종료</span></li>
			    <li <%=(chapterEty.getState() >= 3) ? "class=\"on\"" : ""%> onclick="changeState(3, <%=chapterEty.getId()%>)"><span>점수 공개</span></li>
			    <li <%=(chapterEty.getState() >= 4) ? "class=\"on\"" : ""%> onclick="changeState(4, <%=chapterEty.getId()%>)"><span>완전 종료</span></li>
		    </ol>
		</div>
		</li>
<%	}	%>
	</ul>
</section>
<%@include file="../footer.jsp"%>

</section>

</body>
</html>