<?php
include "../Conexion.php";

$sql = "SELECT 
            id_usuario,
            nickname,
            firma,
            correo,
            ruta_imagen,
            fecha_registro
        FROM usuario";

$rs = $cn->prepare($sql);
$rs->execute();

$rows = $rs->fetchAll(PDO::FETCH_ASSOC);

// Devuelve el JSON sin escapar caracteres Unicode (como acentos o ñ)
echo json_encode($rows, JSON_UNESCAPED_UNICODE);
?>
