<?php
/* El siguiente código, dado un id, devolverá un restaurante
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
	
	// get a restaurante by the ID
	$result = mysqli_query($con, "SELECT id_restaurante, nombre,"
		. " ciudad, provincia, telefono, tipo_cocina,"
		. " valoracion"
		. " FROM Restaurantes"
		. "	WHERE id_restaurante='$id'");
 
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
			
			// calculamos la media de la valoracion de cada restaurante
			$sqlValoracion = "SELECT AVG(valoracion)"
				. " FROM Opiniones"
				. " WHERE restaurante_id=$id"
				. " AND revisar='N'";
			
			// get all favorites from a given user
			$resultValoracion = mysqli_query($con, $sqlValoracion);
			
			// check the query result
			if ($resultValoracion) {
				// determinar el número de filas del resultado
				if (mysqli_num_rows($resultValoracion) == 1) {
					$rowValoracion = mysqli_fetch_array($resultValoracion);
					
					// si obtenemos cero filas la valoracion de ese restaurante sera cero
					if($rowValoracion[0] == NULL) {
						$restaurante['vl'] = '0';
					} else {
						$restaurante['vl'] = $rowValoracion[0];
					}				
					
					// success
					$response['successValoracion'] = 1;
				} else {
					// failed query
					$response["successValoracion"] = 0;
					$response["messageValoracion"] = "Valoration failed";
				}

				// free the memory associated with the result
				mysqli_free_result($resultValoracion);
			} else {
				// failed to insert row
				$response["successValoracion"] = 0;
				$response["messageValoracion"] = "Oops! An error occurred.";
			}
			
			// push cada restaurante en el array final
			array_push($response['restaurantes'], $restaurante);
		}
		
		// free the memory associated with the result
		mysqli_free_result($result);
		
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