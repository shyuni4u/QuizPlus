	
	jQuery(function(){
		$(".chapter_tr")
			.mouseover(function() {
				$(this).css('background-color', '#eeeeee');
			})
			.mouseleave(function() {
				$(this).css('background-color', 'white');
			});
	});

	function showUpdate(id, title, subtitle) {
		var content_td = "";
		
		content_td =	"<td class='chapter_id'></td>" +
						"<td class='chapter_title'><input type='text' name='ut_title' id='ut_title_"+id+"' class='i_text' style='width:90%' value='"+title+"'></td>" +
						"<td class='chapter_subtitle'><input type='text' name='ut_subtitle' id='ut_subtitle_"+id+"' class='i_text' style='width:90%' value='"+subtitle+"'></td>" +
						"<td class='chapter_state'></td>" +
						"<td class='btns'>" +
							"<span class='btn_pack small icon'><span class='check'></span><input type='button' value='확인' onclick='goUpdate("+id+")'/></span>" +
						"</td>";
		$("#chapter_update_"+id).append(content_td);
		//document.getElementById("chapter_update_"+id).innerHTML = content_td;		//	IE9 에서는 innerHTML 을 제공하지 않는다.
	}
	
	function goQuestionList(id) {
		location.href = "./questionList.jsp?id="+id;
	}
	
	function goUpdate(id) {
		var title = document.getElementById("ut_title_"+id).value;
		var subtitle = document.getElementById("ut_subtitle_"+id).value;
		location.href = "./chapterUpdatePrc.jsp?id="+id+"&title="+title+"&subtitle="+subtitle;
	}
	
	function goDelete(id) {
		var isOk = confirm("정말 삭제하시겠습니까? 문제도 삭제됩니다.");
		if( isOk ) {
			location.href = "./chapterDeletePrc.jsp?id="+id;
		}
	}