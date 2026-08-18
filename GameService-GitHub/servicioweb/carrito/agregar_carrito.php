<?php
include "../Conexion.php";

if (isset($_POST['id_usuario'], $_POST['id_videojuego'], $_POST['cantidad'])) {
    $id_usuario = $_POST['id_usuario'];
    $id_videojuego = $_POST['id_videojuego'];
    $cantidad = $_POST['cantidad'];

    try {
        // Verificar si el videojuego ya está en el carrito del usuario
        $sqlCheck = "SELECT cantidad FROM carrito WHERE id_usuario = ? AND id_videojuego = ?";
        $stmtCheck = $cn->prepare($sqlCheck);
        $stmtCheck->execute([$id_usuario, $id_videojuego]);
        $existe = $stmtCheck->fetch(PDO::FETCH_ASSOC);

        if ($existe) {
            // Si ya está en el carrito, se actualiza la cantidad
            $nuevaCantidad = $existe['cantidad'] + $cantidad;
            $sqlUpdate = "UPDATE carrito SET cantidad = ? WHERE id_usuario = ? AND id_videojuego = ?";
            $stmtUpdate = $cn->prepare($sqlUpdate);
            $stmtUpdate->execute([$nuevaCantidad, $id_usuario, $id_videojuego]);

            echo json_encode(["mensaje" => "Cantidad actualizada en el carrito"]);
        } else {
            // Si no está en el carrito, se inserta un nuevo registro
            $sqlInsert = "INSERT INTO carrito (id_usuario, id_videojuego, cantidad, fecha_agregado) VALUES (?, ?, ?, NOW())";
            $stmtInsert = $cn->prepare($sqlInsert);
            $stmtInsert->execute([$id_usuario, $id_videojuego, $cantidad]);

            echo json_encode(["mensaje" => "Videojuego agregado al carrito"]);
        }
    } catch (PDOException $e) {
        echo json_encode(["error" => $e->getMessage()]);
    }
} else {
    echo json_encode(["error" => "Faltan parámetros"]);
}
?>
