<?php
include "../Conexion.php";

$id = $_POST['id_videojuego'] ?? null;

if (!$id) {
    echo json_encode(["estado" => "error", "mensaje" => "ID no proporcionado"]);
    exit;
}

$id = intval($id);

// Alternar activo: si está 1 pasa a 0, si está 0 pasa a 1
$sql = "UPDATE videojuego SET activo = 1 - activo WHERE id_videojuego=$id";

if (mysqli_query($conexion, $sql)) {
    echo json_encode(["estado" => "ok", "mensaje" => "Estado cambiado correctamente"]);
} else {
    echo json_encode(["estado" => "error", "mensaje" => mysqli_error($conexion)]);
}

mysqli_close($conexion);
?>
