<?php
//Objeto 
	class Comentario{

		public $usuario_email;
		public $restaurante_id;
		public $opinion;
		public $fecha_opinion;

		public function __construct($usuario_email, $restaurante_id, $opinion,$fecha_opinion){
			$this -> usuario_email = $usuario_email;
			$this -> restaurante_id = $restaurante_id;
			$this -> opinion = $opinion;
			$this -> fecha_opinion = $fecha_opinion;
		}
	}
?>