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
		$mysqlUserName      = "android";
		$mysqlPassword      = "1234";
		$mysqlHostName      = "localhost";
		$DbName             = "a_comer_db";
		$backup_name        = "mybackup.sql";
		$tables             = "";

		// or add 5th parameter(array) of specific tables:   
		// array("mytable1","mytable2","mytable3") for multiple tables

		function Export_Database($host,$user,$pass,$name,  $tables=false, $backup_name=false) {
			$mysqli = new mysqli($host,$user,$pass,$name); 
			$mysqli->select_db($name); 
			$mysqli->query("SET NAMES 'utf8'");

			$queryTables = $mysqli->query('SHOW TABLES'); 
			
			while($row = $queryTables->fetch_row()) { 
				$target_tables[] = $row[0]; 
			}   
			
			if($tables !== false) { 
				$target_tables = array_intersect( $target_tables, $tables); 
			}
			
			foreach($target_tables as $table) {
				$result         =   $mysqli->query('SELECT * FROM '.$table);  
				$fields_amount  =   $result->field_count;  
				$rows_num=$mysqli->affected_rows;     
				$res            =   $mysqli->query('SHOW CREATE TABLE '.$table); 
				$TableMLine     =   $res->fetch_row();
				$content        = (!isset($content) ?  '' : $content) . "\n\n".$TableMLine[1].";\n\n";

				for ($i = 0, $st_counter = 0; $i < $fields_amount;   $i++, $st_counter=0) {
					while($row = $result->fetch_row()) {
						//when started (and every after 100 command cycle):
						if ($st_counter%100 == 0 || $st_counter == 0 ) {
								$content .= "\nINSERT INTO ".$table." VALUES";
						}
						$content .= "\n(";
						for($j=0; $j<$fields_amount; $j++) { 
							$row[$j] = str_replace("\n","\\n", addslashes($row[$j]) ); 
							if (isset($row[$j])) {
								$content .= '"'.$row[$j].'"' ; 
							} else {   
								$content .= '""';
							}     
							
							if ($j<($fields_amount-1)) {
									$content.= ',';
							}      
						}
						$content .=")";
						//every after 100 command cycle [or at last line] ....p.s. but should be inserted 1 cycle eariler
						if ( (($st_counter+1)%100==0 && $st_counter!=0) || $st_counter+1==$rows_num) {   
							$content .= ";";
						}  else  {
							$content .= ",";
						} 
						$st_counter=$st_counter+1;
					}
				} $content .="\n\n\n";
			}
			//$backup_name = $backup_name ? $backup_name : $name."___(".date('H-i-s')."_".date('d-m-Y').")__rand".rand(1,11111111).".sql";
			$backup_name = $backup_name ? $backup_name : $name.".sql";
			header('Content-Type: application/octet-stream');   
			header("Content-Transfer-Encoding: Binary"); 
			header("Content-disposition: attachment; filename=\"".$backup_name."\"");  
			echo $content;
			exit;
		}
	
		Export_Database($mysqlHostName,$mysqlUserName,$mysqlPassword,$DbName,$tables=false,$backup_name=false);
	
		echo '<p>Autenticación con exito!</p>';
		echo '<p>A continuación, podrá guardar una copia de segridad de la base de datos.</p>';
		echo '<a href="../paginas/admin.html">Volver</a>';
	} else {
		echo '<p>ERROR. Usuario y/o contraseña incorrectos.</p>';
		echo '<a href="../paginas/admin.html">Volver a intentar</a>';
	}
	
?>