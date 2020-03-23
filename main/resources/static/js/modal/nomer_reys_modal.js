//поместить токен csrf
 $.ajaxSetup({
        headers: {
            'X-Csrf-Token':token
        }
    });
 var content;
 $('#addModal').on('show.bs.modal', function (event) {
	   let aircompany=$("#aircompany").val();
  if (aircompany=="")
  {  

	alert(error_aircompany_msg);	
	$('.modal').modal('hide');
	return;
  }

$("#modal_table tbody").empty();
  // получить кнопку, которая его открыло
  var button = $(event.relatedTarget)
  // извлечь информацию из атрибута data-content
   content= button.data('content') 
  if (content=="add")
  {
  $("#modal_title").text(head_add_title);
  addEmptyRow();
}
  else
  {
  //Добавим колонку под id
   $("#modal_title").text(head_red_title);
    $("#content_table tbody tr").each(function(){
	if ($(this).children("td:first-child").children("input").prop('checked')) //Если строка выбрана
	{
	let id=$(this).children("td:first-child").children("input").val();
	let nomer=$(this).children("td:eq(1)").children("label").text();
	let type=$(this).children("td:eq(2)").text();
	$("#modal_table tbody ").append("<tr><td class='d-none'>"+id+"</td><td><input type='text' value='"+nomer+"' class='form-control'/></td>"+getSelect(type)+"</tr>");  
	}
	});

  }//конец else
   });
 
  function addEmptyRow(){
	$("#modal_table tbody ").append("<tr><td class='d-none'>"+null+"</td><td><input type='text' class='form-control'/></td>"+getSelect(null)+"</tr>");  
  }
  
    //Добавить строку
   $("#add_str").on('click', function(e) {
   addEmptyRow();
 }); 
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
		 ajax_object["nomer"]=$(this).children("td:eq(1)").children("input").val();
		 ajax_object["type"]=$(this).children("td:eq(2)").children("select").val();
		 ajax_array.push(ajax_object);
	});
	send_request(ajax_array,service_api_save);
   });
   function send_request(ajax_array,adress)
  { 
  let aircompany=$("#aircompany").val();
  if (aircompany!="")
  {  
let wrap_object={};
wrap_object["nomers"]=ajax_array;

 wrap_object["aicompany_id"]=aircompany;
 //console.log(ajax_array);
 console.log(JSON.stringify(wrap_object));
  $.ajax({
  type: "POST",
  url: adress,
	data: JSON.stringify(wrap_object),
	contentType: 'application/json',
	success: function(data) {
   // alert(data);
   $("#input_select").val(aircompany);
	//location.reload();
	$('#contentForm').submit();
  }
});
}else{
	  alert(error_aircompany_msg);
  }

   }
   
   function getSelect(selected)
   {
	   if (selected==null)
	   {
	   return "<td><select class='form-control'><option>Prilet</option><option>Vilet</option><option>Tranzit</option></select></td>";
	   }else if (selected=="Prilet"){
		return "<td><select class='form-control'><option selected>Prilet</option><option>Vilet</option><option>Tranzit</option></select></td>";
	   }
	   else if (selected=="Vilet"){
		return "<td><select class='form-control'><option>Prilet</option><option selected>Vilet</option><option>Tranzit</option></select></td>";
	   }
	   else if (selected=="Tranzit"){
		return "<td><select class='form-control'><option>Prilet</option><option selected>Vilet</option><option selected>Tranzit</option></select></td>";
	   }
   }
   
