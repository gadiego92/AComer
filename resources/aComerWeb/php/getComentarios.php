<?php
    include 'conectionBBDD.php';
    include 'comentario.php';

	//acceso a la BBDD
    $instancia = conectionBBDD::getInstance();
    $instancia -> openConectionBBDD();

    $result = $instancia -> getComentarios();

    $comentarios = array();

    //guardados todos los datos como object destino
    while($row = $result -> fetch_array()){
        array_push($comentarios, new Comentario($row['usuario_email'],$row['restaurante_id'],$row['opinion'],$row['fecha_opinion'],$row['revisar']));
    }
    
    // return de datos via AJAX
    echo json_encode($comentarios);
?>