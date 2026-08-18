<?php
include "../Conexion.php";

$sql = "SELECT r.id_resena, r.id_videojuego, v.nombre AS videojuego, 
               r.id_usuario, u.nickname AS usuario, 
               r.comentario, r.estrellas, r.fecha_resena
        FROM resena r
        INNER JOIN videojuego v ON r.id_videojuego = v.id_videojuego
        INNER JOIN usuario u ON r.id_usuario = u.id_usuario";

$rs = $cn->prepare($sql);
$rs->execute();

$rows = $rs->fetchAll(PDO::FETCH_ASSOC);
echo json_encode($rows, JSON_UNESCAPED_UNICODE);
?>
