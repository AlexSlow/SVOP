//Если нажать на чекбокс записи
 var idRole;
  $(function selectReadCh() {
        $(document).on('click touchstart', '.writeCh',fireR);
    });
	$(function selectReadCh() {
        $(document).on('click touchstart', '.readCh',fireW);
    });
	//Зажечь чекбокс на чтение
	function fireR(){ 
		//Получить индекс
		if ($(this).is(':checked')){
		let index=$(this).attr("id");
		let readCh=$('.readCh:eq('+index+')');
		 readCh.prop('checked', true);
		}
		}
		
		//Зажечь чекбокс на запись
	function fireW(){ 
		//Получить индекс
		if (!($(this).is(':checked'))){
		let index=$(this).attr("id");
		let readCh=$('.writeCh:eq('+index+')');
		 readCh.prop('checked', false);
		}
		}
		
		
 //поместить токен csrf
 $.ajaxSetup({
        headers: {
            'X-Csrf-Token':token
        }
    });
 var content;
 $('#addModal').on('show.bs.modal', function (event) {
$("#modal_table tbody").empty();
$("#permissions tbody").empty();
  //Начальная инициализация
  var checked = [];
$('input:checkbox:checked').each(function() {
	checked.push($(this).val());
});
if(checked.length>0)
	{
	requestGetRole(checked[0]);
	}
	});
	
	
		 	function requestGetRole(id)
{
idRole=id;
	console.log("id role "+id)
 	 $.ajax({
  type: "POST",
  url: host+"/svop/api/roles/getRole",
	data: JSON.stringify(id),
	contentType: 'application/json',
	success: function(data) {
	//console.log(data);
	$("#modal_table tbody ").append("<tr><td class='d-none'>"+id+"</td><td>"+data+"</td></tr>");
	requestGetPermissions(id);
  }
});
}

	 	function requestGetPermissions(id)
{

 	 $.ajax({
  type: "POST",
  url: host+"/svop/api/roles/getPermissionsByRole",
	data: JSON.stringify(id),
	contentType: 'application/json',
	success: function(data) {
	console.log(data);
	//Перебор резульатата 
	// переберём массив data
	let checkedR="";
	let checkedW="";
$.each(data,function(index,value){
 
  if (value.readAccess)
   checkedR="checked";
   else  checkedR="";
   if (value.writeAccess)
   checkedW="checked";
   else  checkedW="";
   

  //выведем индекс и значение массива в консоль
$("#permissions tbody ").append("<tr><td class='d-none'>"+value.permissionId+"</td><td>"+value.name+"</td>"+
"<td><input type='checkbox' id='"+index+"'  class='readCh' "+checkedR+"/></td>"+
"<td><input type='checkbox' id='"+index+"' class='writeCh' "+checkedW+"/></td></tr>");

});
  }
});
}

 $("#save").on('click', function(e) {
  requestSave();
   });

		 	function requestSave()
{
//Формируем объект
let requestObj={}
let list=[];
$("#permissions tbody tr").each(function(){
let object={};
object["permissionId"]=$(this).children("td:eq(0)").text();
object["readAccess"]=$(this).children("td:eq(2)").children("input").is(':checked');
object["writeAccess"]=$(this).children("td:eq(3)").children("input").is(':checked');
list.push(object);
});
requestObj["idRole"]=idRole;
requestObj["list"]=list;
console.log(requestObj);
 	 $.ajax({
  type: "POST",
  url: host+"/svop/api/roles/save",
	data: JSON.stringify(requestObj),
	contentType: 'application/json',
	success: function(data) {
	console.log(data);
	$('#addModal').modal('hide');
  }
});
}
