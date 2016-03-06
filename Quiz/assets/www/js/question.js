question = {
	chapter_id : -1,
		
	load : function(id) {
		$.mobile.showPageLoadingMsg();
		$("#part_chapter").css("display", "none");
		$("#part_question").css("display", "block");
		$("#part_solution").css("display", "none");
		$("#list_question li").remove();
		$("#list_question div").remove();
		page_index = 2;
		question.chapter_id = id;
		question.refresh();
	},
		
	success : function(data) {
		jsonResult = data;
		if( jsonResult.question_list_result == "Warning" ) {
			alert("이미 응시했습니다.");
			page_index = 1;
			list.load();
		} else {
			question.initialize();
		}
	},
	
	finish_success : function(data) {
		jsonResult = data;
		if( jsonResult.insert_result == "ok" )	alert("입력되었습니다.");
		else									alert("실패했습니다.");
		page_index = 1;
		list.load();
	},
	
	refresh : function() {
		$.ajax({
			type: "GET",
			url: url_path+"/load/question",
			data: "user_id="+user_id+"&chapter_id="+question.chapter_id,
			dataType: "jsonp",
			success: question.success
		});
	},
	
	finish_refresh : function(param) {
		$.ajax({
			type: "GET",
			url: url_path+"/add/answer",
			data: "user_id="+user_id+"&chapter_id="+question.chapter_id+"&answersheet="+param,
			dataType: "jsonp",
			success: question.finish_success
		});
	},

	initialize : function() {
		$.mobile.hidePageLoadingMsg();	//	loading 완료된 경우 loading 메시지 없앰
		
		//	tag initialization 
		var textToInsert = [];
		var idx = 0;

		if( jsonResult != null ) {
			for( var i = 0 ; i < jsonResult.question_list_result.length ; i++ ) {
				if( jsonResult.question_list_result[i].type == 1 ) {		//	참 / 거짓
					textToInsert[idx++] = "<li>";
					textToInsert[idx++] = "<label for=\"question-"+jsonResult.question_list_result[i].question_id+"\">";
					textToInsert[idx++] = jsonResult.question_list_result[i].content;
					textToInsert[idx++] = "</label>";
					textToInsert[idx++] = "<select name=\"question-"+jsonResult.question_list_result[i].question_id+"\" id=\"question-"+jsonResult.question_list_result[i].question_id+"\" data-role=\"slider\">";
					textToInsert[idx++] = "<option value=\"1\">true</option>";
					textToInsert[idx++] = "<option value=\"2\">false</option>";
					textToInsert[idx++] = "</select>"; 
					textToInsert[idx++] = "</li>";
				} else {												//	객관식
					textToInsert[idx++] = "<li>";
					textToInsert[idx++] = "<fieldset data-role=\"controlgroup\" data-type=\"horizontal\">";
					textToInsert[idx++] = "<legend>";
					textToInsert[idx++] = jsonResult.question_list_result[i].content;
					textToInsert[idx++] = "</legend>";
					textToInsert[idx++] = "<input type=\"radio\" name=\"question-"+jsonResult.question_list_result[i].question_id+"\" id=\"question-"+jsonResult.question_list_result[i].question_id+"-1\"value=\"1\" checked=\"checked\"/>";
					textToInsert[idx++] = "<label for=\"question-"+jsonResult.question_list_result[i].question_id+"-1\">1</label>";
					textToInsert[idx++] = "<input type=\"radio\" name=\"question-"+jsonResult.question_list_result[i].question_id+"\" id=\"question-"+jsonResult.question_list_result[i].question_id+"-2\"value=\"2\"/>";
					textToInsert[idx++] = "<label for=\"question-"+jsonResult.question_list_result[i].question_id+"-2\">2</label>";
					textToInsert[idx++] = "<input type=\"radio\" name=\"question-"+jsonResult.question_list_result[i].question_id+"\" id=\"question-"+jsonResult.question_list_result[i].question_id+"-3\"value=\"3\"/>";
					textToInsert[idx++] = "<label for=\"question-"+jsonResult.question_list_result[i].question_id+"-3\">3</label>";
					textToInsert[idx++] = "<input type=\"radio\" name=\"question-"+jsonResult.question_list_result[i].question_id+"\" id=\"question-"+jsonResult.question_list_result[i].question_id+"-4\"value=\"4\"/>";
					textToInsert[idx++] = "<label for=\"question-"+jsonResult.question_list_result[i].question_id+"-4\">4</label>";
					textToInsert[idx++] = "<input type=\"radio\" name=\"question-"+jsonResult.question_list_result[i].question_id+"\" id=\"question-"+jsonResult.question_list_result[i].question_id+"-5\"value=\"5\"/>";
					textToInsert[idx++] = "<label for=\"question-"+jsonResult.question_list_result[i].question_id+"-5\">5</label>";
					textToInsert[idx++] = "</fieldset>";
					textToInsert[idx++] = "</li>";
				}
			}
		} else {
			alert("Error : loading fail ..");
		}

		textToInsert[idx++] = "<button type=\"submit\" data-theme=\"a\" data-corners=\"false\" id=\"btnSubmit\" onclick=\"question.finish(); return false;\">확인</button>";
		
		$("#list_question").append(textToInsert.join(""));
		$("#list_question").listview("refresh");
		$("#list_question").trigger("create");
	},
	
	finish : function() {
		var data_param = "";
		
		//	get 방식으로 넘겨줌. 형식: data_param = question_id-answer+question_id-answer+question_id-answer
		if( jsonResult != null ) {
			for( var i = 0 ; i < jsonResult.question_list_result.length ; i++ ) {
				data_param += "_";
				data_param += jsonResult.question_list_result[i].question_id + "-";
				if( jsonResult.question_list_result[i].type == 1 ) {		//	참 / 거짓
					data_param += $("#question-"+jsonResult.question_list_result[i].question_id).val();
				} else {													//	객관식
					var max = document.getElementsByName("question-"+jsonResult.question_list_result[i].question_id).length;
					for( var j = 1 ; j <= max ; j++ ) {
						if( document.getElementsByName("question-"+jsonResult.question_list_result[i].question_id)[j-1].checked ) data_param += j;
					}
				}
			}
		}
		data_param = data_param.substring(1, data_param.length);
		question.finish_refresh(data_param);
	}
/*	
	jsonResult = {
		"question_list_result":
		[
		 	{
		 		"chapter_id":1,
		 		"question_id":9,
		 		"content":"객관식 문제입니다. 가장 참한 인상은 누구일까요? 1.신경모  2.박수환  3.김선우  4.나형선  5.김예슬",
		 		"point":9,
		 		"type":0		//	객관식
		 	}
		]
	};
*/
};