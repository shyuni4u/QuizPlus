	
	$(document).ready(function() {
		$(".main_chapter").click(goChapter);
		$(".main_question").click(goQuestion);
		$(".main_state").click(goState);
		$(".main_score").click(goScore);
		$(".main_statistics").click(goStatistics);
	});
	
	function goChapter() { location.href = "../pages/chapterList.jsp"; }
	function goQuestion() { location.href = "../pages/questionSelect.jsp"; }
	function goState() { location.href = "../pages/stateList.jsp"; }
	function goScore() { location.href = "../pages/scoreSelect.jsp"; }
	function goStatistics() { location.href = "../pages/statistics.jsp"; }