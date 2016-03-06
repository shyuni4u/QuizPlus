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
<%	}	%>
	<link rel="stylesheet" href="../css/form.css" type="text/css"/>
	<link rel="stylesheet" href="../css/button.css" type="text/css"/>
	<link rel="stylesheet" href="../css/common.css" type="text/css"/>
	<link rel="stylesheet" href="../css/question.css" type="text/css"/>
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="../javascript/form.js"></script>
	<script type="text/javascript" src="../javascript/common.js"></script>
	<script type="text/javascript" src="../javascript/question.js"></script>
	<script type="text/javascript" src="se/js/HuskyEZCreator.js" charset="utf-8"></script>
</head>
<body>

<section id="wrap">

<%@include file="../header.jsp"%> 
<section id="content">
	<form id="question_insert_form" action="./questionInsertPrc.jsp" method="post" enctype="multipart/form-data">
		<fieldset>
			<legend>Insert Question : SmartEditor Basic LPGL V2</legend>
			<div class="form_table">
				<table summary="문제 입력">
	        	<tbody id="question_insert">
		        	<tr>
				        <th scope="row">유형</th>
				        <td>
				            <div class="item">
				            	<input name="chapter_id" type="hidden" value="<%=chapter_id%>"/>
				            	<input name="_type" type="radio" value="0" id="type1" class="i_radio" checked="checked" onclick="selectType(1);"><label for="type1">객관식</label>
				            	<input name="_type" type="radio" value="1" id="type2" class="i_radio" onclick="selectType(2);"><label for="type2">참/거짓</label>
				            </div>
				        </td>
				        <th scope="row">정답</th>
				        <td>
				            <div class="item">
								<input name="answer" type="radio" value="1" id="value1" class="i_radio" checked="checked"><label for="value1">1&nbsp;&nbsp;&nbsp;&nbsp;</label>
								<input name="answer" type="radio" value="2" id="value2" class="i_radio"><label for="value2">2&nbsp;&nbsp;&nbsp;&nbsp;</label>
								<input name="answer" type="radio" value="3" id="value3" class="i_radio"><label id = "value3_for" for="value3">3&nbsp;&nbsp;&nbsp;&nbsp;</label>
								<input name="answer" type="radio" value="4" id="value4" class="i_radio"><label id = "value4_for" for="value4">4&nbsp;&nbsp;&nbsp;&nbsp;</label>
								<input name="answer" type="radio" value="5" id="value5" class="i_radio"><label id = "value5_for" for="value5">5&nbsp;&nbsp;&nbsp;&nbsp;</label>
				            </div>
				        </td>
				        <th scope="row">배점</th>
				        <td>
				            <div class="item">
								<select name="point">
				                    <option value="1">1</option>
				                    <option value="2">2</option>
				                    <option value="3">3</option>
				                    <option value="4">4</option>
				                    <option value="5">5</option>
				                    <option value="6">6</option>
				                    <option value="7">7</option>
				                    <option value="8">8</option>
				                    <option value="9">9</option>
				                    <option value="10">10</option>
				                </select>
				            </div>
				        </td>
	        		</tr>
		        	<tr>
				        <th scope="row">내용</th>
				        <td colspan="5">
				            <div class="item">
				                <label for="temp_textarea" class="i_label" style="position:absolute; visibility:visible; "></label>
				                <textarea name="content" id="question_content" style="width:97%; height:250px"></textarea>
				            </div>
				        </td>
	        		</tr>
		        	<tr>
				        <th scope="row">해설</th>
				        <td colspan="5">
				            <div class="item">
				                <label for="temp_textarea" class="i_label" style="position:absolute; visibility:visible; "></label>
				                <textarea name="solution" id="question_solution" style="width:97%; height:250px"></textarea>
				            </div>
				        </td>
	        		</tr>
			        <tr>	
						<td class="btns" colspan="6" style="text-align:right">
							<span class="btn_pack small icon"><span class="check"></span><input type="button" value="확인" onclick="goSubmit(this)"/></span>
						</td>
			        </tr>
				</tbody>
				</table>
			</div>
		</fieldset>
	</form>
</section>
<%@include file="../footer.jsp"%>

</section>

<script>
	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "question_content",
		sSkinURI: "se/SEditorSkin.html",
		fCreator: "createSEditorInIFrame"
	});
	
	nhn.husky.EZCreator.createInIFrame({
		oAppRef: oEditors,
		elPlaceHolder: "question_solution",
		sSkinURI: "se/SEditorSkin.html",
		fCreator: "createSEditorInIFrame"
	});
</script>

</body>
</html>