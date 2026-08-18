<?php
include("../Conexion.php");

if (isset($_GET['busqueda'])) {
    $busqueda = "%" . $_GET['busqueda'] . "%";

    try {
        $sql = "SELECT id_usuario, nickname, correo, ruta_imagen 
                FROM usuario 
                WHERE nickname LIKE ? OR correo LIKE ?";
        $stmt = $cn->prepare($sql);
        $stmt->execute([$busqueda, $busqueda]);
        $usuarios = $stmt->fetchAll(PDO::FETCH_ASSOC);

        echo json_encode($usuarios);
    } catch (PDOException $e) {
        echo json_encode(["error" => $e->getMessage()]);
    }
} else {
    echo json_encode(["error" => "Falta el parámetro de búsqueda"]);
}
?>
