<?php
include "../Conexion.php";

$id_categoria = isset($_GET['id_categoria']) ? $_GET['id_categoria'] : '';
$nombre_categoria = isset($_GET['nombre_categoria']) ? $_GET['nombre_categoria'] : '';

if (!empty($id_categoria)) {
    $sql = "SELECT v.id_videojuego, v.nombre, v.descripcion, v.fecha_lanzamiento,
                   v.desarrollador, v.logros_totales, v.ruta_imagen,
                   c.nombre_categoria
            FROM videojuego v
            INNER JOIN categoria c ON v.id_categoria = c.id_categoria
            WHERE c.id_categoria = :id_categoria";
    $rs = $cn->prepare($sql);
    $rs->bindValue(':id_categoria', $id_categoria, PDO::PARAM_INT);

} elseif (!empty($nombre_categoria)) {
    $sql = "SELECT v.id_videojuego, v.nombre, v.descripcion, v.fecha_lanzamiento,
                   v.desarrollador, v.logros_totales, v.ruta_imagen,
                   c.nombre_categoria
            FROM videojuego v
            INNER JOIN categoria c ON v.id_categoria = c.id_categoria
            WHERE c.nombre_categoria LIKE :nombre_categoria";
    $rs = $cn->prepare($sql);
    $rs->bindValue(':nombre_categoria', '%' . $nombre_categoria . '%', PDO::PARAM_STR);

} else {
    echo json_encode(["error" => "Debe enviar un parámetro: id_categoria o nombre_categoria"]);
    exit;
}

$rs->execute();
$rows = $rs->fetchAll(PDO::FETCH_ASSOC);
echo json_encode($rows, JSON_UNESCAPED_UNICODE);
?>
