//поместить токен csrf

 $.ajaxSetup({
        headers: {
            'X-Csrf-Token':token
        }
    });
	function clean()
	{
		 $("#modal_table").find ("tr").remove();
	}
	var content;
 $('#addModal').on('show.bs.modal', function (event) {
  // получить кнопку, которая его открыло
  var button = $(event.relatedTarget);
  var airports=$("#airports");
  // извлечь информацию из атрибута data-content
   content= button.data('content') 
  // вывести колонки таблицы
  clean();
  if (content=="add")
  {
	$("#modal_title").text(head_add_title);
	$("#add_buttons").removeClass("d-none");
	addRow(null,null);
		
}
 else 
  {
	$("#modal_table thead tr ").prepend("<th></th>");
   $("#modal_title").text(head_red_title);
   $("#add_buttons").addClass("d-none");
   $("#content_table tbody tr").each(function(){
	if ($(this).children("td:first-child").children("input").prop('checked')) //Если строка выбрана
	{
	let id=$(this).children("td:first-child").children("input").val();
	
	
	let name=$(this).children("td:eq(1)").children("label").text();
	//console.log(name);
	let array=name.split("—");
	//let array=name.split("-");
	console.log(array);
	addRow(id,array);
	}
	});
		
}
  });
 
  
    //Добавить строку
   $("#add_str").on('click',addRow);
   function addRow(checkbox_id,array)
   {

	   $("#modal_table tbody").append("<tr></tr>");
	   $("#modal_table tbody tr:last-child").append("<td style='display:none' id='id'>"+checkbox_id+"</td>");
	  $("#modal_table tbody tr:last-child").append("<td>");
	   $("#modal_table tbody tr:last-child td:last-child")
	   .append('<input type="button" class="addAiroport" value="Добавить"/><input type="button" class="removeAirport" value="Убрать"/>');
	   
	   if (array==null)
	   {
	   for (let i=0;i<2;i++)
  {
	$("#modal_table tbody tr:last-child td:last-child").append(airports.cloneNode(true));
  }
	   }else{
		    
		   array.forEach(function(item, i, array) {
  //alert( i + ": " + item + " (массив:" + arr + ")" );
  $("#modal_table tbody tr:last-child td:last-child").append(airports.cloneNode(true));
  let val  = item;
  //console.log(val);
  $("#modal_table tbody tr:last-child td:last-child select:last-child  :contains('"+val+"')").attr('selected', 'true').text(val);
	
	});
	   }//конец else
	   
	    $("#modal_table tbody tr:last-child td:last-child").append("</td>");
   };
   //Убрать строку
   $("#remove_str").on('click', function(e) {
   $("#modal_table tbody tr:last-child").remove();

   });
   
   //Добавить Аэропорт
   $(function addAiroport() {
        $(document).on('click touchstart', '.addAiroport', function(){ 
           $(this).parent("td").append(airports.cloneNode(true));
        });
    });
	$(function removeAirport() {
        $(document).on('click touchstart', '.removeAirport', function(){ 
		if ( $(this).parent("td").children("select").length>2)
          $(this).parent("td").children("select:last-child").remove();
        });
    });
	
	$("#save").on('click', function(e) {
		var ajax_array =[];
		var ajax_object={};
		 $("#modal_table tbody tr").each(function(){ 
		 let route ="";
			 $(this).children("td").children("select").each(function(){
				 //console.log("1");
				route=route+"/"+$(this).val();
		 }); 
		 ajax_object["id"]=$(this).find("#id").text();
		 ajax_object["route"]=route;
		 
		 ajax_array.push(ajax_object);
		 });
		 console.log(ajax_array);
		 //send_request();
		
	});
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
  
   
  
 
