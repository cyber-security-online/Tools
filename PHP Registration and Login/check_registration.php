<?php
require_once ('configuration.php');
$db = mysqli_connect(
        MYSQL_HOST, 
        MYSQL_BENUTZER, 
        MYSQL_KENNWORT, 
        MYSQL_DATENBANK
                    );

// Read URL parameters.
$col = $_GET["col"];
$type = $_GET["type"];

$sql = "SELECT `" . $type . "` FROM `registration` WHERE " . $type . "='" . $col . "'";

if ($result=mysqli_query($db,$sql)){
	if($result->num_rows != 0){
		echo "err";
	}
}

?>