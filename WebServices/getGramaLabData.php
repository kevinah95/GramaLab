<?php
include("DataBaseClass.php");
$dataBase = new DataBase("mysql.hostinger.es", "u742800395_root", "qwerty123", "u742800395_grama");

$Table = $_POST["Table"];

echo $dataBase->doQuery("Select * from $Table;");
$dataBase->close();
?>