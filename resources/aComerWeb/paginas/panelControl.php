<?php 
	include("../../android_connect/db_connect.php");
	connect($con);
	mysqli_set_charset($con, "utf8");
	
	// check for required fields
	if (isset($_POST['email']) && isset($_POST['password']) && isset($_POST['tipo_usuario'])) {
		if (!empty($_POST['email']) && !empty($_POST['password']) && !empty($_POST['tipo_usuario'])) {
		 
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

	if($response['success'] == 1) {
		//ENTER THE RELEVANT INFO BELOW
		$mysqlUserName      = "acomer";
		$mysqlPassword      = "acomer";
		$mysqlHostName      = "localhost";
		$DbName             = "acomer";
		$backup_name        = "mybackup.sql";
		$tables             = "";
} else {
		echo '<p>ERROR. Usuario y/o contraseña incorrectos.</p>';
		echo '<a href="../paginas/admin.html">Volver a intentar</a>';
	}
	
?>

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
		<script src="../js/js.js"></script>
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
			<div class="container">
			   <div class="row">
			      <div class="col-md-6 col-md-offset-3">
			         <div class="panel panel-login">
			            <div class="panel-heading">
			               <div class="row">
			                  <div class="col-xs-6"><a class="active" id="addrestaurante-form-link" href="#">Comentarios sin Validar</a></div>
			                  <div class="col-xs-6"><a id="addmenu-form-link" href="#" class="">Crear copia de seguridad</a></div>
			               </div>
			               <hr>
			            </div>
			            <div class="panel-body">
			               <div class="row">
			                  <div class="col-lg-12">
			                     <form id="addrestaurante-form" action="" method="" role="form" style="display: block;">



			<table class="table table-bordered table-hover" id="tab_logic">
				<thead>
					<tr >
						<th class="text-center">
							#
						</th>
						<th class="text-center">
							usuario_email
						</th>
						<th class="text-center">
							restaurante_id
						</th>
						<th class="text-center">
							opinion
						</th>
						<th class="text-center">
							fecha_opinion
						</th>
					</tr>
				</thead>
				<tbody>
					
				</tbody>
			</table>




			                     </form>
			         
			                    <form id="addmenu-form" action="panelControl.php" method="post" role="form" style="display: none;">
			                      <div class="form-group">
			                        <input class="form-control" id="email" type="text" name="email" tabindex="1" placeholder="Email" required>
			                      </div>
			                      <div class="form-group">
			                        <input class="form-control" id="password" type="password" name="password" tabindex="2" placeholder="Password" required=>
			                      </div>
			                      <div class="form-group">
			                        <input class="form-control" id="tipo_usuario" type="hidden" name="tipo_usuario" tabindex="3" placeholder="" value="A">
			                      </div>
			                      <div class="form-group">
			                        <div class="row">
			                          <div class="col-sm-6 col-sm-offset-3">
			                            <input class="form-control btn btn-login" id="login-submit" type="submit" name="login-submit" tabindex="4" value="Acceder">
			                          </div>
			                        </div>
			                      </div>
			                    </form>
			                  </div>
			               </div>
			            </div>
			         </div>
			      </div>
			   </div>
			</div>

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