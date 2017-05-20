<!DOCTYPE html>
<html lang="es">
	<head>
		<title>A Comer - Inicio</title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.css">
		<link rel="stylesheet" type="text/css" href="../css/login.css">
		<script src="http://code.jquery.com/jquery-git.min.js"></script>
		<script src="http://code.jquery.com/ui/1.9.2/jquery-ui.js"></script>
		<script src="../bootstrap/js/bootstrap.js"></script>
		<script src="../js/login.js"></script>
	</head>
	
	<body id="body">
		<!--HEADER-->
		<header id="header">
		  <div class="container-fluid" id="logotitle">
			<div class="row">
			  <div class="col-md-3 col-md-offset-3"><img class="img-circle" id="logoacomer" src="../imagens/Logo.png" alt="logo" width="304" height="236"></div>
			  <div class="col-md-6">
				<h1> ACOMER - Registro Restaurantes</h1>
			  </div>
			</div>
		  </div>
		  <div class="container">
			<div class="row">
			  <div class="col-md-12" id="linea"></div>
			</div>
			<!--BOTON CERRAR SESION -->
			<div class="row">
			  <div id="col-md-12">
				<form id="register-form" action="../index.html" method="post" role="form">
					<input class="btn btn-default" id="cerrarSesion" type="submit" tabindex="12" value="Cerrar sesión" />
				</form>
			  </div>
			</div>
			<!--FIN BOTON CERRAR SESION -->
		  </div>
		</header>
		<!--FIN HEADER-->
		
		<main id="main">
			<?php
				// El siguiente código logeará a un usuario

				session_start();
				
				include('../../android_connect/db_connect.php');
				connect($con);
				mysqli_set_charset($con, "utf8");
				//mysqli_select_db($con, DB_DATABASE);
				
				// check for required fields
				if (isset($_POST['email']) && isset($_POST['password']) && isset($_POST['tipo_usuario'])) {
					// check for empty fields
					if (!empty($_POST['email']) && !empty($_POST['password']) && !empty($_POST['tipo_usuario'])) {
						
						$email = strtolower($_POST['email']);
						$password = $_POST['password'];
						$tipo_usuario = $_POST['tipo_usuario'];
						$hash = password_hash($password, PASSWORD_DEFAULT);

						$_SESSION['email'] = $email;
						
						// mysql inserting a new row
						$result = mysqli_query($con, "SELECT password FROM Usuarios WHERE email='$email'"
							. " AND tipo_usuario='$tipo_usuario'");

						// check if row inserted or not
						if ($result) {
							// determinar el número de filas del resultado
							if (mysqli_num_rows($result) == 1) {

								$row = mysqli_fetch_array($result);

								if (password_verify($password, $row[0])) {
									// successfully inserted into database. Password is valid
									$response = 1;
								} else {
									// successfully inserted into database. Invalid password
									$response = 0;
								}
							} else {
								// successfully inserted into database. Username doesn't exist
								$response = 0;
							}

							// free the memory associated with the result
							mysqli_free_result($result);
						} else {
							// failed to insert row. Oops! An error occurred
							$response = 0;
						}

						// cerrar conexion
						//mysqli_close($con);
					} else {
						// required field is missing. Required field(s) is empty
						$response = 0;
					}
				} elseif(isset($_SESSION['email']) && !empty($_SESSION['email'])) {
					// session already started
					$response = 1;
				} else {
					// required field is missing. Required field(s) is missing
					$response = 0;
				}

				// Si el login es correcto o la sesion esta iniciada mostramos la pagina principal
				if($response == 1) {
					echo '<div class="container">';
						echo '<div class="row">';
							echo '<div class="col-md-6 col-md-offset-3">';
								echo '<div class="panel panel-login">';
									echo '<div class="panel-heading">';
										echo '<div class="row">';
											echo '<div class="col-xs-6"><a class="active" id="addrestaurante-form-link" href="#">Añadir Restaurante</a></div>';
											echo '<div class="col-xs-6"><a id="addmenu-form-link" href="#">Añadir Producto</a></div>';
										echo '</div>';
										echo '<hr>';
									echo '</div>';
									echo '<div class="panel-body">';
										echo '<div class="row">';
											echo '<div class="col-lg-12">';
												echo '<form id="addrestaurante-form" action="./anadirRestaurante.php" method="post" role="form" style="display: block;">';													
													echo '<div class="form-group">';
														echo '<input class="form-control" id="nombrerestaurante" type="text" name="nombre" tabindex="1" placeholder="Nombre" required>';
													echo '</div>';													
													echo '<div class="form-group">';
														echo '<input class="form-control" id="direccionrestaurante" type="text" name="direccion" tabindex="2" placeholder="Direccion" required>';
													echo '</div>';													
													echo '<div class="form-group">';
														echo '<input class="form-control" id="ciudadrestaurante" type="text" name="ciudad" tabindex="3" placeholder="Ciudad" required>';
													echo '</div>';													
													echo '<div class="form-group">';
														echo '<input class="form-control" id="provinciarestaurante" type="text" name="provincia" tabindex="4" placeholder="Provincia" required>';
													echo '</div>';													
													echo '<div class="form-group">';
														echo '<input class="form-control" id="paisrestaurante" type="text" name="pais" tabindex="5" placeholder="Pais" required>';
													echo '</div>';													
													echo '<div class="form-group">';
														echo '<input class="form-control" id="codigopostalrestaurante" type="text" name="codigo_postal" tabindex="6" placeholder="Codigo Postal" required>';
													echo '</div>';													
													echo '<div class="form-group">';
														echo '<input class="form-control" id="tipococinarestaurante" type="text" name="tipo_cocina" tabindex="7" placeholder="Tipo de cocina" required>';
													echo '</div>';													
													echo '<div class="form-group">';
														echo '<input class="form-control" id="telefonorestaurante" type="text" name="telefono" tabindex="8" placeholder="Telefono" required>';
													echo '</div>';
													echo '<div class="form-group">';
														echo '<div class="row">';
															echo '<div class="col-sm-6 col-sm-offset-3">';
																echo '<input class="form-control btn btn-login" id="login-submit" type="submit" name="login-submit" tabindex="9" value="Crear Restaurante">';
															echo '</div>';
														echo '</div>';
													echo '</div>';
												
												echo '</form>';
												echo '<form id="addmenu-form" action="./anadirProducto.php" method="post" role="form" style="display: none;">';
												
												echo '<div class="form-group">';
												// mysql inserting a new row
												$result = mysqli_query($con, "SELECT id_restaurante, nombre FROM Restaurantes WHERE usuario_email='" . $_SESSION['email'] . "'");
		
												// check if row inserted or not
												if ($result) {
													$numeroRestaurantes = mysqli_num_rows($result);
													if ($numeroRestaurantes > 0) {
														echo '<label class="control-label" for="restaurante">Restaurante</label>';
														echo '<select class="form-control" id="restaurantesIdComboBox" name="restaurantesIdComboBox" tabindex="1">';
														// determinar el número de filas del resultado
														while ($row = mysqli_fetch_array($result)) {
															// el indice cero es el id del restaurante y el 1 el nombre
															echo '<option value="' . $row[0] . '">' . $row[1] . '</option>';
														}

														echo '</select>';
													} else {
														echo '<label class="control-label" for="restaurante">¡No tiene ningun restaurante! Cree su primer restaurante</label>';
													}
													// free the memory associated with the result
													mysqli_free_result($result);
												} else {
													// failed to insert row. Oops! An error occurred
													$response = 0;
												}

												// cerrar conexion
												mysqli_close($con);
												
												echo '</div>';
												
													echo '<div class="form-group">';
														echo '<label class="control-label" for="nombreproducto">Nombre</label>';
														echo '<input class="form-control" id="nombreproducto" type="text" name="nombre" tabindex="1" placeholder="Nombre" required>';
													echo '</div>';
													echo '<div class="form-group">';
														echo '<label class="control-label" for="descripcionr">Descripcion</label>';
														echo '<div>';
															echo '<textarea class="form-control" id="descripcionrproducto" name="descripcion" tabindex="2" rows="3" required></textarea>';
														echo '</div>';
													echo '</div>';
													echo '<div class="form-group">';
														echo '<label class="control-label" for="precioproducto">Precio(€)</label>';
														echo '<input class="form-control" id="precioproducto" type="text" name="precio" tabindex="3" placeholder="Precio" value="00,00" required>';
													echo '</div>';
													echo '<div class="form-group">';
														echo '<label class="control-label" for="disponibilidadproducto">Disponibilidad(S/N)</label>';
														echo '<input class="form-control" id="disponibilidadproducto" type="text" name="disponibilidad" tabindex="4" placeholder="Disponibilidad" required>';
													echo '</div>';
													echo '<div class="form-group">';
														echo '<div class="col-sm-6 col-sm-offset-3">';
															if($numeroRestaurantes > 0)  {
																echo '<input class="form-control btn btn-register" id="anadirproducto-submit" type="submit" name="anadirproducto" tabindex="5" value="Añadir Producto">';
															} else {
																echo '<input disabled class="form-control btn btn-register" id="anadirproducto-submit" type="submit" name="anadirproducto" tabindex="5" value="Añadir Producto">';
															}
														echo '</div>';
													echo '</div>';
												echo '</form>';
											echo '</div>';
										echo '</div>';
									echo '</div>';
								echo '</div>';
							echo '</div>';
						echo '</div>';
					echo '</div>';
				} else {
					echo '<p>ERROR. Usuario y/o contraseña incorrectos.</p>';
					echo '<a href="../index.html">Volver a Iniciar Sesión</a>';
				}
			?>
		</main>
		<br><br><br><br><br><br>
		<!--MODAL -->
		<div class="modal fade" id="myModal" role="dialog">
		  <div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-content">
			  <div class="modal-header">
				<button class="close" type="button" data-dismiss="modal">×</button>
				<h4 class="modal-title">Ayudar - ACOMER WEB</h4>
			  </div>
			  <div class="modal-body">
				<p id="p-modal">Desde el "Menú Principal" podrás añadir tus restaurantes a la base de datos de la aplicación, ademñas de los productos que oferte tu restaurante.</p>
				<p id="p-modal">El diseño es bastante intuitivo, dispone de dos formularion, uno para añadir restaurantes y otro productos.</p>
			  </div>
			  <div class="modal-footer">
				<button class="btn btn-default" type="button" data-dismiss="modal">Close		</button>
			  </div>
			</div>
		  </div>
		</div>
		<!--FIN MODAL -->
		
		<!--FOOTER -->
		<footer id="footer">
		  <div class="container-fluid">
			<div class="row">
			  <div class="col-md-12" id="linea"></div>
			</div>
		  </div>
		  <div class="container-fluid" id="footer1">
			<div class="row">
			  <div class="col-md-4" id="ayuda">
				<div class="col-md-1"><img class="img" id="ico-help" src="../imagens/ico-help.png"></div>
				<div class="col-md-4"><a id="a-link" data-toggle="modal" data-target="#myModal">Ayuda</a></div>
			  </div>
			  <div class="col-md-7" id="iconos">
				<div class="col-md-4">
				  <div class="col-md-1"><img class="img" id="ico-fb" src="../imagens/ico-fb.png"></div>
				  <div class="col-md-10"><a id="icon-link">ACOMER - Facebook</a></div>
				</div>
				<div class="col-md-4">
				  <div class="col-md-1"><img class="img" id="ico-tw" src="../imagens/ico-twitter.png"></div>
				  <div class="col-md-10"><a id="icon-link">ACOMER - Twitter</a></div>
				</div>
				<div class="col-md-4">
				  <div class="col-md-1"><img class="img" id="ico-phone" src="../imagens/phone2.png"></div>
				  <div class="col-md-10"><a id="icon-link">ACOMER - 987000000</a></div>
				</div>
			  </div>
			</div>
		  </div>
		</footer>
		<!--FIN FOOTER -->
	</body>
</html>