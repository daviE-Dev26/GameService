<?php
include "../Conexion.php";

if (isset($_GET['id_usuario'])) {
    $id_usuario = $_GET['id_usuario'];

    $sql = "SELECT id_usuario, nickname, firma, correo, ruta_imagen, fecha_registro 
            FROM usuario 
            WHERE id_usuario = :id_usuario";

    $rs = $cn->prepare($sql);
    $rs->bindParam(':id_usuario', $id_usuario);
    $rs->execute();

    $row = $rs->fetch(PDO::FETCH_ASSOC);

    if ($row) {
        echo json_encode($row);
    } else {
        echo json_encode(["error" => "Usuario no encontrado"]);
    }
} else {
    echo json_encode(["error" => "Falta el parámetro id_usuario"]);
}
?>
