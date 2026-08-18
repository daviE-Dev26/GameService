<?php
include "../Conexion.php";

$sql = "SELECT v.id_videojuego, 
               v.nombre, 
               v.descripcion, 
               v.fecha_lanzamiento,
               v.desarrollador, 
               v.logros_totales, 
               v.precio, 
               v.ruta_imagen, 
               v.ruta_imagen_grande, 
               c.nombre_categoria,
               v.activo
        FROM videojuego v
        INNER JOIN categoria c ON v.id_categoria = c.id_categoria
        WHERE v.activo = 1";

$rs = $cn->prepare($sql);
$rs->execute();

$rows = $rs->fetchAll(PDO::FETCH_ASSOC);
echo json_encode($rows, JSON_UNESCAPED_UNICODE);
?>
