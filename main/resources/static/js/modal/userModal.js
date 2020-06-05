 $.ajaxSetup({
        headers: {
            'X-Csrf-Token':token
        }
    });
		   //Добавить строку
   $("#user_add_str").on('click', function(e) {
	addStr({username:"",id:null});
}); 
    //Удалить строку
   $("#user_remove_str").on('click', function(e) {
	
   $("#user_modal_table tbody tr:last-child").remove();
   });
 $('#userModal').on('show.bs.modal', function (event) {
	 
$("#user_modal_table tbody").empty();
  //Начальная инициализация
  var checked = [];
$('input:checkbox:checked').each(function() {
	checked.push($(this).val());
});	
requestUsersById(checked).then(data=>{console.log(data);fillTable(data); }).catch(Exception=>$("#user_modal_error").text(Exception));
	});
	function fillTable(rows)
	{
	rows.forEach(function(item, i, rows) {
	addStr(item);			   
	});
	}
	
	function addStr(data)
	{
		$("#user_modal_table tbody").append("<tr><td class='id d-none'>"+data.id+"</td>"+
		"<td><input class='username' type='text' value='"+data.username+"'/></td>"+
		"<td><input class='password' type='password' </td>"+
		"<td><input class='confirmPassword' type='password' /></td></tr>");
	}
    //Сохраняем в массив объектов
    $("#user_save").on('click', function(e) {
	$("#user_modal_error").empty();
	let ajax_array =[];
	let errors=[];
    $("#user_modal_table tbody tr").each(function(){
	let ajax_object ={}
	//Индекс столбца и инодекс поля
	ajax_object.id=$(this).find(".id").text();
	ajax_object.username=$(this).find(".username").val();
	ajax_object.password=$(this).find(".password").val();
	ajax_object.confirmPassword=$(this).find(".confirmPassword").val();
	check(ajax_object,errors);
	
	console.log(ajax_object);
	ajax_array.push(ajax_object);
	});
	if (errors.length>0)
	{
		console.log("Ошибки");
		console.log(errors);
		 errors.forEach(function(item, i, errors) {
	$("#user_modal_error").append("<div>"+item+"</div>");	   
	});
	}else{
		//Мы получили объект запроса
	console.log(ajax_array);
	//Отправка Ajax запроса
	send_request(ajax_array,save_api_adress).then(data=>{console.log(data);getPage();}).catch(Exception=>$("#user_modal_error").text(Exception));
	}
   });
   
   
   function send_request(list,save_api_adress)
	{
		promise=new Promise((resolve,reject)=>{
				 $.ajax({
	type: "POST",
	url: save_api_adress,
	data: JSON.stringify(list),
	contentType: 'application/json',
	success:(data)=>{resolve(data)},
	error:(jqXHR, exception)=>{new Error(jqXHR.responseJSON.message);}
});
		});
		return promise;
	}
	
	
	function requestUsersById(idList)
	{
		promise=new Promise((resolve,reject)=>{
				 $.ajax({
	type: "POST",
	url: host+"/svop/api/admin/getUsersById",
	data: JSON.stringify(idList),
	contentType: 'application/json',
	success:(data)=>{resolve(data)},
	error:(jqXHR, exception)=>{new Error(jqXHR.responseJSON.message);}
});
		});
		return promise;
	}
	//Получить страницу
	function getPage()
{
$.ajax({
  type: "post",
  url: host+"/svop/api/admin/getPage",
	data:JSON.stringify({page:$_GET("page"),size:10}),
	contentType: 'application/json',
	success: function(data) {
	console.log(data);
	//Успех
	fillTableFromAjax(data);
	$('#userModal').modal('hide');
  }
	});
}
//Заполнить страницу пользоателей
 function fillTableFromAjax (data){
$("#content_table tbody").empty();
data.forEach(function(user, i, data) {
 $("#content_table tbody").append("<tr><td  ><input type='checkbox' id='ch"+user.id+"'"+
 "value='"+user.id+"' name='ch[]' form='form'/></td>"+
"<td><label for='ch"+user.id+"'>"+user.username+"</label></td>"+
"</tr>"); 
});
}
function $_GET(key) {
    var p = window.location.search;
    p = p.match(new RegExp(key + '=([^&=]+)'));
    return p ? p[1] : 0;
}
	
	
	function check(object,errors)
	{
		if (object.password!=object.confirmPassword)
	{
		errors.push("Пароли пользователя "+object.username+" не совпадают!");
	}
	if (object.username.length==0){
		errors.push("Логин пользователя "+object.username+" не должен быть пустым!");
	}
	}