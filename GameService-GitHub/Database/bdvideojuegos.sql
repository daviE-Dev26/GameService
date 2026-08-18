-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3307
-- Tiempo de generación: 18-08-2026 a las 22:08:12
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdvideojuegos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `amigo`
--

CREATE TABLE `amigo` (
  `id_amigo` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_usuario_amigo` int(11) NOT NULL,
  `fecha_amistad` date DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `amigo`
--

INSERT INTO `amigo` (`id_amigo`, `id_usuario`, `id_usuario_amigo`, `fecha_amistad`) VALUES
(1, 1, 2, '2025-01-02'),
(2, 2, 1, '2025-01-02'),
(3, 1, 3, '2025-01-10'),
(4, 3, 1, '2025-01-10'),
(5, 4, 5, '2025-02-01'),
(6, 6, 7, '2025-02-08'),
(7, 7, 8, '2025-02-12'),
(8, 8, 9, '2025-02-20'),
(9, 9, 10, '2025-03-01'),
(10, 11, 12, '2025-03-05');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `carrito`
--

CREATE TABLE `carrito` (
  `id_carrito` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_videojuego` int(11) NOT NULL,
  `cantidad` int(11) DEFAULT 1,
  `fecha_agregado` date DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `carrito`
--

INSERT INTO `carrito` (`id_carrito`, `id_usuario`, `id_videojuego`, `cantidad`, `fecha_agregado`) VALUES
(1, 1, 17, 1, '2025-04-01'),
(2, 1, 3, 1, '2025-04-03'),
(3, 2, 2, 1, '2025-03-20'),
(4, 3, 14, 1, '2025-03-22'),
(5, 4, 7, 1, '2025-03-24'),
(6, 5, 1, 1, '2025-03-25'),
(7, 6, 11, 1, '2025-03-26'),
(8, 7, 8, 1, '2025-03-27'),
(9, 8, 9, 1, '2025-03-28'),
(10, 9, 10, 2, '2025-03-29');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id_categoria` int(11) NOT NULL,
  `nombre_categoria` varchar(100) NOT NULL,
  `descripcion_categoria` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id_categoria`, `nombre_categoria`, `descripcion_categoria`) VALUES
(1, 'Acción', 'Juegos centrados en la acción y combate en tiempo real'),
(2, 'Aventura', 'Exploración, narrativa y puzzles'),
(3, 'RPG', 'Juegos de rol, progresión y misiones'),
(4, 'Shooter', 'Disparos en primera o tercera persona'),
(5, 'Battle Royale', 'Multijugador masivo competitivo'),
(6, 'Simulación', 'Simulación de actividades o vida'),
(7, 'Carreras', 'Juegos de conducción y competición'),
(8, 'Plataforma', 'Juegos basados en saltos y plataformas'),
(9, 'Roguelike', 'Partidas con muerte permanente y rejugabilidad'),
(10, 'Party', 'Juegos sociales para grupos');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `logro`
--

CREATE TABLE `logro` (
  `id_logro` int(11) NOT NULL,
  `id_videojuego` int(11) DEFAULT NULL,
  `nombre_logro` varchar(150) NOT NULL,
  `descripcion_logro` text DEFAULT NULL,
  `puntos` int(11) DEFAULT 10
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `logro`
--

INSERT INTO `logro` (`id_logro`, `id_videojuego`, `nombre_logro`, `descripcion_logro`, `puntos`) VALUES
(1, 1, 'Explorador Celestial', 'Completa todas las áreas aéreas', 40),
(2, 1, 'Recolector Maestro', 'Recolecta todos los artefactos', 30),
(3, 2, 'Juez de los Nueve', 'Derrota al jefe final en dificultad alta', 60),
(4, 3, 'Constructor Legendario', 'Construye una estructura de 10000 bloques', 25),
(5, 4, 'Dominio de la Tumba', 'Derrota a 20 jefes opcionales', 50),
(6, 5, 'Rey del Battle', 'Gana 100 partidas', 20),
(7, 6, 'Líder del Escuadrón', 'Alcanza 200 victorias en dúo', 30),
(8, 7, 'Leyenda del Oeste', 'Completa la historia principal al 100%', 70),
(9, 8, 'Maestro del Inframundo', 'Completa la ruta definitiva', 50),
(10, 9, 'Hackerman', 'Alcanza nivel máximo en hacking', 45),
(11, 10, 'Granjero Supremo', 'Cosecha 10,000 productos', 25),
(12, 11, 'Spartan Legend', 'Completa la campaña en dificultad máxima', 40),
(13, 12, 'Detective Estelar', 'Encuentra 200 pistas', 20),
(14, 13, 'Cima de la Montaña', 'Completa todos los desafíos de escalada', 30),
(15, 14, 'Campeón de Carreras', 'Gana 50 eventos Horizon', 35),
(16, 15, 'Equipo perfecto', 'Juega con todos los héroes 10 partidas cada uno', 30),
(17, 16, 'Brujo Errante', 'Completa todas las misiones secundarias', 55),
(18, 17, 'Caos Total', 'Alcanza notoriedad máxima', 50),
(19, 18, 'Destrucción Suprema', 'Derrota 500 demonios', 40),
(20, 19, 'Estrella del Fútbol', 'Gana 100 partidos', 25),
(21, 20, 'Sombras Maestras', 'Vence todos los jefes en sigilo', 45);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `resena`
--

CREATE TABLE `resena` (
  `id_resena` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_videojuego` int(11) NOT NULL,
  `comentario` text DEFAULT NULL,
  `estrellas` tinyint(4) NOT NULL CHECK (`estrellas` between 1 and 5),
  `fecha_resena` date DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `resena`
--

INSERT INTO `resena` (`id_resena`, `id_usuario`, `id_videojuego`, `comentario`, `estrellas`, `fecha_resena`) VALUES
(1, 1, 1, 'Impresionante mundo y libertad total.', 5, '2025-01-20'),
(2, 2, 2, 'Historia brutal y emocionante.', 5, '2025-01-22'),
(3, 3, 3, 'Me encanta construir con amigos.', 5, '2025-01-25'),
(4, 4, 4, 'Difícil pero satisfactorio al vencer jefes.', 5, '2025-02-02'),
(5, 5, 5, 'Eventos en vivo muy entretenidos.', 4, '2025-02-10'),
(6, 6, 6, 'Buen equilibrio entre habilidades y armas.', 4, '2025-02-12'),
(7, 7, 7, 'Narrativa que te atrapa por horas.', 5, '2025-02-20'),
(8, 8, 8, 'Cada run se siente única.', 5, '2025-02-25'),
(9, 9, 9, 'Mejorado mucho desde el lanzamiento.', 3, '2025-03-01'),
(10, 10, 10, 'Relajante y con mucho contenido.', 5, '2025-03-05'),
(11, 11, 11, 'Campaña sólida y multijugador interesante.', 4, '2025-03-12');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL,
  `nickname` varchar(60) NOT NULL,
  `firma` varchar(150) DEFAULT NULL,
  `correo` varchar(150) NOT NULL,
  `clave` varchar(150) NOT NULL,
  `ruta_imagen` varchar(255) DEFAULT 'imagenes/perfil_default.jpg',
  `fecha_registro` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `nickname`, `firma`, `correo`, `clave`, `ruta_imagen`, `fecha_registro`) VALUES
(1, 'DaviGamer', 'El juego nunca termina', 'davi@example.com', 'davi123', 'imagenes/perfil001.jpg', '2025-10-13 10:32:49'),
(2, 'PlayerX', 'Game Over Never', 'playerx@example.com', 'playerx123', 'imagenes/perfil002.jpg', '2025-10-13 10:32:49'),
(3, 'LaraCraft', 'Aventura y exploración', 'lara@example.com', 'lara123', 'imagenes/perfil003.jpg', '2025-10-13 10:32:49'),
(4, 'MasterChief', 'Por la UNSC', 'chief@example.com', 'halo343', 'imagenes/perfil004.jpg', '2025-10-13 10:32:49'),
(5, 'SpeedRunner', 'Contra el reloj', 'speedy@example.com', 'runfast', 'imagenes/perfil005.jpg', '2025-10-13 10:32:49'),
(6, 'StealthNinja', 'Sigilo ante todo', 'ninja@example.com', 'shadow99', 'imagenes/perfil006.jpg', '2025-10-13 10:32:49'),
(7, 'RPGFan', 'Historias largas', 'rpgfan@example.com', 'rpglover', 'imagenes/perfil007.jpg', '2025-10-13 10:32:49'),
(8, 'ShooterKing', 'Precisión absoluta', 'shooter@example.com', 'aimtrue', 'imagenes/perfil008.jpg', '2025-10-13 10:32:49'),
(9, 'IndieLover', 'Pequeñas joyas', 'indie@example.com', 'indieonly', 'imagenes/perfil009.jpg', '2025-10-13 10:32:49'),
(10, 'StrategyPro', 'Plan y táctica', 'strat@example.com', 'thinkbig', 'imagenes/perfil010.jpg', '2025-10-13 10:32:49'),
(11, 'RetroGuy', 'Clásicos forever', 'retro@example.com', '8bitlover', 'imagenes/perfil011.jpg', '2025-10-13 10:32:49'),
(12, 'Explorer', 'Mundos infinitos', 'explore@example.com', 'wanderer', 'imagenes/perfil012.jpg', '2025-10-13 10:32:49'),
(13, 'Collector', 'Todo lo colecciono', 'collector@example.com', 'collectme', 'imagenes/perfil013.jpg', '2025-10-13 10:32:49'),
(14, 'StreamerX', 'Streaming 24/7', 'streamer@example.com', 'liveplay', 'imagenes/perfil014.jpg', '2025-10-13 10:32:49'),
(15, 'Modder', 'Mods y más mods', 'modder@example.com', 'modthis', 'imagenes/perfil015.jpg', '2025-10-13 10:32:49'),
(16, 'GloriousG', 'Gráfica al máximo', 'glorious@example.com', 'ultra123', 'imagenes/perfil016.jpg', '2025-10-13 10:32:49'),
(17, 'Crafty', 'Constructor experto', 'crafty@example.com', 'buildit', 'imagenes/perfil017.jpg', '2025-10-13 10:32:49'),
(18, 'NoobSlayer', 'Siempre listo', 'noob@example.com', 'noobslay', 'imagenes/perfil018.jpg', '2025-10-13 10:32:49'),
(19, 'PixelPro', 'Arte y pixels', 'pixelpro@example.com', 'pixel2025', 'imagenes/perfil019.jpg', '2025-10-13 10:32:49'),
(20, 'BattleAce', 'Siempre en la pelea', 'battle@example.com', 'ace123', 'imagenes/perfil020.jpg', '2025-10-13 10:32:49');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_logro`
--

CREATE TABLE `usuario_logro` (
  `id_usuario_logro` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_logro` int(11) NOT NULL,
  `fecha_obtenido` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario_logro`
--

INSERT INTO `usuario_logro` (`id_usuario_logro`, `id_usuario`, `id_logro`, `fecha_obtenido`) VALUES
(1, 1, 1, '2025-01-05'),
(2, 1, 2, '2025-01-10'),
(3, 2, 3, '2025-01-12'),
(4, 3, 4, '2025-02-02'),
(5, 4, 7, '2025-02-10'),
(6, 5, 5, '2025-02-15'),
(7, 6, 6, '2025-02-20'),
(8, 7, 8, '2025-03-01'),
(9, 8, 9, '2025-03-05'),
(10, 9, 10, '2025-03-10'),
(11, 10, 11, '2025-03-15'),
(12, 11, 12, '2025-03-20'),
(13, 12, 13, '2025-03-25'),
(14, 13, 14, '2025-03-30'),
(15, 14, 15, '2025-04-05'),
(16, 15, 16, '2025-04-10'),
(17, 16, 17, '2025-04-15'),
(18, 17, 18, '2025-04-20'),
(19, 18, 19, '2025-04-25'),
(20, 19, 20, '2025-04-30');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario_videojuego`
--

CREATE TABLE `usuario_videojuego` (
  `id_usuario_videojuego` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_videojuego` int(11) NOT NULL,
  `progreso` int(11) DEFAULT 0,
  `horas_jugadas` int(11) DEFAULT 0,
  `fecha_ultimo_juego` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuario_videojuego`
--

INSERT INTO `usuario_videojuego` (`id_usuario_videojuego`, `id_usuario`, `id_videojuego`, `progreso`, `horas_jugadas`, `fecha_ultimo_juego`) VALUES
(1, 1, 1, 60, 120, '2025-04-01'),
(2, 1, 3, 20, 40, '2025-03-15'),
(3, 2, 2, 80, 200, '2025-03-20'),
(4, 3, 4, 30, 60, '2025-04-10'),
(5, 4, 7, 100, 300, '2025-03-28'),
(6, 5, 5, 10, 15, '2025-02-22'),
(7, 6, 6, 55, 90, '2025-04-02'),
(8, 7, 8, 75, 140, '2025-04-12'),
(9, 8, 9, 40, 70, '2025-03-30'),
(10, 9, 10, 100, 220, '2025-04-05'),
(11, 10, 11, 25, 50, '2025-03-08');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `videojuego`
--

CREATE TABLE `videojuego` (
  `id_videojuego` int(11) NOT NULL,
  `nombre` varchar(150) NOT NULL,
  `descripcion` text DEFAULT NULL,
  `fecha_lanzamiento` date DEFAULT NULL,
  `desarrollador` varchar(150) DEFAULT NULL,
  `logros_totales` int(11) DEFAULT 0,
  `ruta_imagen` varchar(255) DEFAULT 'imagenes/default_juego.jpg',
  `ruta_imagen_grande` varchar(200) DEFAULT NULL,
  `id_categoria` int(11) DEFAULT NULL,
  `precio` decimal(10,2) NOT NULL DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `videojuego`
--

INSERT INTO `videojuego` (`id_videojuego`, `nombre`, `descripcion`, `fecha_lanzamiento`, `desarrollador`, `logros_totales`, `ruta_imagen`, `ruta_imagen_grande`, `id_categoria`, `precio`) VALUES
(1, 'The Legend of Zelda: Tears of the Kingdom', 'Secuela de Breath of the Wild con exploración aérea.', '2023-05-12', 'Nintendo', 80, 'imagenes/juego001.jpg', 'imagenes/juego001_grande.jpg', 2, 299.90),
(2, 'God of War Ragnarök', 'Kratos y Atreus enfrentan nuevas amenazas nórdicas.', '2022-11-09', 'Santa Monica Studio', 65, 'imagenes/juego002.jpg', 'imagenes/juego002_grande.jpg', 1, 289.90),
(3, 'Minecraft', 'Construye, explora y sobrevive en mundos generados.', '2011-11-18', 'Mojang', 50, 'imagenes/juego003.jpg', 'imagenes/juego003_grande.jpg', 6, 89.90),
(4, 'Elden Ring', 'Mundo abierto desafiante creado por FromSoftware.', '2022-02-25', 'FromSoftware', 70, 'imagenes/juego004.jpg', 'imagenes/juego004_grande.jpg', 3, 269.90),
(5, 'Fortnite', 'Battle Royale con construcción y eventos en vivo.', '2017-07-21', 'Epic Games', 40, 'imagenes/juego005.jpg', 'imagenes/juego005_grande.jpg', 5, 0.00),
(6, 'Apex Legends', 'Battle Royale por equipos con héroes con habilidades.', '2019-02-04', 'Respawn Entertainment', 35, 'imagenes/juego006.jpg', 'imagenes/juego006_grande.jpg', 5, 0.00),
(7, 'Red Dead Redemption 2', 'Épica narrativa en el viejo oeste.', '2018-10-26', 'Rockstar Games', 90, 'imagenes/juego007.jpg', 'imagenes/juego007_grande.jpg', 1, 229.90),
(8, 'Hades', 'Roguelike con fuerte narrativa y combate pulido.', '2020-09-17', 'Supergiant Games', 60, 'imagenes/juego008.jpg', 'imagenes/juego008_grande.jpg', 9, 89.90),
(9, 'Cyberpunk 2077', 'RPG futurista en Night City.', '2020-12-10', 'CD Projekt Red', 55, 'imagenes/juego009.jpg', 'imagenes/juego009_grande.jpg', 3, 199.90),
(10, 'Stardew Valley', 'Simulación de granja con elementos RPG.', '2016-02-26', 'ConcernedApe', 45, 'imagenes/juego010.jpg', 'imagenes/juego010_grande.jpg', 6, 49.90),
(11, 'Halo Infinite', 'FPS con campaña y multijugador moderno.', '2021-12-08', '343 Industries', 50, 'imagenes/juego011.jpg', 'imagenes/juego011_grande.jpg', 4, 249.90),
(12, 'Among Us', 'Juego social de deducción y roles.', '2018-06-15', 'InnerSloth', 25, 'imagenes/juego012.jpg', 'imagenes/juego012_grande.jpg', 10, 0.00),
(13, 'Celeste', 'Plataforma desafiante con historia emocional.', '2018-01-25', 'Matt Makes Games', 30, 'imagenes/juego013.jpg', 'imagenes/juego013_grande.jpg', 8, 59.90),
(14, 'Forza Horizon 5', 'Carreras en mundo abierto con clima dinámico.', '2021-11-09', 'Playground Games', 55, 'imagenes/juego014.jpg', 'imagenes/juego014_grande.jpg', 7, 259.90),
(15, 'Overwatch 2', 'Hero shooter por equipos con personajes únicos.', '2022-10-04', 'Blizzard Entertainment', 40, 'imagenes/juego015.jpg', 'imagenes/juego015_grande.jpg', 4, 0.00),
(16, 'The Witcher 3: Wild Hunt', 'RPG de mundo abierto basado en las novelas.', '2015-05-19', 'CD Projekt Red', 120, 'imagenes/juego016.jpg', 'imagenes/juego016_grande.jpg', 3, 189.90),
(17, 'Grand Theft Auto V', 'Mundo abierto con tres protagonistas y modo online.', '2013-09-17', 'Rockstar Games', 100, 'imagenes/juego017.jpg', 'imagenes/juego017_grande.jpg', 1, 139.90),
(18, 'DOOM Eternal', 'Shooter frenético con acción intensa.', '2020-03-20', 'id Software', 40, 'imagenes/juego018.jpg', 'imagenes/juego018_grande.jpg', 4, 179.90),
(19, 'FIFA 23', 'Simulador de fútbol anual con equipos y modos online.', '2022-09-30', 'EA Sports', 30, 'imagenes/juego019.jpg', 'imagenes/juego019_grande.jpg', 6, 259.90),
(20, 'Sekiro: Shadows Die Twice', 'Acción y combate desafiante por FromSoftware.', '2019-03-22', 'FromSoftware', 45, 'imagenes/juego020.jpg', 'imagenes/juego020_grande.jpg', 1, 219.90);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `amigo`
--
ALTER TABLE `amigo`
  ADD PRIMARY KEY (`id_amigo`),
  ADD KEY `fk_amigo_usuario` (`id_usuario`),
  ADD KEY `fk_amigo_usuario_amigo` (`id_usuario_amigo`);

--
-- Indices de la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD PRIMARY KEY (`id_carrito`),
  ADD KEY `fk_carrito_usuario` (`id_usuario`),
  ADD KEY `fk_carrito_videojuego` (`id_videojuego`);

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id_categoria`);

--
-- Indices de la tabla `logro`
--
ALTER TABLE `logro`
  ADD PRIMARY KEY (`id_logro`),
  ADD KEY `fk_logro_videojuego` (`id_videojuego`);

--
-- Indices de la tabla `resena`
--
ALTER TABLE `resena`
  ADD PRIMARY KEY (`id_resena`),
  ADD KEY `fk_resena_usuario` (`id_usuario`),
  ADD KEY `fk_resena_videojuego` (`id_videojuego`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `correo` (`correo`);

--
-- Indices de la tabla `usuario_logro`
--
ALTER TABLE `usuario_logro`
  ADD PRIMARY KEY (`id_usuario_logro`),
  ADD KEY `fk_usuario_logro_usuario` (`id_usuario`),
  ADD KEY `fk_usuario_logro_logro` (`id_logro`);

--
-- Indices de la tabla `usuario_videojuego`
--
ALTER TABLE `usuario_videojuego`
  ADD PRIMARY KEY (`id_usuario_videojuego`),
  ADD KEY `fk_uv_usuario` (`id_usuario`),
  ADD KEY `fk_uv_videojuego` (`id_videojuego`);

--
-- Indices de la tabla `videojuego`
--
ALTER TABLE `videojuego`
  ADD PRIMARY KEY (`id_videojuego`),
  ADD KEY `fk_videojuego_categoria` (`id_categoria`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `amigo`
--
ALTER TABLE `amigo`
  MODIFY `id_amigo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `carrito`
--
ALTER TABLE `carrito`
  MODIFY `id_carrito` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id_categoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `logro`
--
ALTER TABLE `logro`
  MODIFY `id_logro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `resena`
--
ALTER TABLE `resena`
  MODIFY `id_resena` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `usuario_logro`
--
ALTER TABLE `usuario_logro`
  MODIFY `id_usuario_logro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `usuario_videojuego`
--
ALTER TABLE `usuario_videojuego`
  MODIFY `id_usuario_videojuego` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `videojuego`
--
ALTER TABLE `videojuego`
  MODIFY `id_videojuego` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `amigo`
--
ALTER TABLE `amigo`
  ADD CONSTRAINT `fk_amigo_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_amigo_usuario_amigo` FOREIGN KEY (`id_usuario_amigo`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `carrito`
--
ALTER TABLE `carrito`
  ADD CONSTRAINT `fk_carrito_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_carrito_videojuego` FOREIGN KEY (`id_videojuego`) REFERENCES `videojuego` (`id_videojuego`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `logro`
--
ALTER TABLE `logro`
  ADD CONSTRAINT `fk_logro_videojuego` FOREIGN KEY (`id_videojuego`) REFERENCES `videojuego` (`id_videojuego`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `resena`
--
ALTER TABLE `resena`
  ADD CONSTRAINT `fk_resena_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_resena_videojuego` FOREIGN KEY (`id_videojuego`) REFERENCES `videojuego` (`id_videojuego`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario_logro`
--
ALTER TABLE `usuario_logro`
  ADD CONSTRAINT `fk_usuario_logro_logro` FOREIGN KEY (`id_logro`) REFERENCES `logro` (`id_logro`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_usuario_logro_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `usuario_videojuego`
--
ALTER TABLE `usuario_videojuego`
  ADD CONSTRAINT `fk_uv_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_uv_videojuego` FOREIGN KEY (`id_videojuego`) REFERENCES `videojuego` (`id_videojuego`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `videojuego`
--
ALTER TABLE `videojuego`
  ADD CONSTRAINT `fk_videojuego_categoria` FOREIGN KEY (`id_categoria`) REFERENCES `categoria` (`id_categoria`) ON DELETE SET NULL ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
ALTER TABLE videojuego
ADD COLUMN activo TINYINT(1) NOT NULL DEFAULT 1;