<?php
/* El siguiente código logeará a un username
 * Los detalles serán leidos mediante un HTTP Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['email']) && isset($_POST['password']) && isset($_POST['tipo_usuario'])) {
	if (!empty($_POST['email']) && !empty($_POST['password']) && !empty($_POST['tipo_usuario'])) {
 
		include('../android_connect/db_connect.php');	
		connect($con);
		mysqli_set_charset($con, "utf8");
		//mysqli_select_db($con, DB_DATABASE);
	 
		$email = strtolower($_POST['email']);
		$password = $_POST['password'];
		$tipo_usuario = $_POST['tipo_usuario'];
		$hash = password_hash($password, PASSWORD_DEFAULT);
		
		// mysql inserting a new row
		$result = mysqli_query($con, "SELECT password FROM Usuarios WHERE email='$email'"
			. " AND tipo_usuario='$tipo_usuario'");
			
		// check if row inserted or not
		if ($result) {
			// determinar el número de filas del resultado
			if (mysqli_num_rows($result) == 1) {
				
				$row = mysqli_fetch_array($result);
				
				if (password_verify($password, $row[0])) {
					// successfully inserted into database
					$response["success"] = 1;
					$response["message"] = "Password is valid.";
				} else {
					// successfully inserted into database
					$response["success"] = 0;
					$response["message"] = "Invalid password.";
				}
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