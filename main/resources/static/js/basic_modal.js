
 $.ajaxSetup({
        headers: {
            'X-Csrf-Token':token
        }
    });

var Headers = new Map(); // Заголовки главной таблицы
 var Names = new Map(); //Атрибуты name заголовков главной таблицы, они не зависят от языка
 var content;
 $('#addModal').on('show.bs.modal', function (event) {

 clean();
  // получить кнопку, которая его открыло
  var button = $(event.relatedTarget)

  // извлечь информацию из атрибута data-content
   content= button.data('content') 
  // вывести колонки таблицы
  $("#modal_table thead").append("<tr></tr>");
  $("#content_table thead tr").children("th").each(function(){
  if ($(this).text()!="")
  {
  Headers.set($(this).text(),$(this).attr('data-type'));
  Names.set($(this).text(),$(this).attr('name'));
$("#modal_table thead tr").append("<th>"+$(this).text()+"</th>");
}
  
  });
  
  if (content=="add")
  {
  $("#modal_title").text(head_add_title);
  $("#add_buttons").removeClass("d-none");
  
   $("#modal_table tbody ").append("<tr></tr>");
   //console.log(Headers);
	for (let key of Headers.keys()) {
	let val=Headers.get(key);
	let name=Names.get(key);
	$("#modal_table tbody tr:last-child").append("<td>"+"<input class='control-sm'  name='"+name+"'  type='"+val+"'+  /></td>");
}
  }
  
  
  
  
  else
  {
  //Добавим колонку под id
   $("#modal_table thead tr ").prepend("<th></th>");
   $("#modal_title").text(head_red_title);
   $("#add_buttons").addClass("d-none");
   
   //Выбор строк для редактирования
    $("#content_table tbody tr").each(function(){
	if ($(this).children("td:first-child").children("input").prop('checked')) //Если строка выбрана
	{
	let id=$(this).children("td:first-child").children("input").val();
	let data=[];
	
	$(this).children("td").each(function(){
	
	
$(this).children("label").each(function(){
	//Получение полей
	data.push($(this).text());
	});
	});
	//Вставим строку
	 $("#modal_table tbody ").append("<tr></tr>");
	 $("#modal_table tbody tr:last-child ").append("<td>"+"<input class='control-sm d-none' value='"+id+"' type='text'/></td>");
	 let i=0;
	for (let key of Headers.keys()) {
	let name=Names.get(key);
	let val=Headers.get(key);
	let input_value=data[i];
	$("#modal_table tbody tr:last-child").append("<td>"+"<input class='control-sm' name='"+name+"' value='"+input_value+"' type='"+val+"'/></td>");
	i++;
	  }
 }
 });
   
   
  }//конец else
   });
  
  
    //Добавить строку
   $("#add_str").on('click', function(e) {
   
   $("#modal_table tbody ").append("<tr></tr>");
   
   //Выставить все цвета нейтральным
   //.text-danger
   
   
   //console.log(Headers);
	for (let key of Headers.keys()) {
	let val=Headers.get(key);
	let name=Names.get(key);
	$("#modal_table tbody tr:last-child").append("<td>"+"<input class='control-sm'  name='"+name+"'  type='"+val+"'+  /></td>");

}
   });
   
    //Удалить строку
   $("#remove_str").on('click', function(e) {
   $("#modal_table tbody tr:last-child").remove();

   });
   
   
   //Сохраняем в массив объектов
    $("#save").on('click', function(e) {
	var ajax_array =[]

    $("#modal_table tbody tr").each(function(){
	let ajax_object ={}
	$(this).children("td").each(function(){
	//Индекс столбца и инодекс поля
	let collumn=$(this).children("input").attr('name');
	if (collumn==null) collumn="id";//колонка с нулеваым именем это id

	ajax_object[collumn]=$(this).children("input").val();
	});
	//console.log(ajax_object);
	ajax_array.push(ajax_object);
	});
	//Мы получили объект запроса
	//console.log(ajax_array);
	if (content=="add")
	{
	send_add_request(ajax_array);
	}
	else{
	send_update_request(ajax_array);
	}
   });
   
   
   function clean()
   {
   $("#modal_table tbody").empty();
    $("#modal_table thead").empty();
   Headers.clear();
   Names.clear();
   //ajax_array.clear();

   }
   
   
  function send_add_request(ajax_array)
  {
	 
	ajax_array.forEach(function(item, i, arr) {
  $.post(service+"add/",{NameRu:item["NameRu"]
  ,NameEng:item["NameEng"],
  NameCh:item["NameCh"],
  GMT:item["GMT"],
  ICAO:item["ICAO"],
  IATA:item["IATA"]});
  console.log(item);
});
	$('#addModal').modal('hide');
	 window.setTimeout(function(){location.reload()},3000);
   }
   
   
   function send_update_request(ajax_array)
  { 
  //alert(service+"test/"+" "+header+" "+token);
	  /*
 	 ajax_array.forEach(function(item, i, arr) {
  $.post("http://localhost:5000/svop/api/airports/update",{id:item["id"],NameRu:item["NameRu"]
  ,NameEng:item["NameEng"],
  NameCh:item["NameCh"],
  GMT:item["GMT"],
  ICAO:item["ICAO"],
  IATA:item["IATA"]});
  });
//$('#addModal').modal('hide');
// window.setTimeout(function(){location.reload()},3000);
 */
 var request = $.ajax({
type: "POST",
  url: service_api,
  cache: false,
	data: { }
});


request.done(function(msg) {
  alert("done "+msg)
});
 
request.fail(function(jqXHR, textStatus) {
  alert( "Request failed: " + textStatus+"  "+header+" "+token );
});

   }
   
