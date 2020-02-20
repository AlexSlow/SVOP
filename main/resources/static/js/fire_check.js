$('#maincheck').click(function(){
    if ($(this).is(':checked')){
        $('table input:checkbox').prop('checked', true);
    } else {
        $('table input:checkbox').prop('checked', false);
    }
});