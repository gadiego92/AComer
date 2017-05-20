<!DOCTYPE html>
<html lang="es">
	<head>
		<title>A Comer - Inicio</title>
		<meta charset="utf-8">
	</head>
	
	<body id="body">
		<header id="header"></header>
		
		<main id="main">
			<?php
				// El siguiente código creará un nuevo restaurante en la tabla de usuarios

				session_start();
				
				// check for required fields
				if (isset($_POST['restaurantesIdComboBox']) && isset($_POST['nombre']) && isset($_POST['descripcion']) && isset($_POST['precio']) && isset($_POST['disponibilidad'])) {
					// check for empty fields
					if (!empty($_POST['restaurantesIdComboBox']) && !empty($_POST['nombre']) && !empty($_POST['descripcion']) && !empty($_POST['precio']) && !empty($_POST['disponibilidad'])) {

						include('../../android_connect/db_connect.php');	
						connect($con);
						mysqli_set_charset($con, "utf8");
						//mysqli_select_db($con, DB_DATABASE);
						
						$restaurante_id = $_POST['restaurantesIdComboBox'];
						$nombre = $_POST['nombre'];
						$descripcion = $_POST['descripcion'];
						$precio = $_POST['precio'];
						$disponibilidad = $_POST['disponibilidad'];
						$fecha_anadido = date("Y-m-d");
						$usuario_email = $_SESSION['email'];
						
						// mysql inserting a new row
						$result = mysqli_query($con, "INSERT INTO Productos (nombre, descripcion, precio, disponibilidad, fecha_anadido, restaurante_id)"
							. " VALUES ('$nombre', '$descripcion', '$precio', '$disponibilidad', '$fecha_anadido', " . $restaurante_id. ")");
						
						// check if row inserted or not
						if ($result) {
							// successfully inserted into database. Register complete
							$response = 1;
						} else {
							// failed to insert row. Oops! An error occurred
							$response = 0;
						}

						// cerrar conexion
						mysqli_close($con);
					} else {
						// required field is missing. Required field(s) is empty
						$response = 0;
					}
				} else {
					// required field is missing. Required field(s) is missing
					$response = 0;
				}

				if($response == 1) {
					echo '<p>EXITO. Producto creado.</p>';
					echo '<a href="./menuPrincipal.php">Volver a la página principal</a>';
				} else {
					echo '<p>ERROR. El producto no ha podido crearse.</p>';
					echo '<a href="./menuPrincipal.php">Volver a intentar</a>';
				}
			?>
		</main>
		
		<footer id="footer"></footer>
	</body>
</html>