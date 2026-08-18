<?php
include "../Conexion.php";

if (isset($_POST['id_usuario'], $_POST['id_usuario_amigo'])) {
    $id_usuario = $_POST['id_usuario'];
    $id_usuario_amigo = $_POST['id_usuario_amigo'];

    try {
        // Verificar si ya son amigos
        $sql = "SELECT * FROM amigo 
                WHERE (id_usuario = ? AND id_usuario_amigo = ?)
                   OR (id_usuario = ? AND id_usuario_amigo = ?)";
        $stmt = $cn->prepare($sql);
        $stmt->execute([$id_usuario, $id_usuario_amigo, $id_usuario_amigo, $id_usuario]);

        if ($stmt->rowCount() > 0) {
            echo json_encode(["mensaje" => "Ya son amigos"]);
        } else {
            // Insertar amistad
            $sql = "INSERT INTO amigo (id_usuario, id_usuario_amigo, fecha_amistad) VALUES (?, ?, NOW())";
            $stmt = $cn->prepare($sql);
            $stmt->execute([$id_usuario, $id_usuario_amigo]);
            echo json_encode(["mensaje" => "Amigo agregado correctamente"]);
        }
    } catch (PDOException $e) {
        echo json_encode(["error" => $e->getMessage()]);
    }
} else {
    echo json_encode(["error" => "Faltan parámetros"]);
}
?>
