//поместить токен csrf
 $.ajaxSetup({
        headers: {
            'X-Csrf-Token':token
        }
    });

var Headers = new Map(); // Заголовки главной таблицы
var Names = new Map(); //Атрибуты name заголовков главной таблицы, они не зависят от языка
var is_null_map = new Map(); //валидация не пустых элементов
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
is_null_map.set($(this).text(),$(this).attr('data-notnull'));
$("#modal_table thead tr").append("<th>"+$(this).text()+"</th>");
}
});
  
  if (content=="add")
  {
  $("#modal_title").text(head_add_title);
  $("#modal_table thead tr ").prepend("<th></th>");
  $("#modal_table tbody ").append("<tr><td><input class='d-none' value='"+null+"' type='text'></td></tr>");
   //console.log(Headers);
	for (let key of Headers.keys()) {
	let val=Headers.get(key);
	let name=Names.get(key);
	let is_null=is_null_map.get(key);
	$("#modal_table tbody tr:last-child").append("<td><input class='control-sm form-control'   name='"+name+"' data-notnull='"+is_null+"' type='"+val+"'+  /></td>");
}
  }
  
  
  
  
  else
  {
  //Добавим колонку под id 
  $("#modal_title").text(head_red_title);
  $("#modal_table thead tr ").prepend("<th></th>");
   //$("#add_buttons").addClass("d-none");
   
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
	let is_null=is_null_map.get(key);
	let input_value=data[i];
	$("#modal_table tbody tr:last-child").append("<td>"+"<input class='control-sm form-control' name='"+name+"' data-notnull='"+is_null+"' value='"+input_value+"' type='"+val+"'/></td>");
	i++;
	  }
 }
 });
  }//конец else
   });
  
  
    //Добавить строку
   $("#add_str").on('click', function(e) {
   
   $("#modal_table tbody ").append("<tr></tr>");
   $("#modal_table tbody tr:last-child").append("<td><input class='d-none' value='"+null+"' type='text'></td>");
   //Выставить все цвета нейтральным
   //console.log(Headers);
	for (let key of Headers.keys()) {
	let val=Headers.get(key);
	let name=Names.get(key);
	let is_null=is_null_map.get(key);
	$("#modal_table tbody tr:last-child").append("<td>"+"<input class='control-sm form-control' data-notnull='"+is_null+"' name='"+name+"'  type='"+val+"'/></td>");
}
 }); 
    //Удалить строку
   $("#remove_str").on('click', function(e) {
   $("#modal_table tbody tr:last-child").remove();

   });
   
   
   //Сохраняем в массив объектов
    $("#save").on('click', function(e) {
		
		if (check()==true)
		{
			//console.log("Выход");
			$("#modal_error").text("Ошибка. Неккоректные данные");
			return;
		}
		
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

	//Отправка Ajax запроса
	send_request(ajax_array,service_api_save);

	
	
   });
   
   
   function clean()
   {
   $("#modal_table tbody").empty();
    $("#modal_table thead").empty();
   Headers.clear();
   Names.clear();
   is_null_map.clear();
   }
   // Проверка на соответствие данных анотациям таблицы
   function check()
   {
	   	$("#modal_error").text("");
	   //Очистим строки от подцветки
	   $("#modal_table tbody tr").each(function(){
	$(this).children("td").each(function(){
		$(this).parent("tr").css("background-color","white");
	});
	});
	   
	   let is_error=false;
	$("#modal_table tbody tr").each(function(){

	$(this).children("td").each(function(){
	
	let value =$(this).children("input").val();
	let is_not_null_attr=$(this).children("input").attr("data-notnull");
	
	if (is_not_null_attr==1){ if (value==""){
		//console.log("isnull "+is_not_null_attr+" "+value);
		$(this).parent("tr").css("background-color","red");
		is_error=true;
	}
	}
	});
	});  
	   //console.log("true");
	   return is_error;
   }
   
   
  
   
   function send_request(ajax_array,adress)
  { 
 
  $.ajax({
  type: "POST",
  url: adress,
	data: JSON.stringify(ajax_array),
	contentType: 'application/json',
	success: function(data) {
   // alert(data);
	location.reload();
  }
});

   }
   
