<?php
/* El siguiente código devolverá los restaurantes favoritos del username actual
 * Los detalles serán leidos mediante un HTTP Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['usuario_email']) && !empty($_POST['usuario_email'])) {
	include('../android_connect/db_connect.php');	
	connect($con);
	mysqli_set_charset($con, "utf8");
	mysqli_select_db($con, DB_DATABASE);

	$email = $_POST['usuario_email'];
	
	// get all favorites from a given user
	
	//$result = mysqli_query($con, "SELECT Favoritos.restaurante_id, Restaurantes.nombre"
	$result = mysqli_query($con, "SELECT Restaurantes.id_restaurante, Restaurantes.nombre,"
		. " Restaurantes.ciudad, Restaurantes.provincia, Restaurantes.telefono, Restaurantes.tipo_cocina,"
		. " Restaurantes.valoracion"
		. " FROM Favoritos, Restaurantes"
		. "	WHERE Favoritos.restaurante_id = Restaurantes.id_restaurante"
		. " AND Favoritos.usuario_email='$email'"
		. " ORDER BY Favoritos.fecha_favorito DESC");
 
	// comprobamos que el resultado no es vacio
	if(mysqli_num_rows($result) > 0) {
		// nodo restaurantes
		$response['restaurantes'] = array();
		
		while($row = mysqli_fetch_array($result)) {
			// array temporal para cada uno de los restaurantes
			$restaurante = array();
			$restaurante['id'] = $row['id_restaurante'];
			$restaurante['nm'] = $row['nombre'];
			$restaurante['cd'] = $row['ciudad'] . ' (' . $row['provincia'] . ')';
			$restaurante['tl'] = $row['telefono'];
			$restaurante['cn'] = $row['tipo_cocina'];
			$restaurante['vl'] = $row['valoracion'];
			
			// push cada restaurante en el array final
			array_push($response['restaurantes'], $restaurante);
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