<?php
// process.php


include 'conectionBBDD.php';

//Creamos un acceso a la BBDD
$instancia = conectionBBDD::getInstance();
$instancia -> openConectionBBDD();

//Variables del JavaScript
    $restaurante_id = $_POST['restaurante_id'];
    $usuario_email = $_POST['usuario_email'];
    $revisar ="N";

   $instancia -> setValidacion($restaurante_id,$usuario_email,$revisar);

