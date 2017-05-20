<?php
/* El siguiente código eliminará un restaurantes favorito del usuario actual
 * Los detalles serán leidos mediante un HTTP Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['usuario_email']) && !empty($_POST['usuario_email']) 
	&& isset($_POST['restaurante_id']) && !empty($_POST['restaurante_id'])) {
		
	include('../android_connect/db_connect.php');	
	connect($con);
	mysqli_set_charset($con, "utf8");
	mysqli_select_db($con, DB_DATABASE);

	$email = $_POST['usuario_email'];
	$id_restaurante = $_POST['restaurante_id'];
	
	// delete a favorite
	// sql to delete a record
	$sql = "DELETE FROM FAVORITOS WHERE restaurante_id='$id_restaurante' AND usuario_email='$email'";
	
	if (mysqli_query($con, $sql)) {
		// success
		$response['success'] = 1;
	} else {
		// failed to insert row
		$response["success"] = 0;
		$response["message"] = "No restaurants found";
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