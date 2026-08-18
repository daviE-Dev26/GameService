<?php
include "../Conexion.php";

if (isset($_POST['id_carrito'])) {
    $id_carrito = $_POST['id_carrito'];

    try {
        $sql = "DELETE FROM carrito WHERE id_carrito = ?";
        $stmt = $cn->prepare($sql);
        $stmt->execute([$id_carrito]);
        echo json_encode(["mensaje" => "Videojuego eliminado del carrito"]);
    } catch (PDOException $e) {
        echo json_encode(["error" => $e->getMessage()]);
    }
} else {
    echo json_encode(["error" => "Falta el parámetro id_carrito"]);
}
?>
