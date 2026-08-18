<?php
include "../Conexion.php";

$sql = "SELECT id, nombre, latitud, longitud, imagen, descripcion FROM game_locations";

$rs = $cn->prepare($sql);
$rs->execute();

$locations = $rs->fetchAll(PDO::FETCH_ASSOC);

echo json_encode($locations, JSON_UNESCAPED_UNICODE);
?>
