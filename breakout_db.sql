-- =============================================
-- 1) Configuración inicial y creación de la base de datos
-- =============================================
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

-- Creamos la base de datos y la seleccionamos
CREATE DATABASE IF NOT EXISTS `breakout_db`
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;
USE `breakout_db`;

-- Elimina primero las tablas hijas para evitar errores de clave foránea
DROP TABLE IF EXISTS `listing_images`;
DROP TABLE IF EXISTS `listing_tags`;
DROP TABLE IF EXISTS `ratings`;
DROP TABLE IF EXISTS `comments`;
DROP TABLE IF EXISTS `listings`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `player`;

-- =============================================
-- 2) Creación de las tablas (estructura completa)
--    Se incluyen DROP TABLE IF EXISTS previo,
--    CREATE TABLE, índices, AUTO_INCREMENT y claves foráneas
-- =============================================

-- --------------------------------------------------------
-- Tabla `users`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Tabla `listings`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `listings`;
CREATE TABLE `listings` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `tags` varbinary(255) DEFAULT NULL,
  `video` varchar(255) DEFAULT NULL,
  `comments_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Tabla `listing_images`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `listing_images`;
CREATE TABLE `listing_images` (
  `listing_id` bigint(20) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  KEY `idx_listing_images_listing` (`listing_id`),
  CONSTRAINT `FK_listing_images_listing`
    FOREIGN KEY (`listing_id`) REFERENCES `listings` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Tabla `listing_tags`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `listing_tags`;
CREATE TABLE `listing_tags` (
  `listing_id` bigint(20) NOT NULL,
  `tags` varchar(255) DEFAULT NULL,
  KEY `idx_listing_tags_listing` (`listing_id`),
  CONSTRAINT `FK_listing_tags_listing`
    FOREIGN KEY (`listing_id`) REFERENCES `listings` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Tabla `ratings`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `ratings`;
CREATE TABLE `ratings` (
  `id` bigint(20) NOT NULL,
  `listing_id` bigint(20) DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Tabla `comments`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `listing_id` bigint(20) DEFAULT NULL,
  `text` text DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `title` varchar(250) DEFAULT NULL,
  `user_name` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------
-- Tabla `player`
-- --------------------------------------------------------
DROP TABLE IF EXISTS `player`;
CREATE TABLE `player` (
  `id` int(11) NOT NULL,
  `date_game` datetime(6) DEFAULT NULL,
  `destroyed_blocks` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- =============================================
-- 3) AUTO_INCREMENT para todas las tablas
-- =============================================
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

ALTER TABLE `listings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

-- No AUTO_INCREMENT aquí, ya que no tiene clave primaria autónoma
-- ALTER TABLE `listing_images`

-- No AUTO_INCREMENT aquí
-- ALTER TABLE `listing_tags`

ALTER TABLE `ratings`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

ALTER TABLE `comments`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

ALTER TABLE `player`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;


-- =============================================
-- 4) Inserción de datos (registros nuevos)
-- =============================================

-- --------------------------------------------------------
-- 4.1) Usuarios (`users`)
-- --------------------------------------------------------
INSERT INTO `users` (`id`, `created_at`, `email`, `last_name`, `name`, `password`, `role`) VALUES
  (1, '2025-06-04 10:00:00', 'juan@example.com',   'González',   'Juan',   'pass123', 'USER'),
  (2, '2025-06-04 10:05:00', 'ana@example.com',    'Pérez',      'Ana',    'pass123', 'USER'),
  (3, '2025-06-04 10:10:00', 'luis@example.com',   'Ramírez',    'Luis',   'pass123', 'USER'),
  (4, '2025-06-04 10:15:00', 'carmen@example.com', 'Martínez',   'Carmen', 'pass123', 'USER'),
  (5, '2025-06-04 10:20:00', 'pedro@example.com',  'López',      'Pedro',  'pass123', 'USER');

-- --------------------------------------------------------
-- 4.2) Listings (`listings`)
-- --------------------------------------------------------
INSERT INTO `listings` (`id`, `created_at`, `description`, `rating`, `title`, `updated_at`, `user_id`, `user_name`, `tags`, `video`) VALUES
  (1, '2025-06-04 11:00:00',
    'The Witcher 3: Wild Hunt es un RPG de acción de mundo abierto donde interpretas a Geralt de Rivia, un cazador de monstruos profesional. Explora vastos territorios, toma decisiones morales difíciles y vive una historia épica que ha marcado un antes y un después en los juegos de rol.',
    4.9,
    'The Witcher 3: Wild Hunt',
    '2025-06-04 11:00:00',
    1,
    'Juan',
    'RPG,Acción,Aventura',
    NULL),
  (2, '2025-06-04 11:10:00',
    'Elden Ring es un RPG de acción desarrollado por FromSoftware y producido por Hidetaka Miyazaki junto con George R. R. Martin. Sumérgete en las Tierras Intermedias, derrota jefes colosales y descubre secretos que cambiarán el destino de este mundo.',
    4.8,
    'Elden Ring',
    '2025-06-04 11:10:00',
    2,
    'Ana',
    'RPG,Acción,Mundo Abierto',
    NULL),
  (3, '2025-06-04 11:20:00',
    'Hades es un roguelike de acción en el que te adentrarás en el inframundo griego como Zagreus, hijo de Hades, intentando escapar de su destino. Cada intento es distinto, con una narrativa que se despliega pieza a pieza y combates vertiginosos contra dioses y criaturas mitológicas.',
    4.7,
    'Hades',
    '2025-06-04 11:20:00',
    3,
    'Luis',
    'Roguelike,Acción,Indie',
    NULL),
  (4, '2025-06-04 11:30:00',
    'Stardew Valley es un simulador de granja donde restauras la vieja granja de tu abuelo. Cultiva, cría animales, haz amistad con los habitantes del pueblo y explora minas llenas de misterios. Una experiencia relajante y adictiva que combina lo mejor de los simuladores de vida.',
    4.6,
    'Stardew Valley',
    '2025-06-04 11:30:00',
    4,
    'Carmen',
    'Simulación,Indie,Granja',
    NULL),
  (5, '2025-06-04 11:40:00',
    'Hollow Knight es un metroidvania en el que exploras Hallownest, un reino insecto que yace en ruinas. Con un estilo artístico dibujado a mano y una banda sonora envolvente, descubre secretos, derrota jefes desafiantes y revela la historia oculta de este mundo subterráneo.',
    4.8,
    'Hollow Knight',
    '2025-06-04 11:40:00',
    5,
    'Pedro',
    'Metroidvania,Indie,Exploración',
    NULL);

-- --------------------------------------------------------
-- 4.3) Imágenes de cada listing (`listing_images`)
--       (tres URLs válidas para cada juego)
-- --------------------------------------------------------
INSERT INTO `listing_images` (`listing_id`, `image`) VALUES
  -- The Witcher 3 (app id 292030)
  (1, 'https://cdn.akamai.steamstatic.com/steam/apps/292030/header.jpg'),
  (1, 'https://cdn.akamai.steamstatic.com/steam/apps/292030/capsule_231x87.jpg'),
  (1, 'https://cdn.akamai.steamstatic.com/steam/apps/292030/capsule_616x353.jpg'),
  -- Elden Ring (app id 1245620)
  (2, 'https://cdn.akamai.steamstatic.com/steam/apps/1245620/header.jpg'),
  (2, 'https://cdn.akamai.steamstatic.com/steam/apps/1245620/capsule_231x87.jpg'),
  (2, 'https://cdn.akamai.steamstatic.com/steam/apps/1245620/capsule_616x353.jpg'),
  -- Hades (app id 1145360)
  (3, 'https://cdn.akamai.steamstatic.com/steam/apps/1145360/header.jpg'),
  (3, 'https://cdn.akamai.steamstatic.com/steam/apps/1145360/capsule_231x87.jpg'),
  (3, 'https://cdn.akamai.steamstatic.com/steam/apps/1145360/capsule_616x353.jpg'),
  -- Stardew Valley (app id 413150)
  (4, 'https://cdn.akamai.steamstatic.com/steam/apps/413150/header.jpg'),
  (4, 'https://cdn.akamai.steamstatic.com/steam/apps/413150/capsule_231x87.jpg'),
  (4, 'https://cdn.akamai.steamstatic.com/steam/apps/413150/capsule_616x353.jpg'),
  -- Hollow Knight (app id 367520)
  (5, 'https://cdn.akamai.steamstatic.com/steam/apps/367520/header.jpg'),
  (5, 'https://cdn.akamai.steamstatic.com/steam/apps/367520/capsule_231x87.jpg'),
  (5, 'https://cdn.akamai.steamstatic.com/steam/apps/367520/capsule_616x353.jpg');

-- --------------------------------------------------------
-- 4.4) Etiquetas (`listing_tags`)
-- --------------------------------------------------------
INSERT INTO `listing_tags` (`listing_id`, `tags`) VALUES
  (1, 'RPG'),
  (1, 'Acción'),
  (2, 'Mundo Abierto'),
  (2, 'Desafío'),
  (3, 'Roguelike'),
  (3, 'Indie'),
  (4, 'Simulación'),
  (4, 'Indie'),
  (5, 'Metroidvania'),
  (5, 'Exploración');

-- --------------------------------------------------------
-- 4.5) Valoraciones (`ratings`)
-- --------------------------------------------------------
INSERT INTO `ratings` (`id`, `listing_id`, `rating`, `user_id`) VALUES
  (1, 1, 5.0,  2),
  (2, 1, 4.9,  3),
  (3, 2, 4.8,  1),
  (4, 2, 4.7,  5),
  (5, 3, 4.6,  4),
  (6, 3, 4.5,  2),
  (7, 4, 4.7,  3),
  (8, 4, 4.6,  5),
  (9, 5, 4.8,  1),
  (10,5, 4.9,  4);

-- --------------------------------------------------------
-- 4.6) Comentarios (`comments`)
-- --------------------------------------------------------
INSERT INTO `comments` (`id`, `created_at`, `listing_id`, `text`, `updated_at`, `user_id`, `title`, `user_name`) VALUES
  (1, '2025-06-04 12:00:00', 1,
       'La historia de Geralt es impresionante y las misiones secundarias tienen un guion excelente.',
       '2025-06-04 12:00:00', 2, NULL, 'Ana'),
  (2, '2025-06-04 12:10:00', 1,
       'El mundo abierto se siente vivo. Cada pueblo tiene su propia historia y tradiciones.',
       '2025-06-04 12:10:00', 3, NULL, 'Luis'),
  (3, '2025-06-04 12:20:00', 2,
       'Los jefes son desafiantes y la atmósfera de las Tierras Intermedias es única.',
       '2025-06-04 12:20:00', 1, NULL, 'Juan'),
  (4, '2025-06-04 12:30:00', 2,
       'Cada zona tiene un diseño espectacular. ¡Me encanta la libertad para explorar!',
       '2025-06-04 12:30:00', 5, NULL, 'Pedro'),
  (5, '2025-06-04 12:40:00', 3,
       'La narrativa dinámica en cada intento hace que quieras seguir probando una y otra vez.',
       '2025-06-04 12:40:00', 4, NULL, 'Carmen'),
  (6, '2025-06-04 12:50:00', 4,
       'Relajarme en la granja me ayuda a desconectar. ¡Muy adictivo!',
       '2025-06-04 12:50:00', 3, NULL, 'Luis'),
  (7, '2025-06-04 13:00:00', 5,
       'Los combates con jefes en Hollow Knight son de los mejores que he jugado en un metroidvania.',
       '2025-06-04 13:00:00', 1, NULL, 'Juan'),
  (8, '2025-06-04 13:10:00', 5,
       'El arte y la banda sonora crean una atmósfera tan envolvente que no quieres parar de jugar.',
       '2025-06-04 13:10:00', 2, NULL, 'Ana');

-- --------------------------------------------------------
-- 4.7) Jugadores (`player`)
-- --------------------------------------------------------
INSERT INTO `player` (`id`, `date_game`, `destroyed_blocks`, `score`, `user_id`, `user_name`) VALUES
  (1, '2025-06-04 14:00:00', 150, 12000, 1, 'Juan'),
  (2, '2025-06-04 14:10:00', 130, 11500, 2, 'Ana'),
  (3, '2025-06-04 14:20:00', 160, 12500, 3, 'Luis'),
  (4, '2025-06-04 14:30:00', 170, 13000, 4, 'Carmen'),
  (5, '2025-06-04 14:40:00', 140, 11800, 5, 'Pedro');

-- Actualiza el campo comments_count de cada listing según los comentarios existentes
UPDATE `listings` SET `comments_count` = 2 WHERE `id` = 1;
UPDATE `listings` SET `comments_count` = 2 WHERE `id` = 2;
UPDATE `listings` SET `comments_count` = 1 WHERE `id` = 3;
UPDATE `listings` SET `comments_count` = 1 WHERE `id` = 4;
UPDATE `listings` SET `comments_count` = 2 WHERE `id` = 5;

COMMIT;
