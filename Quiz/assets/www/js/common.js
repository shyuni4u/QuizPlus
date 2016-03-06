document.addEventListener("backbutton", onBackKeyDown, false);

function onBackKeyDown() {
	navigator.notification.confirm("종료하시겠습니까?", exit_application, "확인", "예, 아니요");
}

function exit_application(button) {
	if( button == 1 )	navigator.app.exitApp();
}
var url_path = "http://14.63.223.191:8080/quiz_api";
//var url_path = "http://localhost:8080/QuizApi";
var user_id = null;
var jsonResult = null;
var page_index = 1;

function checkUser() {
	
	var temp_user_id = window.localStorage.getItem("user_id");
	if( temp_user_id == null || temp_user_id == "null" )	user_id = "";
	else													user_id = temp_user_id;
}

function goBack() {
	if( page_index == 2 ) {
		location.href="./list.html";
	} else {
		location.href="../index.html";
	}
	return false;
}