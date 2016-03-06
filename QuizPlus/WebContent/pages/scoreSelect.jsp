<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="control.*"%>
<%@page import="ety.*"%>
<%
	Control control = new Control();
	Vector <ChapterEty> chapterList = control.getChapterList();
%>
<!DOCTYPE HTML>
<html>
<head>
	<title>내가 퀴즈를 알아</title>
	<link rel="stylesheet" href="../css/form.css" type="text/css"/>
	<link rel="stylesheet" href="../css/button.css" type="text/css"/>
	<link rel="stylesheet" href="../css/common.css" type="text/css"/>
	<link rel="stylesheet" href="../css/chapter.css" type="text/css"/>
	<link rel="stylesheet" href="../css/score.css" type="text/css"/>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="../javascript/chapter.js"></script>
	<script type="text/javascript" src="../javascript/form.js"></script>
	<script type="text/javascript" src="../javascript/common.js"></script>
	<script type="text/javascript" src="../javascript/score.js"></script>
</head>
<body>

<section id="wrap">

<%@include file="../header.jsp"%>

<section id="content">
	<table class="chapter_list">
	<caption>Open Chapter List</caption>
	<thead>
		<tr>
			<th class="chapter_id">ID</th>
			<th class="chapter_title">제목</th>
			<th class="chapter_subtitle">부제목</th>
			<th class="chapter_state">총인원</th>
		</tr>
	</thead>
	<tbody>
<%
	for( int i = 0 ; i < chapterList.size() ; i++ ) {
		ChapterEty chapterEty = chapterList.get(i);
		if( chapterEty.getState() >= 3) {
			Vector <ScoreEty> scoreList = control.getScoreList(chapterEty.getId());
			
%>
	<tr class="chapter_tr" onclick="goScoreList('<%=chapterEty.getId()%>')">
		<td class="chapter_id "><%=chapterEty.getId()%></td>
		<td class="chapter_title"><%=chapterEty.getTitle()%></td>
		<td class="chapter_subtitle"><%=chapterEty.getSub_title()%></td>
		<td class="chapter_state"><%=scoreList.size()%> 명</td>
	</tr>
<%	}	}	%>
	</tbody>
	</table>
	
	<p><div id="score_div"></div>
	
</section>

<%@include file="../footer.jsp"%>

</section>

</body>
</html>