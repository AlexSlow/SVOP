
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
  url: "http://localhost:5000/svop/api/tablo/control/status",
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
        stompClient.subscribe('/topic/seazonTablo', function (greeting) {
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
	$("#aircompanies").text(message["aircompanies"]);
	$("#period_start").text(message["period_start"]);
	$("#period_end").text(message["period_end"]);
	$("#rout").text(message["rout"]);
	$("#prilet").text(message["prilet"]);
	$("#vilet").text(message["vilet"]);
	$("#Logo").text(message["logo"]);
	$("#nomer_reys_p").text(message["nomer_reys"]);
	$("#prilet_days").text(message["prilet_days"]);
	
	$("#time_otpravl_p").text(message["time_otpravl"]);
	$("#nomer_reys_p").text(message["nomer_reys"]);
	$("#time_prib_p").text(message["time_prib"]);
	
	$("#nomer_reys_v").text(message["nomer_reys"]);
	$("#vilet_days").text(message["vilet_days"]);
	$("#time_otpravl_v").text(message["time_otpravl"]);
	$("#time_prib_v").text(message["time_prib"]);
	$("#head").text(message["tablo_head"]);
}
function showReys(message) {
	$("#filter_table").empty();
    message.forEach( function (sezonSchedule)
{
//Переформируем таблицу
$("#filter_table").append("<tr></tr>");
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.aircompany+"</td>");	
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.periodStart+"</td>");	
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.periodEnd+"</td>");	
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.rout+"</td>");
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.nomerPrilet+"</td>");	
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.priletDays+"</td>");	
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.prilet_time_otpravl+"</td>");	  
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.prilet_time_prib+"</td>");	
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.nomerVilet+"</td>");	
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.viletDays+"</td>");	
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.vilet_time_otpravl+"</td>");	  
$("#filter_table tr:last-child").append("<td>"+sezonSchedule.vilet_time_prib+"</td>");	
$("#filter_table tr:last-child").append("<td><img src='"+sezonSchedule.img+"'/></td>");	
});
}