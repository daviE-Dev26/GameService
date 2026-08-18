<?php
include "../Conexion.php";

if (isset($_POST['id_usuario'], $_POST['id_usuario_amigo'])) {
    $id_usuario = $_POST['id_usuario'];
    $id_usuario_amigo = $_POST['id_usuario_amigo'];

    try {
        $sql = "DELETE FROM amigo 
                WHERE (id_usuario = ? AND id_usuario_amigo = ?)
                   OR (id_usuario = ? AND id_usuario_amigo = ?)";
        $stmt = $cn->prepare($sql);
        $stmt->execute([$id_usuario, $id_usuario_amigo, $id_usuario_amigo, $id_usuario]);

        echo json_encode(["mensaje" => "Amistad eliminada"]);
    } catch (PDOException $e) {
        echo json_encode(["error" => $e->getMessage()]);
    }
} else {
    echo json_encode(["error" => "Faltan parámetros"]);
}
?>
