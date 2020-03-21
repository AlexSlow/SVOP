//поместить токен csrf
 $.ajaxSetup({
        headers: {
            'X-Csrf-Token':token
        }
    });

 var content;
 $('#addModal').on('show.bs.modal', function (event) {
  var button = $(event.relatedTarget)
  // извлечь информацию из атрибута data-content
 $('#modal_form')[0].reset();
   content= button.data('content')   
  if (content=="add")
  {
  $("#modal_title").text(head_add_title);
}
  else
  {
	  console.log("Редактирование");
   $("#modal_title").text(head_red_title);
   $("#content_table tbody tr").each(function(){
	if ($(this).children("td:first-child").children("input").prop('checked')) //Если строка выбрана
	{
		let id=$(this).children("td:eq(0)").children("input").val();
		let longName=$(this).children("td:eq(1)").children("label").text();
		let shortName=$(this).children("td:eq(2)").children("label").text();
		$("#modal_id").attr("value",id);
		$("#modal_longName").attr("value",longName);
		$("#modal_shortName").attr("value",shortName);
		console.log(id+" "+longName);
		return false;
	}
  });//конец else

  }
   });
  
   
