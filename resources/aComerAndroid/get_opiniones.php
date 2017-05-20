<?php
/* El siguiente código devolverá los restaurantes favoritos del username actual
 * Los detalles serán leidos mediante un HTTP Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['restaurante_id']) && !empty($_POST['restaurante_id'])) {
	include('../android_connect/db_connect.php');	
	connect($con);
	mysqli_set_charset($con, "utf8");
	mysqli_select_db($con, DB_DATABASE);

	$id = $_POST['restaurante_id'];
	
	// get all favorites from a given user
	// SQL to select the opinions by restaurant id
	$sql = "SELECT restaurante_id, usuario_email, opinion, fecha_opinion, valoracion"
		. " FROM Opiniones"
		. " WHERE restaurante_id='$id'"
		. " AND revisar='N'";
	
	$result = mysqli_query($con, $sql);
 
	// comprobamos que el resultado no es vacio
	if(mysqli_num_rows($result) > 0) {
		// nodo restaurantes
		$response['opiniones'] = array();
		
		while($row = mysqli_fetch_array($result)) {
			// array temporal para cada uno de los restaurantes
			$opinion = array();
			$opinion['id'] = $row['restaurante_id'];
			$opinion['us'] = $row['usuario_email'];
			$opinion['da'] = $row['fecha_opinion'];
			$opinion['op'] = $row['opinion'];
			$opinion['vl'] = $row['valoracion'];
			
			// push cada restaurante en el array final
			array_push($response['opiniones'], $opinion);
		}
		
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