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
	<script type="text/javascript" src="../javascript/form.js"></script>
	<script type="text/javascript" src="../javascript/common.js"></script>
	<script type="text/javascript" src="../javascript/chapter.js"></script>
</head>
<body>

<section id="wrap">

<%@include file="../header.jsp"%>

<section id="content">
	<div id="chapter_add">
		<form action="./chapterInsertPrc.jsp" method="post">
		<fieldset>
			<legend>Insert Chapter</legend>
			<div class="form_table">
				<table summary="단원 입력">
	        	<tbody>
		        	<tr>
				        <th scope="row">제목</th>
				        <td>
				            <div class="item">
				            	<label for="title" class="i_label" style="position:absolute; visibility:visible; ">소프트웨어공학 이론</label>
                				<input type="text" name="title" id="title" class="i_text" style="width:98%">
				            </div>
				        </td>
	        		</tr>
	        		<tr>
	        			<th scope="row">부제목</th>
				        <td>
				            <div class="item">
				                <label for="subtitle" class="i_label" style="position:absolute; visibility:visible; ">디자인 패턴</label>
                				<input type="text" name="subtitle" id="subtitle" class="i_text" style="width:98%">
				            </div>
				        </td>
	        		</tr>
	        		<tr>
	        			<th scope="row">버튼</th>
				        <td>
				        	<span class="btn_pack small icon"><span class="add"></span><input type="submit" id="add_btn" value="추가" onclick="goInsert()"></span>
				        </td>
	        		</tr>
	        	</tbody>
	        	</table>
	        </div>
	    </fieldset>
		</form>		
	</div>

	<table class="chapter_list">
	<caption>Chapter List</caption>
	<thead>
		<tr>
			<th class="chapter_id">ID</th>
			<th class="chapter_title">제목</th>
			<th class="chapter_subtitle">부제목</th>
			<th class="chapter_state">상태</th>
			<th class="btns"></th>
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
			case 4:	strState = "완전 종료";	break;
			default: strState = "상태 오류";
		}
%>
	<tr class="chapter_tr">
		<td class="chapter_id "><%=chapterEty.getId()%></td>
		<td class="chapter_title"><%=chapterEty.getTitle()%></td>
		<td class="chapter_subtitle"><%=chapterEty.getSub_title()%></td>
		<td class="chapter_state"><%=strState%></td>
		<td class="btns">
			<span class="btn_pack small icon"><span class="calendar"></span><input type="button" value="수정" onclick="showUpdate(<%=chapterEty.getId()%>, '<%=chapterEty.getTitle()%>', '<%=chapterEty.getSub_title()%>')"/></span>
			<span class="btn_pack small icon"><span class="delete"></span><input type="button" value="삭제" onclick="goDelete(<%=chapterEty.getId()%>)"/></span>
		</td>
	</tr>
	<tr id="chapter_update_<%=chapterEty.getId()%>"></tr>
<%	}	%>
	</tbody>
	</table>
</section>

<%@include file="../footer.jsp"%>

</section>

</body>
</html>