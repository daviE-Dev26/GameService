<?php
include "../Conexion.php";

$nombre = isset($_GET['nombre']) ? $_GET['nombre'] : '';

$sql = "SELECT v.id_videojuego, v.nombre, v.descripcion, v.fecha_lanzamiento,
               v.desarrollador, v.logros_totales, v.ruta_imagen,
               c.nombre_categoria
        FROM videojuego v
        INNER JOIN categoria c ON v.id_categoria = c.id_categoria
        WHERE v.nombre LIKE :nombre";

$rs = $cn->prepare($sql);
$rs->bindValue(':nombre', '%' . $nombre . '%', PDO::PARAM_STR);
$rs->execute();

$rows = $rs->fetchAll(PDO::FETCH_ASSOC);
echo json_encode($rows, JSON_UNESCAPED_UNICODE);
?>
