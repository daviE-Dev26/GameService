<?php
include "../Conexion.php";

if (isset($_POST['nickname']) && isset($_POST['correo']) && isset($_POST['clave'])) {
    $nickname = $_POST['nickname'];
    $correo = $_POST['correo'];
    $clave = $_POST['clave'];

    $sql = "INSERT INTO usuario (nickname, firma, correo, clave, ruta_imagen, fecha_registro)
            VALUES (:nickname, '', :correo, :clave, 'imagenes/perfil_default.jpg', NOW())";

    $rs = $cn->prepare($sql);
    $rs->bindParam(':nickname', $nickname);
    $rs->bindParam(':correo', $correo);
    $rs->bindParam(':clave', $clave);

    if ($rs->execute()) {
        echo json_encode(["success" => "Usuario registrado correctamente"]);
    } else {
        echo json_encode(["error" => "Error al registrar usuario"]);
    }
} else {
    echo json_encode(["error" => "Faltan parámetros requeridos"]);
}
?>
