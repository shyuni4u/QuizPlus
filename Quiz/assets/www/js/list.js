list = {
	load : function() {
		$.mobile.showPageLoadingMsg();
		$("#part_chapter").css("display", "block");
		$("#part_question").css("display", "none");
		$("#part_solution").css("display", "none");
		$("#list_chapter li").remove();
		list.refresh();
	},
		
	success : function(data) {
		jsonResult = data;
		list.initialize();
	},
	
	refresh : function() {
		$.ajax({
			type: "GET",
			url: url_path+"/load/chapter",
			data: "user_id="+user_id,
			dataType: "jsonp",
			success: list.success
		});
	},

	initialize : function() {
		$.mobile.hidePageLoadingMsg();	//	loading 완료된 경우 loading 메시지 없앰
		
		//	tag initialization 
		var textToInsert = [];
		var idx = 0;

		if( jsonResult != null ) {
			for( var i = 0 ; i < jsonResult.chapter_list_result.length ; i++ ) {
				textToInsert[idx++] = "<li>";
				textToInsert[idx++] = "<h3>"+jsonResult.chapter_list_result[i].title+"</h3>";
				textToInsert[idx++] = "<p>"+jsonResult.chapter_list_result[i].subtitle+"</p>";
				if( jsonResult.chapter_list_result[i].state == 3 ) {
					textToInsert[idx++] = "<span class=\"li-btn li-btn-cc\" onclick=\"solution.load('"+ jsonResult.chapter_list_result[i].chapter_id +"');\">정답</span>";
					textToInsert[idx++] = "<span class=\"li-btn li-sc\">"+ jsonResult.chapter_list_result[i].score +"/10</span>";
				} else if( jsonResult.chapter_list_result[i].state == 2 ) {
					textToInsert[idx++] = "<span class=\"li-btn li-btn-fin\">응시</span>";
				} else if( jsonResult.chapter_list_result[i].state == 1 ) {
					textToInsert[idx++] = "<span class=\"li-btn li-btn-ok\" onclick=\"question.load('"+ jsonResult.chapter_list_result[i].chapter_id +"');\">미응시</span>";
				} else {
					textToInsert[idx++] = "";
				}
				textToInsert[idx++] = "</li>";
			}
		} else {
			alert("Error : loading fail ..");
		}
		
		$("#list_chapter").append(textToInsert.join(""));
		$("#list_chapter").listview("refresh");
	}
/*
	jsonResult = {
		"chapter_list_result":	[
		 	{
		 		"chapter_id":1,
		 		"title":"소프트웨어공학 이론",
		 		"subtitle":"1. 프로세스",
		 		"state":4,
		 		"score":7
		 	}
		]
	};
*/
};