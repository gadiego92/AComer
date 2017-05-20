<?php
/* El siguiente código devolverá la informacion personal de un usuario logeado
 * Los detalles serán leidos mediante un HTTP Request
 */
 
// array for JSON response
$response = array();


// check for required fields
if (isset($_POST['email'])) {
	if (!empty($_POST['email'])) {

		include('../android_connect/db_connect.php');	
		connect($con);
		mysqli_set_charset($con, "utf8");
		mysqli_select_db($con, DB_DATABASE);

		$email = $_POST['email'];
		
		// get all favorites from a given user
		$result = mysqli_query($con, "SELECT nombre, apellido, telefono, email"
			. " FROM Usuarios"
			. " WHERE email='$email'");

		// check if row inserted or not
		if ($result) {
			// determinar el número de filas del resultado
			if (mysqli_num_rows($result) == 1) {
				// nodo restaurantes
				$response['usuario'] = array();
				
				$row = mysqli_fetch_array($result);

				// array temporal para cada uno de los restaurantes
				$usuario = array();
				$usuario['nm'] = $row['nombre'];
				$usuario['ap'] = $row['apellido'];
				$usuario['tl'] = $row['telefono'];
				$usuario['em'] = $row['email'];
				
				// push cada restaurante en el array final
				array_push($response['usuario'], $usuario);
				
				// success
				$response['success'] = 1;
			} else {
				// successfully inserted into database
				$response["success"] = 0;
				$response["message"] = "Username doesn't exist.";
			}

			// free the memory associated with the result
			mysqli_free_result($result);
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