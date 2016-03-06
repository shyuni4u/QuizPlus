<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@page import="control.*"%>
<%@page import="ety.*"%>
<%
	Control control = new Control();
	Vector <ChapterEty> chapterList = control.getChapterListAVG();
	Vector <ChapterEty> chapterScore = control.getChapterHighLowScore();
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
	<script type="text/javascript" src="../javascript/form.js"></script>
	<script type="text/javascript" src="../javascript/common.js"></script>
</head>
<body>

<section id="wrap">

<%@include file="../header.jsp"%>

<section id="content">
	<table class="chapter_list">
	<caption>Statistics List</caption>
	<thead>
		<tr>
			<th class="chapter_id">ID</th>
			<th class="chapter_title">제목</th>
			<th class="chapter_subtitle">부제목</th>
			<th class="chapter_personnel">총인원</th>
			<th class="chapter_state">평균</th>
			<th class="chapter_lowScore">최저점</th>
			<th class="chapter_highScore">최고점</th>
		</tr>
	</thead>
	<tbody>
<%
	for( int i = 0 ; i < chapterList.size() ; i++ ) {
		ChapterEty chapterEty = chapterList.get(i);
		ChapterEty chapterEty2 = chapterScore.get(i);
		Vector <ScoreEty> scoreList = control.getScoreList(chapterEty.getId());
%>
	<tr class="chapter_tr">
		<td class="chapter_id "><%=chapterEty.getId()%></td>
		<td class="chapter_title"><%=chapterEty.getTitle()%></td>
		<td class="chapter_subtitle"><%=chapterEty.getSub_title()%></td>
		<td class="chapter_personnel"><%=scoreList.size()%> 명</td>
		<td class="chapter_avgScore"><%=chapterEty.getAvg_score()%></td>
		<td class="chapter_lowScore"><%=chapterEty2.getLow_score()%></td>
		<td class="chapter_highScore"><%=chapterEty2.getHigh_score()%></td>
	</tr>
<%	}	%>
	</tbody>
	</table>
</section>

<%@include file="../footer.jsp"%>

</section>

</body>
</html>