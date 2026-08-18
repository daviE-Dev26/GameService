<?php
include "../Conexion.php";

if (isset($_GET['id_usuario'])) {
    $id_usuario = $_GET['id_usuario'];

    $sql = "SELECT l.id_logro, l.nombre_logro AS logro, l.descripcion_logro, ul.fecha_obtenido
            FROM usuario_logro ul
            INNER JOIN logro l ON ul.id_logro = l.id_logro
            WHERE ul.id_usuario = :id_usuario";

    $rs = $cn->prepare($sql);
    $rs->bindParam(':id_usuario', $id_usuario);
    $rs->execute();

    $rows = $rs->fetchAll(PDO::FETCH_ASSOC);

    if ($rows) {
        echo json_encode($rows, JSON_UNESCAPED_UNICODE);
    } else {
        echo json_encode(["mensaje" => "El usuario no tiene logros aún"]);
    }
} else {
    echo json_encode(["error" => "Falta el parámetro id_usuario"]);
}
?>
