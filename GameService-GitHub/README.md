# 🎮 GameService

Aplicación Android desarrollada en Kotlin para la consulta y gestión de videojuegos mediante una API web.

## 🛠️ Tecnologías

- Kotlin
- Android Studio
- Jetpack Compose
- Retrofit
- Gson
- PHP
- MariaDB / MySQL
- phpMyAdmin
- alwaysdata

## 📱 Funcionamiento

La aplicación Android consume una API desarrollada en PHP mediante Retrofit.

```text
Android (Kotlin)
       ↓
    Retrofit
       ↓
    API PHP
       ↓
    MariaDB

La API permite obtener información de videojuegos y usuarios almacenados en la base de datos.

🌐 Configuración de la API

La URL base se configura en Android mediante:

const val API_URL = "https://TU-DOMINIO/"

Por ejemplo, el endpoint para obtener videojuegos utiliza:

/videojuego/listar_videojuego.php

Por lo tanto, la aplicación realiza una petición a:

https://TU-DOMINIO/videojuego/listar_videojuego.php
🗄️ Base de datos

El proyecto incluye la estructura de la base de datos dentro de la carpeta:

Database/

Para utilizarla:

Crear una base de datos MariaDB/MySQL.
Importar el archivo .sql incluido en Database/.
Configurar las credenciales de conexión en el backend.
🔐 Configuración del backend

Las credenciales de la base de datos no se incluyen en este repositorio.

El archivo de conexión debe configurarse localmente con los datos correspondientes al servidor:

$cn = new PDO(
    "mysql:host=TU_HOST;dbname=TU_BASE_DE_DATOS;charset=utf8mb4",
    "TU_USUARIO",
    "TU_CONTRASEÑA"
);

Después de configurar el backend, los archivos PHP deben estar disponibles en el servidor web.

▶️ Ejecución
Clonar el repositorio.
Abrir el proyecto en Android Studio.
Configurar la base de datos.
Configurar la conexión PHP.
Configurar API_URL.
Ejecutar la aplicación en un dispositivo o emulador Android.

👨‍💻 Proyecto académico

GameService fue desarrollado como proyecto académico durante la formación en Desarrollo de Software.

El proyecto integra:

Desarrollo de aplicaciones Android
Consumo de APIs REST
Desarrollo backend con PHP
Gestión de bases de datos
Conexión entre aplicaciones móviles y servicios web
Persistencia de información mediante MariaDB/MySQL