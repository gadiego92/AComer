$(document).ready( function() {
 	//Peticion de comentarios
	$.get("../php/getComentarios.php", function(data) {
		//console.log("data" + data);

		//var select = document.getElementById("elecccionSelect"); //Div donde guardamos los destinos
		
		var comentarios = JSON.parse(data); //Datos obtenidos get
		var hasta = comentarios.length;
		var i = 0;
		comentarios.forEach((comentarios) => {
			var usuario_email = comentarios.usuario_email;
			var restaurante_id = comentarios.restaurante_id;
			var opinion = comentarios.opinion;
			var fecha_opinion = comentarios.fecha_opinion;
/*
			var opt = document.createElement('option'); //Tipo de html que queremos crear
			opt.value = destino.ciudad; //Obtenemos la ciudad del objeto
			//console.log("destino ciudad" + destino.ciudad);
			opt.innerHTML = destino.ciudad; 
			select.appendChild(opt); //Lo añadimos al DIV seleccionado */

		

  		row = $('<tr><td id="boton'+i+'"></td><td>'+usuario_email+'</td><td>'+restaurante_id+'</td><td>'+opinion+'</td><td>'+fecha_opinion+'</td></tr>'); //create row
   		$('#tab_logic').append(row);

   		 $('<input />', {
   		 	class: "botonValidar",
            type : 'button',
            id: 'id' + i,
            name: i,
            value: "Validar",
        }).appendTo('#boton'+i+'');
   		 i++;

	});


$( ".botonValidar" ).click(function() {
  console.log("validando comentario");
 
});

});













});


