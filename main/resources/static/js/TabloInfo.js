
//Нужно поместить websocket csrf
var token = $("meta[name='_csrf']").attr("content"); 
var header = $("meta[name='_csrf_header']").attr("content");
var headers = {};
headers[header] = token;
var url = window.location.href + '?';

var stompClient = null;
//ПОлучить статус
$.ajaxSetup({
        headers: {
            'X-Csrf-Token':token
        }
    });
			 $.ajax({
	 async: false, 			 
  type: "POST",
  url: host+"/svop/api/tablo/control/TabloInfoStatus",
	contentType: 'application/json',
	success: function(data) {
		console.log("Получить статус");
		console.log(data);
	if (data.message=="off"){
		off();
		}else if (data.message=="on")
		{
		on();
		if(data.headers!=null)
		{
		setHeaders(data.headers);
		}
		if(data.list!=null)
		{
		showReys(data.list);
		}
}
connect();
	}
});	

//while(isInit!=true){}


function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
	
    stompClient.connect({headers}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/TabloInfo', function (greeting) {
		//Обработка полученного результата
		if (greeting.body=="off"){
		off();
		
		}else if (greeting.body=="on")
		{
		//console.log(greeting.body);
		on();
		}else
		{
		let message = JSON.parse(greeting.body);
		//console.log(message);
		setHeaders(message.header);
		showReys(message.body);
		}
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}
//При закрытии окна
window.onbeforeunload = function() {
   disconnect();
};
function off()
{
	console.log("выключить");
	if (tablo == true) {
    $("body").removeClass("TabloActive") ;
	$("body").addClass("tabloBlack");
}

		$("#tablo").removeClass("TabloActive") 
		$("#tablo").addClass("tabloBlack");
		$("#content_table").css("display","none");
		$("#headTitle").css("display","none");
}
function on()
{
	console.log("включить");
		if (tablo == true) {
    $("body").removeClass("tabloBlack");
	$("body").addClass("TabloActive");
}
		$("#tablo").removeClass("tabloBlack"); 
		$("#tablo").addClass("TabloActive");
		$("#content_table").css("display","");
		$("#headTitle").css("display","");
}
function setHeaders(message) {
	$("#day").text(message["day"]);
	$("#rout").text(message["rout"]);
	$("#nomer").text(message["nomer"]);
	$("#time_otpravl").text(message["timeDeporture"]);
	$("#nomerStoic").text(message["nomerStoic"]);
	$("#status").text(message["status"]);
	$("#head").text(message["tablo_head"]);
	$("#timeText").text(message["currentTime"]);
	$("#locale").text(message["locale"]);
}
function showReys(message) {
	$("#filter_table").empty();
    message.forEach( function (Schedule)
{
	date=new Date(Schedule.day);
	console.log(date);
//Переформируем таблицу
$("#filter_table").append("<tr></tr>");
$("#filter_table tr:last-child").append("<td>"+date.toLocaleDateString($("#locale").val())+"</td>");	
$("#filter_table tr:last-child td:last-child").addClass("align-middle");
$("#filter_table tr:last-child").append("<td >"+Schedule.route+"</td>");	
$("#filter_table tr:last-child td:last-child").addClass("align-middle");
$("#filter_table tr:last-child").append("<td>"+Schedule.nomerReys+" "+"<img src='"+Schedule.img+"'/></td>");	
$("#filter_table tr:last-child td:last-child").addClass("align-middle");
$("#filter_table tr:last-child").append("<td>"+Schedule.nomer+"</td>");
$("#filter_table tr:last-child td:last-child").addClass("align-middle");
$("#filter_table tr:last-child").append("<td>"+Schedule.timeVilet+"</td>");
$("#filter_table tr:last-child td:last-child").addClass("align-middle");	
$("#filter_table tr:last-child").append("<td>"+Schedule.status+"</td>");
$("#filter_table tr:last-child td:last-child").addClass("align-middle");	
});
}
setInterval(() => {
	let date=new Date();
$("#time").text(date.toLocaleTimeString("ru"))}, 1000);