//поместить токен csrf
 $.ajaxSetup({
        headers: {
            'X-Csrf-Token':token
        }
    });
	//------------------------------Нажатие на показ стойки для рейсов
$('#showReys').on('click', function() {
	// ПОлучить первый id
	var id=-1;
$('#content_table input:checkbox:checked').each(function() {
	id=$(this).val();
})
//id - id стойки
if (id!=-1){
 getReysAjax(id,service_api_getReys); 
}
 
});

	//------------------------------Нажатие показать стойку
$('#showStoic').on('click', function() {
	var id=-1;
$('#content_table input:checkbox:checked').each(function() {
	id=$(this).val();
})
//id - id стойки
if (id!=-1){
 $("#rightMenu").load(host+"/svop/stoic/"+id+" #tablo"); 
  $.getScript('/js/stoic.js', function() {

    });
}
	
});

	//------------------------------Нажатие связывание
$('#bindReysAndStoic').on('click', function() {
	// ПОлучить первый id

let idStoic=$("#rightMenu #idSelectedStoic").val();
let idReys= $('input[name=radio]:checked').val();
bindStoicAndReys(idStoic,idReys,service_api_bindStoicReys);

 
});
//-----------------------------------------Нажатие зажечь
$('#off_on').on('click', function() {
	// ПОлучить первый id
var idStoicArr=[];
$('#content_table input:checkbox:checked').each(function() {
	idStoicArr.push($(this).val());
})
fireAjaxRequest(idStoicArr,service_api_fire);

 
});
//id -id стойки
function setReysTable(stoicReys,id)
{
	$("#reysTable  tbody").empty();
	$("#rightMenu #idSelectedStoic").val(id);
	//console.log($("#rightMenu #idSelectedStoic").val());
	$("#reysTable").removeClass("d-none");
	$("#rightMenuTitle").removeClass("d-none");
	let checked="";
	stoicReys.forEach( function (sezonSchedule)
{
	if (sezonSchedule.bind){checked="checked";}else {checked="";}
	
	$("#reysTable tbody").append("<tr><td><input type='radio' name='radio' class='radio' value='"+sezonSchedule.flightScheduleView.id+"' "+checked+"></td>"
	+"<td>"+sezonSchedule.flightScheduleView.nomer+"</td>"+"<td>"+sezonSchedule.nomerStoic+"</td>"+"<td>"+sezonSchedule.flightScheduleView.day+"</td>"+
	"<td>"+sezonSchedule.flightScheduleView.rout+"</td>"+"<td>"+sezonSchedule.flightScheduleView.timeDeporture+"</td>"+"<td>"+sezonSchedule.flightScheduleView.timePrilet+"</td>"+"<td>"+sezonSchedule.flightScheduleView.direction+"</td></tr>");
});
	
}
//----------------------------------------------------------------------Обновить таблицу стоек
function refreshStoicsTable(Stoic)
{

	$("#content_table tbody").empty();	
	Stoic.forEach( function (stoic)
{
	let sts;
	if (stoic.status){sts="Включен";}else{sts="Выключен";}
	
	$("#content_table tbody").append("<tr><td><input type='checkbox' id='ch"+stoic.id+"' value='"+stoic.id+"' name='ch[]' form='form'/></td>"+
	"<td><label for='ch"+stoic.id+"'>"+stoic.nomer+"</label></td>"+
	"<td><label for='ch"+stoic.id+"' >"+stoic.nomerReys+" </label></td>"+
	"<td>"+sts+"</td></tr>");
});
	
}
//--------------------------------------------------------------------- Запросы
function getReysAjax(id,adress)
  { 
  console.log("Получение стойки с id= "+id);
  $.ajax({
  type: "POST",
  url: adress,
	data: JSON.stringify(id),
	contentType: 'application/json',
	success: function(data) {
   // alert(data);
   console.log(data);
   setReysTable(data,id);
	}
   
});
   }
   
   function bindStoicAndReys(stoic_id,reys_id,adress)
  { 
  console.log("Связь стойки с id= "+stoic_id+" рейса"+reys_id);
  let obj={};
  obj["reysId"]=reys_id;
  obj["stoicId"]=stoic_id;
  $.ajax({
  type: "POST",
  url: adress,
	data: JSON.stringify(obj),
	contentType: 'application/json',
	success: function(data) {
   console.log(data);
   setReysTable(data,stoic_id);
   refreshStoicsRequest(service_api_fefresh);
	}
   
});
   }
   
   function refreshStoicsRequest(adress)
  { 
  $.ajax({
  type: "POST",
  url: adress,
	contentType: 'application/json',
	success: function(data) {
   console.log(data);
   refreshStoicsTable(data);
	}
   
});
   }
   
    function fireAjaxRequest(stoic_id,adress)
  { 
   console.log("Связь стойки с id= "+stoic_id);
  $.ajax({
  type: "POST",
  url: adress,
	contentType: 'application/json',
	data: JSON.stringify(stoic_id),
	success: function(data) {
   console.log(data);
   refreshStoicsTable(data);
	}
   
});
   }
  