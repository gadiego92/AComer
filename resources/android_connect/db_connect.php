<?php
	define('DB_SERVER', "localhost");		// DB_SERVER
	define('DB_USER', "android");			// DB_USER
	define('DB_PASSWORD', "1234");			// DB_PASSWORD
	define('DB_DATABASE', "a_comer_db");	// DB_DATABASE
		
	$con;

	function connect(&$con) {
		$con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysql_error("could not connect to mysql"));

		/* verificar conexión */
		if (mysqli_connect_errno()) {
			printf("Connect failed: %s\n", mysqli_connect_error());
			exit();
		}
	}
?> 