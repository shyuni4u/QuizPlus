solution = {
	chapter_id : -1,
		
	load : function(id) {
		$.mobile.showPageLoadingMsg();
		$("#part_chapter").css("display", "none");
		$("#part_question").css("display", "none");
		$("#part_solution").css("display", "block");
		$("#list_solution li").remove();
		page_index = 2;
		solution.chapter_id = id;
		solution.refresh();
	},
		
	success : function(data) {
		jsonResult = data;
		solution.initialize();
	},
	
	refresh : function() {
		$.ajax({
			type: "GET",
			url: url_path+"/load/solution",
			data: "user_id="+user_id+"&chapter_id="+solution.chapter_id,
			dataType: "jsonp",
			success: solution.success
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
					textToInsert[idx++] = "<select name=\"question-"+jsonResult.question_list_result[i].question_id+"\" id=\"question-"+jsonResult.question_list_result[i].question_id+"\" data-role=\"slider\" disabled=\"disabled\">";
					textToInsert[idx++] = "<option value=\"1\" "+ ( ( jsonResult.question_list_result[i].answer == 2 ) ? "selected=\"selected\"" :  "" ) +">true</option>";
					textToInsert[idx++] = "<option value=\"2\" "+ ( ( jsonResult.question_list_result[i].answer == 2 ) ? "selected=\"selected\"" :  "" ) +">false</option>";
					textToInsert[idx++] = "</select>"; 
					textToInsert[idx++] = "</li>";
				} else {												//	객관식
					textToInsert[idx++] = "<li>";
					textToInsert[idx++] = "<fieldset data-role=\"controlgroup\" data-type=\"horizontal\">";
					textToInsert[idx++] = "<legend>";
					textToInsert[idx++] = jsonResult.question_list_result[i].content;
					textToInsert[idx++] = "</legend>";
					textToInsert[idx++] = "<input type=\"radio\" name=\"question-"+jsonResult.question_list_result[i].question_id+"\" id=\"question-"+jsonResult.question_list_result[i].question_id+"-1\"value=\"1\" disabled=\"disabled\" "+ ( ( jsonResult.question_list_result[i].answer == 1 ) ? "checked=\"checked\"" :  "" ) +"/>";
					textToInsert[idx++] = "<label for=\"question-"+jsonResult.question_list_result[i].question_id+"-1\">1</label>";
					textToInsert[idx++] = "<input type=\"radio\" name=\"question-"+jsonResult.question_list_result[i].question_id+"\" id=\"question-"+jsonResult.question_list_result[i].question_id+"-2\"value=\"2\" disabled=\"disabled\" "+ ( ( jsonResult.question_list_result[i].answer == 2 ) ? "checked=\"checked\"" :  "" ) +"/>";
					textToInsert[idx++] = "<label for=\"question-"+jsonResult.question_list_result[i].question_id+"-2\">2</label>";
					textToInsert[idx++] = "<input type=\"radio\" name=\"question-"+jsonResult.question_list_result[i].question_id+"\" id=\"question-"+jsonResult.question_list_result[i].question_id+"-3\"value=\"3\" disabled=\"disabled\" "+ ( ( jsonResult.question_list_result[i].answer == 3 ) ? "checked=\"checked\"" :  "" ) +"/>";
					textToInsert[idx++] = "<label for=\"question-"+jsonResult.question_list_result[i].question_id+"-3\">3</label>";
					textToInsert[idx++] = "<input type=\"radio\" name=\"question-"+jsonResult.question_list_result[i].question_id+"\" id=\"question-"+jsonResult.question_list_result[i].question_id+"-4\"value=\"4\" disabled=\"disabled\" "+ ( ( jsonResult.question_list_result[i].answer == 4 ) ? "checked=\"checked\"" :  "" ) +"/>";
					textToInsert[idx++] = "<label for=\"question-"+jsonResult.question_list_result[i].question_id+"-4\">4</label>";
					textToInsert[idx++] = "<input type=\"radio\" name=\"question-"+jsonResult.question_list_result[i].question_id+"\" id=\"question-"+jsonResult.question_list_result[i].question_id+"-5\"value=\"5\" disabled=\"disabled\" "+ ( ( jsonResult.question_list_result[i].answer == 5 ) ? "checked=\"checked\"" :  "" ) +"/>";
					textToInsert[idx++] = "<label for=\"question-"+jsonResult.question_list_result[i].question_id+"-5\">5</label>";
					textToInsert[idx++] = "</fieldset>";
					textToInsert[idx++] = "</li>";
				}
				textToInsert[idx++] = "<li>";
				textToInsert[idx++] = "<span class=\"result-"+jsonResult.question_list_result[i].ox+"\">";
				textToInsert[idx++] = (jsonResult.question_list_result[i].ox == "O") ? "정답 :" : "오답 :";
				textToInsert[idx++] = "</span>";
				textToInsert[idx++] = "<span class=\"part_solution\">";
				textToInsert[idx++] = jsonResult.question_list_result[i].solution;
				textToInsert[idx++] = "</span>";
				textToInsert[idx++] = "</li>";
			}
		} else {
			alert("Error : loading fail ..");
		}
		
		$("#list_solution").append(textToInsert.join(""));
		$("#list_solution").listview("refresh");
		$("#list_solution").trigger("create");
	}
	
/*
	jsonResult = {
		"question_list_result":
		[
		 	{
		 		"chapter_id":1,
		 		"question_id":9,
		 		"content":"객관식 문제입니다. 가장 참한 인상은 누구일까요? 1.신경모  2.박수환  3.김선우  4.나형선  5.김예슬",
		 		"answer":3,
		 		"ox":"O",
		 		"solution":"이건 당연히 3번이 정답 아닌가 ?",
		 		"point":9,
		 		"type":0		//	객관식
		 	}
		]
	};
*/
};