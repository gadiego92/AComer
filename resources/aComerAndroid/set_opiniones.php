<?php
/* El siguiente código insertará una opinión en la tabla opiniones
 * Los detalles serán leidos mediante un HTTP Request
 */
 
// check for required fields
if (isset($_POST['usuario_email']) && !empty($_POST['usuario_email'])
	&& isset($_POST['restaurante_id']) && !empty($_POST['restaurante_id'])
	&& isset($_POST['opinion']) && !empty($_POST['opinion'])
	&& isset($_POST['numEstrellas']) && !empty($_POST['numEstrellas'])) {
		
	include('../android_connect/db_connect.php');	
	connect($con);
	mysqli_set_charset($con, "utf8");
	mysqli_select_db($con, DB_DATABASE);

	$email = $_POST['usuario_email'];
	$id = $_POST['restaurante_id'];
	$opinion = $_POST['opinion'];
	$estrellas = $_POST['numEstrellas'];
	$fechaActual = date("Y-m-d H:i:s");
	$revisar = 'S';
	
	// get all favorites from a given user
	// SQL to insert the opinion
	$sql = "INSERT INTO Opiniones VALUES ($id, '$email', '$opinion', '$fechaActual', '$estrellas', '$revisar')";
	
	if (mysqli_query($con, $sql)) {
		// success insert
		$response['success'] = 1;
	} else {
		// failed insert
		// if the user already gave an opinion
		// update the opinion
		
		// SQL to update the opinion
		$sqlUpdate = "UPDATE Opiniones SET opinion='$opinion',"
			. " fecha_opinion='$fechaActual',"
			. " valoracion='$estrellas',"
			. " revisar='$revisar'"
			. " WHERE restaurante_id=$id"
			. " AND usuario_email='$email'";
		
		if (mysqli_query($con, $sqlUpdate)) {
			// success update
			$response['success'] = 2;
		} else {
			// insert and update failed
			$response["success"] = 0;
			$response["message"] = "Insert & Update failed";
		}
	}
	
	// cerrar conexion
	mysqli_close($con);
	
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Required field(s) is missing or empty.";
}

// echoing JSON response
echo json_encode($response);

?>