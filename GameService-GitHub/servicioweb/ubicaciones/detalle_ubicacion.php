<?php
include "../Conexion.php";

$id = $_GET["id"];


$sql = "SELECT * FROM game_locations WHERE id = :id LIMIT 1";
$rs = $cn->prepare($sql);
$rs->bindParam(":id", $id, PDO::PARAM_INT);
$rs->execute();

$row = $rs->fetch(PDO::FETCH_ASSOC);

if ($row) {
    echo json_encode($row, JSON_UNESCAPED_UNICODE);
} else {
    echo json_encode(["status" => "error", "message" => "No encontrado"]);
}
?>
