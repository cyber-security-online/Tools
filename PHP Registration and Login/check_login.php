<?php
require_once ('configuration.php');
$db = mysqli_connect(
        MYSQL_HOST, 
        MYSQL_BENUTZER, 
        MYSQL_KENNWORT, 
        MYSQL_DATENBANK
                    );

// Read URL parameters.
$username = $_GET["username"];
$password = $_GET["password"];

$sql = "SELECT `password` FROM `registration` WHERE username='" . $username . "'";

if ($result=mysqli_query($db,$sql)){
	// Username not found.
	if($result->num_rows == 0){
		echo "err_usr";
	} /* Incorrect password. */ else if(mysqli_fetch_assoc($result)["password"] !== sha1($password)){
		echo "err_pwd";
	}
}

?>