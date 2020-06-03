//поместить токен csrf
 $.ajaxSetup({
        headers: {
            'X-Csrf-Token':token
        }
    });
 var content;
 $('#addModal').on('show.bs.modal', function (event) {
$("#modal_table tbody").empty();
  //Начальная инициализация
  selectedArray=[];
    $("#content_table tbody tr").each(function(){
	if ($(this).children("td:first-child").children("input").prop('checked')) //Если строка выбрана
	{
		selectedArray.push($(this).children("td:first-child").children("input").val());
	}
	});

	requestGetFlightSheduleById(selectedArray);
	
	});
 
  function addEmptyRow(){
	$("#modal_table tbody ").append("<tr><td class='d-none'>"+null+"</td><td><input type='text' class='form-control'/></td>"+getSelect(null)+"</tr>");  
  }
  
    //Удалить строку
   $("#remove_str").on('click', function(e) {
   $("#modal_table tbody tr:last-child").remove();

   });
   
   
   //Сохраняем в массив объектов
    $("#save").on('click', function(e) {
	var ajax_array =[];
	$("#modal_table tbody tr").each(function(){ 
		 let ajax_object={};
		 let id=$(this).children("td:eq(0)").text();
		 ajax_object["id"]=id;
		 ajax_object["day"]=Date.parse($(this).find("#day").val());
		 
		 ajax_object["deporture"]=$(this).find("#deporture").val();
		 ajax_object["prilet"]=$(this).find("#prilet").val();
		 ajax_object["comment"]=$(this).find("#comment").val();
		  ajax_object["vs"]=$(this).find("#vs").val();
		 ajax_object["status"]=$(this).find("#status").text();
		 let undoMoving=$(this).find(".checkboxClass").prop("checked");
		 if (undoMoving==null) undoMoving=false;
		 ajax_object["undoMoving"]=undoMoving;
		 //ПРоверить объект перед добавлением.
		 /*
		 let isValidate=true;
		 for (var key in ajax_object) {
		console.log(key, ':', ajax_object[key]);
		if (ajax_object[key]==null) isValidate=false;
			}
		if (isValidate)
			*/
		ajax_array.push(ajax_object);
		 
	});
	console.log(ajax_array);
	requestMove(ajax_array);
   });
  
  
  	function requestGetFlightSheduleById(idlist)
{
	//console.log(idlist)
 	 $.ajax({
  type: "POST",
  url: host+"/svop/api/flightShedule/getById",
	data: JSON.stringify(idlist),
	contentType: 'application/json',
	success: function(data) {
	//console.log(data);
	data.forEach(function(item, i, arr) {
	let id=item.id;
	let day=item.day;
	let nomer=item.nomer;
	let type=item.status;
	let timeDep=item.timeDeporture;
	let timePril=item.timePrilet;
	
	let timeDeportureNext=item.timeDeportureNext;
	let timePriletNext=item.timePriletNext;
	let dayNext=item.dayNext;
	let comment=item.comment;
	
	let moveable=item.moveable;
	let dateMills=item.dateNextMills;
	let vs=item.tipVs;
	//Сформируем запрос на сервер и получим информацию о нужных рейсах
	if (type!="Перемещенный")
	{
$("#modal_table tbody ").append("<tr><td class='d-none'>"+id+"</td><td>"+day+"</td><td>"+nomer+"</td>"+
"<td>"+timeDep+"</td><td>"+timePril+"</td><td>"+vs+"</td><td><input type='date' id='day' /></td><td><input id='deporture' type='time'/></td><td><input id='prilet' type='time'/></td>"+
"<td><input id='vs' type='text'/></td><td><input id='comment' type='text'/></td><td id='status' class='d-none'>"+type+"</td></tr>"); 
	} 
else
{
	if (moveable!=true)
{
	$("#modal_table tbody ").append("<tr><td class='d-none'>"+id+"</td><td>"+day+"</td><td>"+nomer+"</td>"+
"<td>"+timeDep+"</td><td>"+timePril+"</td><td>"+vs+"</td><td>"+dayNext+"</td><td>"+timeDeportureNext+"</td><td>"+timePriletNext+"</td><td>"+vs+"</td><td>"+comment+"</td>"+
"<td class='d-none'>"+type+"</td></tr>"); 
}else{
	
	var d = new Date(dateMills);
	$("#modal_table tbody ").append("<tr><td class='d-none'>"+id+"</td><td>"+day+"</td><td>"+nomer+"</td>"+
"<td>"+timeDep+"</td><td>"+timePril+"</td><td>"+vs+"</td><td><input type='date' id='day' value='"+dateFormat(d)+"'/></td><td><input type='time' id='deporture' value='"+timeDeportureNext+"'/></td><td><input type='time' id='prilet' value='"+timePriletNext+"'/></td><td><input type='text' id='vs' value='"+vs+"' /></td>"+
"<td><input id='comment' value='"+comment+"'  type='text'/></td>  <td id='status' class='d-none'>"+type+"</td> <td>Отменить перемещение <input class='checkboxClass'  type='checkbox'/></td></tr>"); 
}
}
});
  }
});
}
  	function requestMove(ajax_array)
{
	//console.log(idlist)
 	 $.ajax({
  type: "POST",
  url: host+"/svop/api/flightShedule/move",
	data: JSON.stringify(ajax_array),
	contentType: 'application/json',
	success: function(data) {
	$('#addModal').modal('hide');
	console.log(data);
  },
  	error: function (jqXHR, exception) {
		console.log(jqXHR);
	$("#modal_error").text(jqXHR.responseJSON.message);
    // Your error handling logic here..
}
});
}

function dateFormat(d) {
  let month = String(d.getMonth() + 1);
  let day = String(d.getDate());
  const year = String(d.getFullYear());

  if (month.length < 2) month = '0' + month;
  if (day.length < 2) day = '0' + day;

  return `${year}-${month}-${day}`;
}
   

   
