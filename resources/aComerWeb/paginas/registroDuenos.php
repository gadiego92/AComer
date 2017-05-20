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
				// El siguiente código creará un nuevo username en la tabla de usuarios
				 
				// check for required fields
				if (isset($_POST['email']) && isset($_POST['password']) && isset($_POST['tipo_usuario']) &&
						isset($_POST['nombre']) && isset($_POST['apellido']) && isset($_POST['fecha_nacimiento']) &&
						isset($_POST['telefono']) && isset($_POST['ciudad']) && isset($_POST['provincia']) && isset($_POST['pais']) &&
						isset($_POST['codigo_postal'])) {
					// check for empty fields
					if (!empty($_POST['email']) && !empty($_POST['password']) && !empty($_POST['tipo_usuario']) &&
						!empty($_POST['nombre']) && !empty($_POST['apellido']) && !empty($_POST['fecha_nacimiento']) &&
						!empty($_POST['telefono']) && !empty($_POST['ciudad']) && !empty($_POST['provincia']) && !empty($_POST['pais']) &&
						!empty($_POST['codigo_postal'])) {
				 
						include('../../android_connect/db_connect.php');	
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
							// successfully inserted into database. Register complete
							$response = 1;
						} else {
							// failed to insert row. Oops! An error occurred
							$response = 0;
						}
						
						// cerrar conexion
						mysqli_close($con);
					} else {
						// required field is empty. Required field(s) is empty
						$response = 0;
					}
				} else {
					// required field is missing. Required field(s) is missing
					$response = 0;
				}

				if($response == 1) {
					echo '<p>EXITO. Usuario creado.</p>';
					echo '<a href="../index.html">Iniciar Sesión</a>';
				} else {
					echo '<p>ERROR. El username no ha podido crearse.</p>';
					echo '<a href="../index.html">Reintentar registro</a>';
				}
			?>
		</main>
		
		<footer id="footer"></footer>
	</body>
</html>