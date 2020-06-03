//Нужно поместить websocket csrf
var token = $("meta[name='_csrf']").attr("content"); 
var header = $("meta[name='_csrf_header']").attr("content");
var headers = {};
headers[header] = token;
var url = window.location.href + '?';
var stompClient = null;

$.ajaxSetup({
        headers: {
            'X-Csrf-Token':token
        }
    });
	getStatus();
	function getStatus()
	{
			 $.ajax({
	 async: false, 			 
  type: "POST",
  url: host+"/svop/api/stoic/getStatus",
  data: JSON.stringify(StoicCurrentId),
	contentType: 'application/json',
	success: function(data) {
		console.log("Получить статус");
		console.log(data);
		if (data.status==true)
		{
			on();
			show(data);
		}else{
			off();
		}
		connect();
}

});	
	}

function connect() {
	//console.log("Начать соединение");
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
	
    stompClient.connect({headers}, function (frame) {
        console.log('Connected: ' + frame);
		console.log('/topic/stoic/'+StoicCurrentId);
        stompClient.subscribe('/topic/stoic/'+StoicCurrentId, function (greeting) {
		console.log("Пакет");
		console.log(greeting.body);
		if (greeting.body=="ref")
		{
			getStatus();
		}else{
			if (greeting.body=="ON")
		{
			on();
		}else if (greeting.body=="OFF"){
			off();
		}
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
   alert("Выкл");
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
		$(".text").css("display","none");
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
		$(".text").css("display","");
}
function show(message) {
//Переформируем таблицу
$("#Img").empty();
$("#Img").append("<img src='"+message.img+"'/>");
$("#StoicText").text(titleRu+message.nomer);
$("#NomerText").text(ReysRu+message.nomerReys);
$("#RoutText").text(getLastAirport(message.routeRu)+" / "+getLastAirport(message.routeEn));
console.log(message.timeViletRu);
date=new Date(message.timeViletRu);
console.log(date);
date.setMinutes(date.getMinutes() - TimeBeforePosadka);
$("#TimeText").text(EndPosadkaTeks+date);	
}
function getLastAirport(route)
{
	
	let ports=route.split("—");
	return ports[ports.length-1];
}