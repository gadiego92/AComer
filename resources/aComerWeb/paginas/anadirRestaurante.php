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
				if (isset($_POST['nombre']) && isset($_POST['telefono']) && isset($_POST['direccion']) && isset($_POST['ciudad'])
					&& isset($_POST['provincia']) && isset($_POST['pais']) && isset($_POST['codigo_postal']) && isset($_POST['tipo_cocina'])) {
					// check for empty fields
					if (!empty($_POST['nombre']) && !empty($_POST['telefono']) && !empty($_POST['direccion']) && !empty($_POST['ciudad'])
						 && !empty($_POST['provincia']) && !empty($_POST['pais']) && !empty($_POST['codigo_postal']) && !empty($_POST['tipo_cocina'])) {

						include('../../android_connect/db_connect.php');	
						connect($con);
						mysqli_set_charset($con, "utf8");
						//mysqli_select_db($con, DB_DATABASE);
						
						$nombre = $_POST['nombre'];
						$telefono = $_POST['telefono'];
						$direccion = $_POST['direccion'];
						$ciudad = $_POST['ciudad'];
						$provincia = $_POST['provincia'];
						$pais = $_POST['pais'];
						$codigo_postal = $_POST['codigo_postal'];
						$tipo_cocina = $_POST['tipo_cocina'];
						$valoracion = 5;
						$fecha_registro = date("Y-m-d");
						$usuario_email = $_SESSION['email'];
						
						// mysql inserting a new row
						$result = mysqli_query($con, "INSERT INTO Restaurantes (nombre, telefono, direccion, ciudad, provincia, pais,"
							. " codigo_postal, tipo_cocina, valoracion, fecha_registro, usuario_email)"
							. " VALUES ('$nombre', '$telefono', '$direccion', '$ciudad', '$provincia', '$pais',"
							. " '$codigo_postal', '$tipo_cocina', '$valoracion', '$fecha_registro', '$usuario_email')");
						
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
					echo '<p>EXITO. Restaurante creado.</p>';
					echo '<a href="./menuPrincipal.php">Volver a la página principal</a>';
				} else {
					echo '<p>ERROR. El restaurante no ha podido crearse.</p>';
					echo '<a href="./menuPrincipal.php">Volver a intentar</a>';
				}
			?>
		</main>
		
		<footer id="footer"></footer>
	</body>
</html>