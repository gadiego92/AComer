<?php 
class conectionBBDD{
   private static $instance;
   private static $conexion;

   private function __construct(){}

//Instanciamos la base de datos
   public static function getInstance(){
      if (!self::$instance instanceof self){
         self::$instance = new self;
      }
      return self::$instance;
   }

   //Conectamos a la bbdd
   function openConectionBBDD(){
      $server = 'localhost';
      $username = 'android';
      $password = '1234';
      $database_name = 'a_comer_db';
      
      $this->conexion = new mysqli($server,$username,$password,$database_name);

      //Comprobamos si se ha creado la conexcion 
      if ($this->conexion->connect_error) { 
         alert('Error de Conexión con la base de datos');
      } else {
        
      }

   }
   
   //Cerramos conexcion
   function closeConectionBBDD(){
      $this->conexion->close();
   }

   //Query obtener destinos 
   function getComentarios(){
      $sql= "SELECT usuario_email, restaurante_id, opinion, fecha_opinion from opiniones";
      $result = $this->conexion->query($sql);
      return $result;
   }



}
?>