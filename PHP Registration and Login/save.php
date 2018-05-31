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
$email = $_GET["email"];
$password = $_GET["password"];

$sql = "INSERT INTO `registration` (username, email, password) VALUES ('" . $username . "', '" . $email . "', '" . sha1($password) ."')";
mysqli_query($db,$sql);
?>