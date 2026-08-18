<?php
include "../Conexion.php";

if (isset($_POST['id_usuario']) && isset($_POST['id_logro'])) {
    $id_usuario = $_POST['id_usuario'];
    $id_logro = $_POST['id_logro'];

    $sql = "INSERT INTO usuario_logro (id_usuario, id_logro, fecha_obtenido)
            VALUES (:id_usuario, :id_logro, NOW())";

    $rs = $cn->prepare($sql);
    $rs->bindParam(':id_usuario', $id_usuario);
    $rs->bindParam(':id_logro', $id_logro);

    if ($rs->execute()) {
        echo json_encode(["success" => "Logro asignado correctamente"]);
    } else {
        echo json_encode(["error" => "Error al asignar logro"]);
    }
} else {
    echo json_encode(["error" => "Faltan parámetros requeridos"]);
}
?>
