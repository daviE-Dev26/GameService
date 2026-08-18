<?php
include "../Conexion.php";

try {
    $sql = "SELECT 
                c.id_carrito,
                c.id_usuario,
                v.id_videojuego,
                v.nombre AS nombre_videojuego,
                v.precio,
                c.cantidad,
                (v.precio * c.cantidad) AS subtotal,
                c.fecha_agregado,
                v.ruta_imagen
            FROM carrito c
            INNER JOIN videojuego v ON c.id_videojuego = v.id_videojuego";
    
    $stmt = $cn->prepare($sql);
    $stmt->execute();
    $carrito = $stmt->fetchAll(PDO::FETCH_ASSOC);
    
    echo json_encode($carrito);
} catch (PDOException $e) {
    echo json_encode(["error" => $e->getMessage()]);
}
?>
