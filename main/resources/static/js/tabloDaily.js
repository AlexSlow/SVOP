
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
  url: host+"/svop/api/tablo/control/Dailystatus",
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
//isInit=true;
	}
});	

//while(isInit!=true){}


function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
	
    stompClient.connect({headers}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/dailyTablo', function (greeting) {
		//console.log(greeting.body);
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
    setConnected(false);
    console.log("Disconnected");
}
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
	$("#time_otpravl").text(message["time_otpravl"]);
	$("#time_prib").text(message["time_prib"]);
}
function showReys(message) {
	$("#filter_table").empty();
    message.forEach( function (sezonSchedule)
{
//Переформируем таблицу
$("#filter_table").append("<tr></tr>");
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.day+"</td>");	
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.rout+"</td>");	
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.nomer+"</td>");	
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.time_otpravl+"</td>");
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.time_prib+"</td>");		
});
}