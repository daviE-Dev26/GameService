<?php
include "../Conexion.php";

if (isset($_GET['id_videojuego'])) {
    $id_videojuego = $_GET['id_videojuego'];

    $sql = "SELECT ROUND(AVG(estrellas), 1) AS promedio 
            FROM resena 
            WHERE id_videojuego = :id_videojuego";

    $rs = $cn->prepare($sql);
    $rs->bindParam(':id_videojuego', $id_videojuego);
    $rs->execute();

    $row = $rs->fetch(PDO::FETCH_ASSOC);
    echo json_encode($row);
} else {
    echo json_encode(["error" => "Falta el parámetro id_videojuego"]);
}
?>
