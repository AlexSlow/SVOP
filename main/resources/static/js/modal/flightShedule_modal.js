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
	var ajax_array =[]
	$("#modal_table tbody tr").each(function(){ 
		 let ajax_object={};
		 ajax_object["id"]=$(this).children("td:eq(0)").text();
		 ajax_object["day"]=Date.parse($(this).children("td:eq(5)").children("input").val());
		 ajax_object["deporture"]=$(this).children("td:eq(6)").children("input").val();
		 ajax_object["prilet"]=$(this).children("td:eq(7)").children("input").val();
		 ajax_object["comment"]=$(this).children("td:eq(8)").children("input").val();
		 ajax_object["status"]=$(this).children("td:eq(9)").text();
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
  url: "http://localhost:5000/svop/api/flightShedule/getById",
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
	
	//Сформируем запрос на сервер и получим информацию о нужных рейсах
	if (type!="Moved")
$("#modal_table tbody ").append("<tr><td class='d-none'>"+id+"</td><td>"+day+"</td><td>"+nomer+"</td>"+
"<td>"+timeDep+"</td><td>"+timePril+"</td><td><input type='date'/></td><td><input type='time'/></td><td><input type='time'/></td>"+
"<td><input type='text'/></td><td class='d-none'>"+type+"</td></tr>");  
});
	
  }
});
}

  	function requestMove(ajax_array)
{
	//console.log(idlist)
 	 $.ajax({
  type: "POST",
  url: "http://localhost:5000/svop/api/flightShedule/move",
	data: JSON.stringify(ajax_array),
	contentType: 'application/json',
	success: function(data) {
	console.log(data);
  }
});
}
   

   
