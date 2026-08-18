<?php
include "../Conexion.php";

header("Content-Type: application/json; charset=UTF-8");

if (isset($_POST['correo']) && isset($_POST['clave'])) {
    $correo = $_POST['correo'];
    $clave = $_POST['clave'];

    $sql = "SELECT id_usuario, nickname, correo 
            FROM usuario 
            WHERE correo = :correo AND clave = :clave";

    $rs = $cn->prepare($sql);
    $rs->bindParam(':correo', $correo);
    $rs->bindParam(':clave', $clave);
    $rs->execute();

    $row = $rs->fetch(PDO::FETCH_ASSOC);

    if ($row) {
        echo json_encode([
            "success" => true,
            "usuario" => $row
        ]);
    } else {
        echo json_encode([
            "success" => false,
            "mensaje" => "Correo o clave incorrectos"
        ]);
    }
} else {
    echo json_encode([
        "success" => false,
        "mensaje" => "Faltan parámetros: correo o clave"
    ]);
}
?>
