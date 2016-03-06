
	function goQuestionInsert(id) {
		location.href = "../pages/questionInsert.jsp?id="+id;
	}
	
	function selectType(type_id) {
		if( type_id == 1 ) {
			$("#value3").css('visibility', 'visible');
			$("#value4").css('visibility', 'visible');
			$("#value5").css('visibility', 'visible');
			$("#value3_for").css('visibility', 'visible');
			$("#value4_for").css('visibility', 'visible');
			$("#value5_for").css('visibility', 'visible');
		} else {
			$("#value3").css('visibility', 'hidden');
			$("#value4").css('visibility', 'hidden');
			$("#value5").css('visibility', 'hidden');
			$("#value3_for").css('visibility', 'hidden');
			$("#value4_for").css('visibility', 'hidden');
			$("#value5_for").css('visibility', 'hidden');
		}
	}
	
	function goSubmit(ob) {
		oEditors.getById["question_content"].exec("UPDATE_IR_FIELD", []);
		oEditors.getById["question_solution"].exec("UPDATE_IR_FIELD", []);
		
		var form = document.getElementById("question_insert_form");
		var isOk = true;
		
		//	Check
		if( form._type[1].checked ) {
			if( form.answer[2].checked || form.answer[3].checked || form.answer[4].checked ) isOk = false;
		}
		if( document.getElementById("question_content").value == "" ) isOk = false;
		if( document.getElementById("question_solution").value == "" ) isOk = false;
		
		if( isOk == false ) alert("입력되었습니다.");
		else {
			try {
				ob.form.submit();
			} catch(e) {}
		}
	}
	
	function showUpdate(question_id, chapter_id) {
		location.href = "../pages/questionUpdate.jsp?id="+question_id+"&chapter_id="+chapter_id;
	}
	
	function goDelete(question_id, chapter_id) {
		var isOk = confirm("정말 삭제하시겠습니까?문제까지 삭제됩니다.");
		if( isOk ) {
			location.href = "../pages/questionDeletePrc.jsp?id="+question_id+"&chapter_id="+chapter_id;
		}
	}