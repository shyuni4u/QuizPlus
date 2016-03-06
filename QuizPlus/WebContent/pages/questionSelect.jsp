<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="control.*"%>
<%@page import="ety.*"%>
<%
	Control control = new Control();
	Vector <ChapterEty> chapterList = control.getChapterList();
	String strState = "";
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>내가 퀴즈를 알아</title>
	<link rel="stylesheet" href="../css/form.css" type="text/css"/>
	<link rel="stylesheet" href="../css/button.css" type="text/css"/>
	<link rel="stylesheet" href="../css/common.css" type="text/css"/>
	<link rel="stylesheet" href="../css/chapter.css" type="text/css"/>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="../javascript/chapter.js"></script>
	<script type="text/javascript" src="../javascript/form.js"></script>
	<script type="text/javascript" src="../javascript/common.js"></script>
</head>
<body>

<section id="wrap">

<%@include file="../header.jsp"%>

<section id="content">
	<table class="chapter_list">
	<caption>Chapter List</caption>
	<thead>
		<tr>
			<th class="chapter_id">ID</th>
			<th class="chapter_title">제목</th>
			<th class="chapter_subtitle">부제목</th>
			<th class="chapter_state">상태</th>
		</tr>
	</thead>
	<tbody>
<%
	for( int i = 0 ; i < chapterList.size() ; i++ ) {
		ChapterEty chapterEty = chapterList.get(i);
		switch( chapterEty.getState() ) {
		case 0:	strState = "챕터 등록";	break;
		case 1:	strState = "시험 개시";	break;
		case 2:	strState = "시험 종료";	break;
		case 3:	strState = "점수 공개";	break;
		case 4: strState = "완전 종료"; break;
		default: strState = "상태 오류";
	}
%>
	<tr class="chapter_tr" onclick="goQuestionList('<%=chapterEty.getId()%>')">
		<td class="chapter_id "><%=chapterEty.getId()%></td>
		<td class="chapter_title"><%=chapterEty.getTitle()%></td>
		<td class="chapter_subtitle"><%=chapterEty.getSub_title()%></td>
		<td class="chapter_state"><%=strState%></td>
	</tr>
<%	}	%>
	</tbody>
	</table>
</section>

<%@include file="../footer.jsp"%>

</section>

</body>
</html>