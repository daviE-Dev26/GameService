<?php
include "../Conexion.php";

// Recibir datos usando POST
$id = $_POST['id_videojuego'] ?? null;
$nombre = $_POST['nombre'] ?? null;
$descripcion = $_POST['descripcion'] ?? null;
$fecha_lanzamiento = $_POST['fecha_lanzamiento'] ?? null;
$desarrollador = $_POST['desarrollador'] ?? null;
$logros_totales = $_POST['logros_totales'] ?? 0;
$ruta_imagen = $_POST['ruta_imagen'] ?? "";
$ruta_imagen_grande = $_POST['ruta_imagen_grande'] ?? "";
$id_categoria = $_POST['id_categoria'] ?? 1;
$precio = $_POST['precio'] ?? "0";

if (!$id || !$nombre || !$descripcion || !$fecha_lanzamiento || !$desarrollador) {
    echo json_encode(["estado" => "error", "mensaje" => "Faltan datos obligatorios"]);
    exit;
}

// Escapar datos
$id = intval($id);
$nombre = mysqli_real_escape_string($conexion, $nombre);
$descripcion = mysqli_real_escape_string($conexion, $descripcion);
$fecha_lanzamiento = mysqli_real_escape_string($conexion, $fecha_lanzamiento);
$desarrollador = mysqli_real_escape_string($conexion, $desarrollador);
$ruta_imagen = mysqli_real_escape_string($conexion, $ruta_imagen);
$ruta_imagen_grande = mysqli_real_escape_string($conexion, $ruta_imagen_grande);
$precio = mysqli_real_escape_string($conexion, $precio);

$sql = "UPDATE videojuego SET 
nombre='$nombre', 
descripcion='$descripcion', 
fecha_lanzamiento='$fecha_lanzamiento', 
desarrollador='$desarrollador', 
logros_totales=$logros_totales, 
ruta_imagen='$ruta_imagen', 
ruta_imagen_grande='$ruta_imagen_grande', 
id_categoria=$id_categoria, 
precio='$precio'
WHERE id_videojuego=$id";

if (mysqli_query($conexion, $sql)) {
    echo json_encode(["estado" => "ok", "mensaje" => "Videojuego actualizado correctamente"]);
} else {
    echo json_encode(["estado" => "error", "mensaje" => mysqli_error($conexion)]);
}

mysqli_close($conexion);
?>
