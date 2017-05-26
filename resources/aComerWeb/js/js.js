$(document).ready( function() {
 	//Peticion de comentarios
	$.get("../php/getComentarios.php", function(data) {
		var comentarios = JSON.parse(data); //Datos obtenidos get
		var hasta = comentarios.length;
		var i = 0;
		comentarios.forEach((comentarios) => {
			var usuario_email = comentarios.usuario_email;
			var restaurante_id = comentarios.restaurante_id;
			var opinion = comentarios.opinion;
			var fecha_opinion = comentarios.fecha_opinion;
			var revisar = comentarios.revisar;

  		row = $('<tr><td id="boton'+i+'"></td><td>'+usuario_email+'</td><td>'+restaurante_id+'</td><td>'+opinion+'</td><td>'+fecha_opinion+'</td></tr>'); //create row
   		$('#tab_logic').append(row);

   		 $('<input />', {
   		 	class: "botonValidar",
            type : 'button',
            id: 'id' + i,
            name: restaurante_id+"/"+usuario_email,
            value: "Validar",
        }).appendTo('#boton'+i+'');
   		 i++;

	});


$( ".botonValidar" ).click(function() {

	var name = $( this ).attr("name");
 	var res = name.split("/");
 	var restaurante_id = res[0];
 	var usuario_email = res[1];
 	
 	    var formData = {
            'restaurante_id' :  restaurante_id,
            'usuario_email'    : usuario_email,
        };

       //Enviamos un post mediantes ajax con los datos formData que contieen todos los datos
        $.ajax({
            type        : 'POST', 
            url         : '../php/setComentariosValidados.php', //archivo que procesa los datos 
            data        : formData, 
            dataType    : 'json', 
                        encode          : true
        })
           var parentTag = $(this).parent().parent().remove();


 
});

});






});


