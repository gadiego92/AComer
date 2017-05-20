<?php
/* El siguiente código creará un nuevo usuario en la tabla de usuarios
 * Los detalles serán leidos mediante un HTTP Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['email']) && isset($_POST['password']) && isset($_POST['tipo_usuario']) &&
		isset($_POST['nombre']) && isset($_POST['apellido']) && isset($_POST['fecha_nacimiento']) &&
		isset($_POST['telefono']) && isset($_POST['ciudad']) && isset($_POST['provincia']) && isset($_POST['pais']) &&
		isset($_POST['codigo_postal'])) {
 
	if (!empty($_POST['email']) && !empty($_POST['password']) && !empty($_POST['tipo_usuario']) &&
		!empty($_POST['nombre']) && !empty($_POST['apellido']) && !empty($_POST['fecha_nacimiento']) &&
		!empty($_POST['telefono']) && !empty($_POST['ciudad']) && !empty($_POST['provincia']) && !empty($_POST['pais']) &&
		!empty($_POST['codigo_postal'])) {
 
		include('../android_connect/db_connect.php');	
		connect($con);
		mysqli_set_charset($con, "utf8");
		mysqli_select_db($con, DB_DATABASE);
	 
		$email = strtolower($_POST['email']);
		$password = $_POST['password'];
		$tipo_usuario = $_POST['tipo_usuario'];
		$nombre = $_POST['nombre'];
		$apellido = $_POST['apellido'];
		$fecha_nacimiento = $_POST['fecha_nacimiento'];
		$telefono = $_POST['telefono'];
		$ciudad = $_POST['ciudad'];
		$provincia = $_POST['provincia'];
		$pais = $_POST['pais'];
		$codigo_postal = $_POST['codigo_postal'];
		// Fecha actual con formato AAAA-MM-DD
		$usuario_desde = date("Y-m-d");
		$hash = password_hash($password, PASSWORD_DEFAULT);
	
		// mysql inserting a new row
		$result = mysqli_query($con, "INSERT INTO Usuarios VALUES ('$email', '$hash',"
			. " '$tipo_usuario', '$nombre', '$apellido', '$fecha_nacimiento', '$telefono', '$ciudad', '$provincia',"
			. " '$pais', '$codigo_postal', '$usuario_desde')");
		
		// check if row inserted or not
		if ($result) {
			// successfully inserted into database
			$response["success"] = 1;
			$response["message"] = "Register complete.";
			
		} else {
			// failed to insert row
			$response["success"] = 0;
			$response["message"] = "Oops! An error occurred.";
		}
		
		// cerrar conexion
		mysqli_close($con);
	} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is empty";
	}
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing";
}

// echoing JSON response
echo json_encode($response);

?>