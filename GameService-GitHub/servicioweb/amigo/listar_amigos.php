<?php
include "../Conexion.php";

if (isset($_GET['id_usuario'])) {
    $id_usuario = $_GET['id_usuario'];

    try {
        $sql = "SELECT u.id_usuario, u.nickname, u.correo, u.ruta_imagen, a.fecha_amistad
                FROM amigo a
                INNER JOIN usuario u ON a.id_usuario_amigo = u.id_usuario
                WHERE a.id_usuario = ?";
        $stmt = $cn->prepare($sql);
        $stmt->execute([$id_usuario]);
        $amigos = $stmt->fetchAll(PDO::FETCH_ASSOC);

        echo json_encode($amigos);
    } catch (PDOException $e) {
        echo json_encode(["error" => $e->getMessage()]);
    }
} else {
    echo json_encode(["error" => "Falta el parámetro id_usuario"]);
}
?>
