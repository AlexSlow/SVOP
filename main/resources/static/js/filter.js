$('#filter_feild').keyup(function(){
  let text = $('#filter_feild').val();
if (text!="")
{
$('tbody tr').each(function(){
$(this).hide();//Скрываем сначала все

});
$('tbody tr:last-child').show();



$('tbody td').each(function(){
$(this).css("color","black");

if($(this).text().toLowerCase().indexOf(text.toLowerCase())+1) {
    //Проверка вхождения
//console.log($(this).text());

$(this).parent("tr").show();
$(this).css("color","red");
}
});


}else{
$('tbody tr').each(function(){
$(this).show();

});

$('tbody td').each(function(){
	$(this).css("color","black");
});

}

});