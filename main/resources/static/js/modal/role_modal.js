//id пользователя, которого редактируют
 var idUser;		
 //поместить токен csrf
 $.ajaxSetup({
        headers: {
            'X-Csrf-Token':token
        }
    });
 var content;
 $('#addModal').on('show.bs.modal', function (event) {
$("#modal_table tbody").empty();
$("#addRemoveBtBlock").css("display","none");
  //Начальная инициализация
  var checked = [];
   var names = [];
$('input:checkbox:checked').each(function() {
	checked.push($(this).val());
	let parentRow=$(this).parents("tr");
	names.push(parentRow.children("td:eq(1)").text());
});
if(checked.length>0)
	{
		idUser=checked[0];
	
	$("#modal_title").text(names[0]);
	requestGetRoles(idUser);
	}
	
	
	});
	


	 	function requestGetRoles(id)
{

 	 $.ajax({
  type: "POST",
  url: host+"/svop/api/roles/getAllRolesByUser",
	data: JSON.stringify(id),
	contentType: 'application/json',
	success: function(data) {
	console.log(data);
	//Перебор резульатата 
	
$.each(data,function(index,value){
 let checked="";
 if (value.active){checked="checked";}
  //выведем индекс и значение массива в консоль
$("#modal_table tbody ").append("<tr><td class='d-none'>"+value.id+"</td><td>"+value.name+"</td>"+
"<td><input type='checkbox'  class='readCh' "+checked+"/></td></tr>");

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
$("#modal_table tbody tr").each(function(){
let object={};
object["id"]=$(this).children("td:eq(0)").text();
object["name"]=$(this).children("td:eq(1)").text();
object["active"]=$(this).children("td:eq(2)").children("input").is(':checked');
list.push(object);
});
requestObj["idUser"]=idUser;
requestObj["list"]=list;
console.log(requestObj);
 	 $.ajax({
  type: "POST",
  url: host+"/svop/api/roles/saveUserRoles",
	data: JSON.stringify(requestObj),
	contentType: 'application/json',
	success: function(data) {
	console.log(data);
	$('#addModal').modal('hide');
  }
});
}
