function setUserId() {
	$("#student_id").val(user_id);
}

function goChapterList() {
	window.localStorage.setItem("user_id", $("#student_id").val());
	location.href="./page/list.html";
}