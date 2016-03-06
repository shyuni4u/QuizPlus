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
		Vector <ScoreEty> scoreList = control.getScoreList(chapter_id);
%>
	<link rel="stylesheet" href="../css/form.css" type="text/css"/>
	<link rel="stylesheet" href="../css/button.css" type="text/css"/>
	<link rel="stylesheet" href="../css/common.css" type="text/css"/>
	<link rel="stylesheet" href="../css/score.css" type="text/css"/>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="../javascript/form.js"></script>
	<script type="text/javascript" src="../javascript/common.js"></script>
	<script type="text/javascript" src="../javascript/score.js"></script>
</head>
<body>


<section id="content">
	<table class="score_list">
	<caption>Score List</caption>
	<thead>
		<tr>
			<th class="score_id">ID</th>
			<th class="score_sid">학번</th>
			<th class="score_point">점수</th>
			<th class="score_rate">등수</th>
		</tr>
	</thead>
	<tbody>
<%
	for( int i = 0 ; i < scoreList.size() ; i++ ) {
		ScoreEty scoreEty = scoreList.get(i);
%>
	<tr class="score_tr">
		<td class="score_id "><%=scoreEty.getChapter_id()%></td>
		<td class="score_sid"><%=scoreEty.getStudent_id()%></td>
		<td class="score_point"><%=scoreEty.getScore()%></td>
		<td class="score_rate"><%=i+1%></td>
	</tr>
<%	}  %>
	</tbody>
	</table>
</section>


</body>
</html>