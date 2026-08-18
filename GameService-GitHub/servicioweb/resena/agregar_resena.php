<?php
include "../Conexion.php";

if (isset($_POST['id_videojuego']) && isset($_POST['id_usuario']) && isset($_POST['comentario']) && isset($_POST['estrellas'])) {
    $id_videojuego = $_POST['id_videojuego'];
    $id_usuario = $_POST['id_usuario'];
    $comentario = $_POST['comentario'];
    $estrellas = $_POST['estrellas'];

    $sql = "INSERT INTO resena (id_videojuego, id_usuario, comentario, estrellas, fecha)
            VALUES (:id_videojuego, :id_usuario, :comentario, :estrellas, NOW())";

    $rs = $cn->prepare($sql);
    $rs->bindParam(':id_videojuego', $id_videojuego);
    $rs->bindParam(':id_usuario', $id_usuario);
    $rs->bindParam(':comentario', $comentario);
    $rs->bindParam(':estrellas', $estrellas);

    if ($rs->execute()) {
        echo json_encode(["success" => "Reseña agregada correctamente"]);
    } else {
        echo json_encode(["error" => "Error al agregar reseña"]);
    }
} else {
    echo json_encode(["error" => "Faltan parámetros requeridos"]);
}
?>
