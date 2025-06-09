-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 08-06-2025 a las 10:42:24
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `breakout_db`
--
CREATE DATABASE IF NOT EXISTS `breakout_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `breakout_db`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comments`
--

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `listing_id` bigint(20) DEFAULT NULL,
  `text` text DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `title` varchar(250) DEFAULT NULL,
  `user_name` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `comments`
--

INSERT INTO `comments` (`id`, `created_at`, `listing_id`, `text`, `user_id`, `title`, `user_name`) VALUES
(1, '2025-06-08 10:00:00.000000', 1, 'This game is absolutely fantastic. Highly recommended!', 1, 'Epic Adventure', 'Juan'),
(2, '2025-06-08 10:05:00.000000', 2, 'I enjoyed the story but the gameplay could be better.', 2, 'Not Bad', 'Ana'),
(3, '2025-06-08 10:10:00.000000', 3, 'The graphics are stunning and the world feels alive.', 3, 'Great Graphics', 'Luis'),
(4, '2025-06-08 10:15:00.000000', 4, 'Boss fights are tough but rewarding.', 4, 'Challenging', 'Carmen'),
(5, '2025-06-08 10:20:00.000000', 5, 'Perfect game to relax after work.', 5, 'Relaxing', 'Pedro'),
(6, '2025-06-08 10:25:00.000000', 6, 'Could not stop playing for hours!', 6, 'Addictive', 'Alejandro'),
(7, '2025-06-08 10:30:00.000000', 7, 'As an admin, I approve this game.', 7, 'Admin Review', 'Super'),
(8, '2025-06-08 10:35:00.000000', 8, 'Best played with friends for maximum fun.', 8, 'Fun with Friends', 'Eve'),
(9, '2025-06-08 10:40:00.000000', 9, 'Reminds me of classic games from my childhood.', 9, 'Classic', 'Bob'),
(10, '2025-06-08 10:45:00.000000', 10, 'Great game but too short for the price.', 10, 'Too Short', 'Alice'),
(11, '2025-06-08 10:50:00.000000', 11, 'Encountered several bugs during my playthrough.', 11, 'Buggy', 'Charlie'),
(12, '2025-06-08 10:55:00.000000', 12, 'One of the best games I have ever played.', 12, 'Masterpiece', 'Dave'),
(13, '2025-06-08 11:00:00.000000', 1, 'The world-building is top notch.', 1, 'Immersive', 'Juan'),
(14, '2025-06-08 11:05:00.000000', 2, 'The music is beautiful and fits the mood.', 2, 'Soundtrack', 'Ana'),
(15, '2025-06-08 11:10:00.000000', 3, 'Lots of secrets to discover on a second playthrough.', 3, 'Replay Value', 'Luis'),
(16, '2025-06-08 11:15:00.000000', 4, 'Controls are smooth and responsive.', 4, 'Controls', 'Carmen'),
(17, '2025-06-08 11:20:00.000000', 5, 'The story kept me hooked until the end.', 5, 'Story', 'Pedro'),
(18, '2025-06-08 11:25:00.000000', 6, 'Visually stunning, especially on high settings.', 6, 'Graphics', 'Alejandro'),
(19, '2025-06-08 11:30:00.000000', 7, 'Multiplayer mode is chaotic fun.', 7, 'Multiplayer', 'Super'),
(20, '2025-06-08 11:35:00.000000', 8, 'Love the character customization options.', 8, 'Customization', 'Eve'),
(21, '2025-06-08 11:40:00.000000', 9, 'Some puzzles are really clever.', 9, 'Puzzles', 'Bob'),
(22, '2025-06-08 11:45:00.000000', 10, 'The atmosphere is dark and mysterious.', 10, 'Atmosphere', 'Alice'),
(23, '2025-06-08 11:50:00.000000', 11, 'Boss battles are epic and memorable.', 11, 'Bosses', 'Charlie'),
(24, '2025-06-08 11:55:00.000000', 12, 'Side quests are as good as the main story.', 12, 'Side Quests', 'Dave'),
(25, '2025-06-08 12:00:00.000000', 3, 'Runs smoothly even on older hardware.', 1, 'Performance', 'Juan'),
(26, '2025-06-08 12:05:00.000000', 4, 'A few bugs but nothing game-breaking.', 2, 'Bugs', 'Ana'),
(27, '2025-06-08 12:10:00.000000', 5, 'Looking forward to the upcoming DLC.', 3, 'DLC', 'Luis'),
(28, '2025-06-08 12:15:00.000000', 6, 'Enemy AI is challenging and unpredictable.', 4, 'AI', 'Carmen'),
(29, '2025-06-08 12:20:00.000000', 7, 'Took me over 50 hours to finish everything.', 5, 'Length', 'Pedro'),
(30, '2025-06-08 12:25:00.000000', 8, 'Voice acting is professional and emotional.', 6, 'Voice Acting', 'Alejandro');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `user_name` varchar(50) NOT NULL UNIQUE,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL,
  `profile_pic` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `created_at`, `email`, `user_name`, `password`, `role`, `profile_pic`) VALUES
(1, '2025-06-04 10:00:00.000000', 'juan@example.com', 'Juan', 'pass123', 'USER', 'default.png'),
(2, '2025-06-04 10:05:00.000000', 'ana@example.com', 'Ana', 'pass123', 'USER', 'default.png'),
(3, '2025-06-04 10:10:00.000000', 'luis@example.com', 'Luis', 'pass123', 'USER', 'default.png'),
(4, '2025-06-04 10:15:00.000000', 'carmen@example.com', 'Carmen', 'pass123', 'USER', 'default.png'),
(5, '2025-06-04 10:20:00.000000', 'pedro@example.com', 'Pedro', 'pass123', 'USER', 'default.png'),
(6, '2025-06-04 11:54:54.000000', 'aarcos664@gmail.com', 'Alejandro', '$2a$10$Ird/WoPCRBGJYg4FJRn5lunBzyZr4QYJz0CAGqAKJwmaSeJ5oRUcu', 'USER', 'default.png');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `listings`
--

CREATE TABLE `listings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `official_url` varchar(255) NOT NULL,
  `rating` double DEFAULT NULL,
  `price` decimal(10,2) NOT NULL,
  `location` varchar(255) NOT NULL,
  `status` varchar(50) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `user_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `video` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Volcado de datos para la tabla `listings`
--

INSERT INTO `listings` (`id`, `title`, `description`, `official_url`, `rating`, `price`, `location`, `status`, `created_at`, `updated_at`, `user_id`, `user_name`, `video`) VALUES
(1, 'Epic Adventure', 'A thrilling adventure game with stunning graphics and a captivating story.', 'https://example.com/epic-adventure', 4.8, '49.99', 'New York', 'Available', '2025-06-08 10:00:00', '2025-06-08 10:00:00', 1, 'Juan', NULL),
(2, 'Not Bad', 'A fun and engaging open-world game with a beautiful soundtrack.', 'https://example.com/not-bad', 4.2, '39.99', 'Los Angeles', 'Available', '2025-06-08 10:05:00', '2025-06-08 10:05:00', 2, 'Ana', NULL),
(3, 'Great Graphics', 'A visually stunning game with a rich and immersive world.', 'https://example.com/great-graphics', 4.9, '59.99', 'Tokyo', 'Available', '2025-06-08 10:10:00', '2025-06-08 10:10:00', 3, 'Luis', NULL),
(4, 'Challenging', 'A challenging game with tough boss fights and rewarding gameplay.', 'https://example.com/challenging', 4.5, '44.99', 'Paris', 'Available', '2025-06-08 10:15:00', '2025-06-08 10:15:00', 4, 'Carmen', NULL),
(5, 'Relaxing', 'A perfect game to relax after work.', 'https://example.com/relaxing', 4.7, '29.99', 'London', 'Available', '2025-06-08 10:20:00', '2025-06-08 10:20:00', 5, 'Pedro', NULL),
(6, 'Addictive', 'A game that is incredibly addictive and hard to put down.', 'https://example.com/addictive', 4.6, '34.99', 'Berlin', 'Available', '2025-06-08 10:25:00', '2025-06-08 10:25:00', 1, 'Juan', NULL),
(7, 'Admin Review', 'As an admin, I approve this game.', 'https://example.com/admin-review', 5.0, '0.00', 'New York', 'Available', '2025-06-08 10:30:00', '2025-06-08 10:30:00', 2, 'Ana', NULL),
(8, 'Fun with Friends', 'Best played with friends for maximum fun.', 'https://example.com/fun-with-friends', 4.3, '0.00', 'Los Angeles', 'Available', '2025-06-08 10:35:00', '2025-06-08 10:35:00', 3, 'Luis', NULL),
(9, 'Classic', 'Reminds me of classic games from my childhood.', 'https://example.com/classic', 4.4, '29.99', 'Tokyo', 'Available', '2025-06-08 10:40:00', '2025-06-08 10:40:00', 4, 'Carmen', NULL),
(10, 'Too Short', 'Great game but too short for the price.', 'https://example.com/too-short', 3.8, '19.99', 'Paris', 'Available', '2025-06-08 10:45:00', '2025-06-08 10:45:00', 5, 'Pedro', NULL),
(11, 'Buggy', 'Encountered several bugs during my playthrough.', 'https://example.com/buggy', 3.5, '39.99', 'London', 'Available', '2025-06-08 10:50:00', '2025-06-08 10:50:00', 1, 'Juan', NULL),
(12, 'Masterpiece', 'One of the best games I have ever played.', 'https://example.com/masterpiece', 5.0, '59.99', 'Berlin', 'Available', '2025-06-08 10:55:00', '2025-06-08 10:55:00', 2, 'Ana', NULL),
(13, 'Immersive', 'The world-building is top notch.', 'https://example.com/immersive', 4.9, '49.99', 'New York', 'Available', '2025-06-08 11:00:00', '2025-06-08 11:00:00', 3, 'Luis', NULL),
(14, 'Soundtrack', 'The music is beautiful and fits the mood.', 'https://example.com/soundtrack', 4.8, '39.99', 'Los Angeles', 'Available', '2025-06-08 11:05:00', '2025-06-08 11:05:00', 4, 'Carmen', NULL),
(15, 'Replay Value', 'Lots of secrets to discover on a second playthrough.', 'https://example.com/replay-value', 4.7, '44.99', 'Tokyo', 'Available', '2025-06-08 11:10:00', '2025-06-08 11:10:00', 5, 'Pedro', NULL),
(16, 'Controls', 'Controls are smooth and responsive.', 'https://example.com/controls', 4.6, '39.99', 'Paris', 'Available', '2025-06-08 11:15:00', '2025-06-08 11:15:00', 1, 'Juan', NULL),
(17, 'Story', 'The story kept me hooked until the end.', 'https://example.com/story', 4.8, '49.99', 'London', 'Available', '2025-06-08 11:20:00', '2025-06-08 11:20:00', 2, 'Ana', NULL),
(18, 'Graphics', 'Visually stunning, especially on high settings.', 'https://example.com/graphics', 4.9, '59.99', 'Berlin', 'Available', '2025-06-08 11:25:00', '2025-06-08 11:25:00', 3, 'Luis', NULL),
(19, 'Multiplayer', 'Multiplayer mode is chaotic fun.', 'https://example.com/multiplayer', 4.5, '44.99', 'New York', 'Available', '2025-06-08 11:30:00', '2025-06-08 11:30:00', 4, 'Carmen', NULL),
(20, 'Customization', 'Love the character customization options.', 'https://example.com/customization', 4.4, '39.99', 'Los Angeles', 'Available', '2025-06-08 11:35:00', '2025-06-08 11:35:00', 5, 'Pedro', NULL),
(21, 'Puzzles', 'Some puzzles are really clever.', 'https://example.com/puzzles', 4.3, '44.99', 'Tokyo', 'Available', '2025-06-08 11:40:00', '2025-06-08 11:40:00', 1, 'Juan', NULL),
(22, 'Atmosphere', 'The atmosphere is dark and mysterious.', 'https://example.com/atmosphere', 4.2, '39.99', 'Paris', 'Available', '2025-06-08 11:45:00', '2025-06-08 11:45:00', 2, 'Ana', NULL),
(23, 'Bosses', 'Boss battles are epic and memorable.', 'https://example.com/bosses', 4.7, '49.99', 'London', 'Available', '2025-06-08 11:50:00', '2025-06-08 11:50:00', 3, 'Luis', NULL),
(24, 'Side Quests', 'Side quests are as good as the main story.', 'https://example.com/side-quests', 4.6, '44.99', 'Berlin', 'Available', '2025-06-08 11:55:00', '2025-06-08 11:55:00', 4, 'Carmen', NULL),
(25, 'Performance', 'Runs smoothly even on older hardware.', 'https://example.com/performance', 4.5, '39.99', 'New York', 'Available', '2025-06-08 12:00:00', '2025-06-08 12:00:00', 5, 'Pedro', NULL),
(26, 'Bugs', 'A few bugs but nothing game-breaking.', 'https://example.com/bugs', 3.9, '34.99', 'Los Angeles', 'Available', '2025-06-08 12:05:00', '2025-06-08 12:05:00', 1, 'Juan', NULL),
(27, 'DLC', 'Looking forward to the upcoming DLC.', 'https://example.com/dlc', 4.1, '19.99', 'Tokyo', 'Available', '2025-06-08 12:10:00', '2025-06-08 12:10:00', 2, 'Ana', NULL),
(28, 'AI', 'Enemy AI is challenging and unpredictable.', 'https://example.com/ai', 4.0, '44.99', 'Paris', 'Available', '2025-06-08 12:15:00', '2025-06-08 12:15:00', 3, 'Luis', NULL),
(29, 'Length', 'Took me over 50 hours to finish everything.', 'https://example.com/length', 4.2, '39.99', 'London', 'Available', '2025-06-08 12:20:00', '2025-06-08 12:20:00', 4, 'Carmen', NULL),
(30, 'Voice Acting', 'Voice acting is professional and emotional.', 'https://example.com/voice-acting', 4.8, '34.99', 'Berlin', 'Available', '2025-06-08 12:25:00', '2025-06-08 12:25:00', 5, 'Pedro', NULL);

-- Añadir la foreign key después
ALTER TABLE `listings`
  ADD CONSTRAINT `fk_listings_user` FOREIGN KEY (`user_id`) REFERENCES `users`(`id`);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `listing_images`
--

DROP TABLE IF EXISTS `listing_images`;
CREATE TABLE `listing_images` (
  `id` bigint(20) NOT NULL,
  `listing_id` bigint(20) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `listing_images`
--

DELETE FROM `listing_images`;

INSERT INTO `listing_images` (`id`, `listing_id`, `image_url`) VALUES
-- Listing 1: Epic Adventure (The Legend of Zelda: Breath of the Wild)
(1, 1, 'https://upload.wikimedia.org/wikipedia/en/0/0d/The_Legend_of_Zelda_Breath_of_the_Wild.jpg'),
(2, 1, 'https://upload.wikimedia.org/wikipedia/commons/6/6e/Zelda_Breath_of_the_Wild_artwork.png'),
(3, 1, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Zelda_Breath_of_the_Wild_logo.png'),
-- Listing 2: Not Bad (Grand Theft Auto V)
(4, 2, 'https://upload.wikimedia.org/wikipedia/en/a/a5/Grand_Theft_Auto_V.png'),
(5, 2, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/GTA_V_logo.svg'),
(6, 2, 'https://upload.wikimedia.org/wikipedia/commons/2/2d/GTAV_boxart.jpg'),
-- Listing 3: Great Graphics (Horizon Zero Dawn)
(7, 3, 'https://upload.wikimedia.org/wikipedia/en/0/0d/Horizon_Zero_Dawn.jpg'),
(8, 3, 'https://upload.wikimedia.org/wikipedia/commons/2/2d/Horizon_Zero_Dawn_logo.png'),
(9, 3, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Horizon_Zero_Dawn_artwork.jpg'),
-- Listing 4: Challenging (Dark Souls III)
(10, 4, 'https://upload.wikimedia.org/wikipedia/en/8/8d/Dark_Souls_III_cover_art.jpg'),
(11, 4, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Dark_Souls_III_logo.png'),
(12, 4, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Dark_Souls_III_artwork.jpg'),
-- Listing 5: Relaxing (Animal Crossing: New Horizons)
(13, 5, 'https://upload.wikimedia.org/wikipedia/en/9/9b/Animal_Crossing_New_Horizons.jpg'),
(14, 5, 'https://upload.wikimedia.org/wikipedia/commons/3/3c/Animal_Crossing_Logo.png'),
(15, 5, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Animal_Crossing_New_Horizons_artwork.jpg'),
-- Listing 6: Addictive (Tetris)
(16, 6, 'https://upload.wikimedia.org/wikipedia/en/7/7c/Tetris_Boxshot.png'),
(17, 6, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Tetris_logo.svg'),
(18, 6, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Tetris_artwork.png'),
-- Listing 7: Admin Review (Minecraft)
(19, 7, 'https://upload.wikimedia.org/wikipedia/en/2/2d/Minecraft_cover.png'),
(20, 7, 'https://upload.wikimedia.org/wikipedia/commons/5/51/Minecraft_logo.svg'),
(21, 7, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Minecraft_artwork.jpg'),
-- Listing 8: Fun with Friends (Among Us)
(22, 8, 'https://upload.wikimedia.org/wikipedia/en/2/2a/Among_Us_cover_art.jpg'),
(23, 8, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Among_Us_logo.png'),
(24, 8, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Among_Us_artwork.jpg'),
-- Listing 9: Classic (Super Mario Bros.)
(25, 9, 'https://upload.wikimedia.org/wikipedia/en/0/03/Super_Mario_Bros._box.png'),
(26, 9, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Super_Mario_Bros_logo.png'),
(27, 9, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Super_Mario_Bros_artwork.png'),
-- Listing 10: Too Short (Celeste)
(28, 10, 'https://upload.wikimedia.org/wikipedia/en/0/0a/Celeste_boxart.jpg'),
(29, 10, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Celeste_logo.png'),
(30, 10, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Celeste_artwork.jpg'),
-- Listing 11: Buggy (Cyberpunk 2077)
(31, 11, 'https://upload.wikimedia.org/wikipedia/en/9/9f/Cyberpunk_2077_box_art.jpg'),
(32, 11, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Cyberpunk_2077_logo.png'),
(33, 11, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Cyberpunk_2077_artwork.jpg'),
-- Listing 12: Masterpiece (The Witcher 3)
(34, 12, 'https://upload.wikimedia.org/wikipedia/en/6/6a/The_Witcher_3_cover_art.jpg'),
(35, 12, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/The_Witcher_3_logo.png'),
(36, 12, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/The_Witcher_3_artwork.jpg'),
-- Listing 13: Immersive (Red Dead Redemption II)
(37, 13, 'https://upload.wikimedia.org/wikipedia/en/7/7e/Red_Dead_Redemption_II.jpg'),
(38, 13, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Red_Dead_Redemption_II_logo.png'),
(39, 13, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Red_Dead_Redemption_II_artwork.jpg'),
-- Listing 14: Soundtrack (Persona 5)
(40, 14, 'https://upload.wikimedia.org/wikipedia/en/6/6d/Persona_5_cover_art.jpg'),
(41, 14, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Persona_5_logo.png'),
(42, 14, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Persona_5_artwork.jpg'),
-- Listing 15: Replay Value (The Binding of Isaac)
(43, 15, 'https://upload.wikimedia.org/wikipedia/en/7/7e/The_Binding_of_Isaac_cover.png'),
(44, 15, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/The_Binding_of_Isaac_logo.png'),
(45, 15, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/The_Binding_of_Isaac_artwork.jpg'),
-- Listing 16: Controls (Hollow Knight)
(46, 16, 'https://upload.wikimedia.org/wikipedia/en/2/2c/Hollow_Knight_cover.jpg'),
(47, 16, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Hollow_Knight_logo.png'),
(48, 16, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Hollow_Knight_artwork.jpg'),
-- Listing 17: Story (The Last of Us)
(49, 17, 'https://upload.wikimedia.org/wikipedia/en/4/4f/The_Last_of_Us_cover.jpg'),
(50, 17, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/The_Last_of_Us_logo.png'),
(51, 17, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/The_Last_of_Us_artwork.jpg'),
-- Listing 18: Graphics (DOOM Eternal)
(52, 18, 'https://upload.wikimedia.org/wikipedia/en/3/3a/DOOM_Cover.jpg'),
(53, 18, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/DOOM_logo.png'),
(54, 18, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/DOOM_artwork.jpg'),
-- Listing 19: Multiplayer (Overwatch)
(55, 19, 'https://upload.wikimedia.org/wikipedia/en/8/8d/Overwatch_cover_art.jpg'),
(56, 19, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Overwatch_logo.png'),
(57, 19, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Overwatch_artwork.jpg'),
-- Listing 20: Customization (The Sims 4)
(58, 20, 'https://upload.wikimedia.org/wikipedia/en/0/0b/The_Sims_4_cover_art.jpg'),
(59, 20, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/The_Sims_4_logo.png'),
(60, 20, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/The_Sims_4_artwork.jpg'),
-- Listing 21: Puzzles (Portal 2)
(61, 21, 'https://upload.wikimedia.org/wikipedia/en/2/2e/Portal2cover.jpg'),
(62, 21, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Portal_2_logo.png'),
(63, 21, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Portal_2_artwork.jpg'),
-- Listing 22: Atmosphere (Limbo)
(64, 22, 'https://upload.wikimedia.org/wikipedia/en/7/7e/Limbo_Box_Art.jpg'),
(65, 22, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Limbo_logo.png'),
(66, 22, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Limbo_artwork.jpg'),
-- Listing 23: Bosses (Shadow of the Colossus)
(67, 23, 'https://upload.wikimedia.org/wikipedia/en/0/0c/Shadow_of_the_Colossus_cover.jpg'),
(68, 23, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Shadow_of_the_Colossus_logo.png'),
(69, 23, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Shadow_of_the_Colossus_artwork.jpg'),
-- Listing 24: Side Quests (The Elder Scrolls V: Skyrim)
(70, 24, 'https://upload.wikimedia.org/wikipedia/en/8/8c/The_Elder_Scrolls_V_Skyrim_cover.png'),
(71, 24, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Skyrim_logo.png'),
(72, 24, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Skyrim_artwork.jpg'),
-- Listing 25: Performance (Forza Horizon 4)
(73, 25, 'https://upload.wikimedia.org/wikipedia/en/7/7e/Forza_Horizon_4_cover_art.jpg'),
(74, 25, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Forza_Horizon_4_logo.png'),
(75, 25, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Forza_Horizon_4_artwork.jpg'),
-- Listing 26: Bugs (Fallout 4)
(76, 26, 'https://upload.wikimedia.org/wikipedia/en/7/70/Fallout_4_cover_art.jpg'),
(77, 26, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Fallout_4_logo.png'),
(78, 26, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Fallout_4_artwork.jpg'),
-- Listing 27: DLC (The Witcher 3: Blood and Wine)
(79, 27, 'https://upload.wikimedia.org/wikipedia/en/6/6a/The_Witcher_3_cover_art.jpg'),
(80, 27, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/The_Witcher_3_logo.png'),
(81, 27, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/The_Witcher_3_artwork.jpg'),
-- Listing 28: AI (Halo: Combat Evolved)
(82, 28, 'https://upload.wikimedia.org/wikipedia/en/3/3e/Halo_-_Combat_Evolved_(XBox_version_-_box_art).jpg'),
(83, 28, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Halo_logo.png'),
(84, 28, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Halo_artwork.jpg'),
-- Listing 29: Length (Persona 5 Royal)
(85, 29, 'https://upload.wikimedia.org/wikipedia/en/6/6d/Persona_5_cover_art.jpg'),
(86, 29, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/Persona_5_logo.png'),
(87, 29, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/Persona_5_artwork.jpg'),
-- Listing 30: Voice Acting (The Last of Us Part II)
(88, 30, 'https://upload.wikimedia.org/wikipedia/en/4/4f/The_Last_of_Us_Part_II_cover_art.jpg'),
(89, 30, 'https://upload.wikimedia.org/wikipedia/commons/7/7e/The_Last_of_Us_Part_II_logo.png'),
(90, 30, 'https://upload.wikimedia.org/wikipedia/commons/2/2e/The_Last_of_Us_Part_II_artwork.jpg');

--
-- Indices de la tabla `listing_images`
--
ALTER TABLE `listing_images`
  ADD KEY `idx_listing_images_listing` (`listing_id`);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `listing_tags`
--

DROP TABLE IF EXISTS `listing_tags`;
CREATE TABLE `listing_tags` (
  `listing_id` bigint(20) NOT NULL,
  `tags` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `listing_tags`
--

INSERT INTO `listing_tags` (`listing_id`, `tags`) VALUES
(1, 'RPG'),
(1, 'Adventure'),
(2, 'Open World'),
(2, 'Challenge'),
(3, 'Roguelike'),
(3, 'Indie'),
(4, 'Simulation'),
(4, 'Relax'),
(5, 'Metroidvania'),
(5, 'Exploration'),
(6, 'Platformer'),
(6, 'Family'),
(7, 'Action'),
(7, 'Narrative'),
(8, 'Sandbox'),
(8, 'Creativity'),
(9, 'Shooter'),
(9, 'Battle Royale'),
(10, 'Cyberpunk'),
(10, 'Futuristic'),
(11, 'Boss Fight'),
(11, 'Indie'),
(12, 'Farming'),
(12, 'Pixel Art'),
(13, 'Battle Royale'),
(13, 'Multijugador'),
(13, 'Equipos'),
(13, 'Futurista'),
(13, 'Héroes'),
(13, 'Shooter'),
(14, 'Shooter'),
(14, 'Táctico'),
(14, 'Multijugador'),
(14, 'Agentes'),
(14, 'Estrategia'),
(14, 'Acción'),
(15, 'Shooter'),
(15, 'Héroes'),
(15, 'Multijugador'),
(15, 'Futurista'),
(15, 'Cooperativo'),
(15, 'Acción'),
(16, 'RPG'),
(16, 'Acción'),
(16, 'Fantasía'),
(16, 'Geralt'),
(16, 'Mundo Abierto'),
(16, 'Narrativa'),
(17, 'Acción'),
(17, 'Mundo Abierto'),
(17, 'Crimen'),
(17, 'Conducción'),
(17, 'Cooperativo'),
(17, 'Competitivo'),
(18, 'Cyberpunk'),
(18, 'RPG'),
(18, 'Acción'),
(18, 'Hackeo'),
(18, 'Rebeldía'),
(18, 'Futurista'),
(19, 'Deportes'),
(19, 'Fútbol'),
(19, 'Simulador'),
(19, 'Ligas'),
(19, 'Competitivo'),
(19, 'Multijugador'),
(20, 'Simulación'),
(20, 'Familia'),
(20, 'Animales'),
(20, 'Creatividad'),
(20, 'Social'),
(20, 'DIY'),
(21, 'Pokémon'),
(21, 'Aventura'),
(21, 'Exploración'),
(21, 'Fantasía'),
(21, 'Criaturas'),
(21, 'Mundo Abierto'),
(22, 'Terror'),
(22, 'Supervivencia'),
(22, 'Misterio'),
(22, 'Gore'),
(22, 'Acción'),
(22, 'Cinemático'),
(23, 'Arcade'),
(23, 'Lucha'),
(23, 'Retro'),
(23, 'Cooperativo'),
(23, 'Callejero'),
(23, 'Acción'),
(24, 'Acción'),
(24, 'Mitos'),
(24, 'Fantasía'),
(24, 'Aventura'),
(24, 'Narrativa'),
(24, 'Jefes'),
(25, 'RPG'),
(25, 'Final Fantasy'),
(25, 'Fantasía'),
(25, 'Épico'),
(25, 'Clásico'),
(25, 'Música'),
(26, 'RPG'),
(26, 'Ópera Espacial'),
(26, 'Ciencia Ficción'),
(26, 'Decisiones'),
(26, 'Acción'),
(26, 'Futurista'),
(27, 'Cooperativo'),
(27, 'Cocina'),
(27, 'Fiesta'),
(27, 'Humor'),
(27, 'Indie'),
(27, 'Multijugador'),
(28, 'Aventura'),
(28, 'Mazmorras'),
(28, 'Pixel Art'),
(28, 'Exploración'),
(28, 'Acción'),
(28, 'Cooperativo'),
(29, 'Velocidad'),
(29, 'Plataformas'),
(29, 'Clásico'),
(29, 'Futurista'),
(29, 'Arcade'),
(29, 'Fiesta'),
(30, 'Samurai'),
(30, 'Acción'),
(30, 'Historia'),
(30, 'Mundo Abierto'),
(30, 'Stealth'),
(30, 'Japón Feudal'),
(31, 'Simulación'),
(31, 'Espacio'),
(31, 'Ciencia'),
(31, 'Educativo'),
(31, 'Cohetes'),
(31, 'Exploración'),
(32, 'JRPG'),
(32, 'Social'),
(32, 'Academia'),
(32, 'Misiones'),
(32, 'Amigos'),
(32, 'Fantasía'),
(33, 'Lucha'),
(33, 'Multijugador'),
(33, 'Nintendo'),
(33, 'Fiesta'),
(33, 'Competitivo'),
(33, 'Clásico'),
(34, 'Plataformas'),
(34, 'Indie'),
(34, 'Desafíos'),
(34, 'Narrativa'),
(34, 'Pixel Art'),
(34, 'Reflexión'),
(35, 'Acción'),
(35, 'Aventura'),
(35, 'Futurista'),
(35, 'Fantasía'),
(35, 'Exploración'),
(35, 'Jefes'),
(36, 'MOBA'),
(36, 'Estrategia'),
(36, 'Multijugador'),
(36, 'Campeones'),
(36, 'Competitivo'),
(36, 'Fantasy'),
(37, 'Cooperativo'),
(37, 'Aventura'),
(37, 'Prisión'),
(37, 'Acción'),
(37, 'Narrativa'),
(37, 'Cinemático'),
(38, 'MOBA'),
(38, 'Estrategia'),
(38, 'Héroes'),
(38, '5v5'),
(38, 'Fantasía'),
(38, 'Acción'),
(39, 'Sandbox'),
(39, 'Supervivencia'),
(39, 'Biomas'),
(39, 'Construcción'),
(39, 'Creatividad'),
(39, 'Noche'),
(40, 'Supervivencia'),
(40, 'Dinosaurios'),
(40, 'Acción'),
(40, 'Construcción'),
(40, 'Multijugador'),
(40, 'Crafting'),
(41, 'Acción'),
(41, 'Western'),
(41, 'Mundo Abierto'),
(41, 'Narrativa'),
(41, 'Épico'),
(41, 'Aventura'),
(42, 'FPS'),
(42, 'Bélico'),
(42, 'Competitivo'),
(42, 'Historia'),
(42, 'Acción'),
(42, 'Multijugador'),
(43, 'JRPG'),
(43, 'Social'),
(43, 'Fantasía'),
(43, 'Misterio'),
(43, 'Narrativa'),
(43, 'Escolar'),
(44, 'Shooter'),
(44, 'Pintura'),
(44, 'Colorido'),
(44, 'Multijugador'),
(44, 'Nintendo'),
(44, 'Acción'),
(45, 'RPG'),
(45, 'Fantasía'),
(45, 'Música'),
(45, 'Encantamiento'),
(45, 'Animación'),
(45, 'Mágico'),
(46, 'Acción'),
(46, 'Hackeo'),
(46, 'Stealth'),
(46, 'Tecnología'),
(46, 'Futurista'),
(46, 'Mundo Abierto'),
(47, 'FPS'),
(47, 'Futurista'),
(47, 'Guerra'),
(47, 'Multijugador'),
(47, 'Competitivo'),
(47, 'Vehículos'),
(48, 'Plataformas'),
(48, 'Indie'),
(48, 'Boss Fight'),
(48, 'Pixel Art'),
(48, 'Cooperativo'),
(48, 'Retro'),
(49, 'JRPG'),
(49, 'Misterio'),
(49, 'Adolescencia'),
(49, 'Investigación'),
(49, 'Fantasía'),
(49, 'TV'),
(50, 'Terror'),
(50, 'Remake'),
(50, 'Zombies'),
(50, 'Suspenso'),
(50, 'Puzzles'),
(50, 'Acción'),
(51, 'JRPG'),
(51, 'Estrategia'),
(51, 'Social'),
(51, 'Bandas'),
(51, 'Rebelión'),
(51, 'Táctica'),
(52, 'Sandbox'),
(52, 'Pixel Art'),
(52, 'Exploración'),
(52, 'Crafting'),
(52, 'Jefes'),
(52, '2D'),
(53, 'RPG'),
(53, 'Souls-like'),
(53, 'Oscuro'),
(53, 'Medieval'),
(53, 'Desafío'),
(53, 'Acción'),
(54, 'Ritmo'),
(54, 'Baile'),
(54, 'Fiesta'),
(54, 'Música'),
(54, 'Competitivo'),
(54, 'Pop'),
(55, 'Social'),
(55, 'Multijugador'),
(55, 'Deducción'),
(55, 'Espacio'),
(55, 'Impostores'),
(55, 'Indie'),
(56, 'tag1'),
(56, 'tag2'),
(56, 'tag3'),
(57, 'dd'),
(57, 'e');

--
-- Indices de la tabla `listing_tags`
--
ALTER TABLE `listing_tags`
  ADD KEY `idx_listing_tags_listing` (`listing_id`);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `player`
--

DROP TABLE IF EXISTS `player`;
CREATE TABLE `player` (
  `id` bigint(20) NOT NULL,
  `date_game` datetime(6) DEFAULT NULL,
  `destroyed_blocks` int(11) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `player`
--

INSERT INTO `player` (`id`, `date_game`, `destroyed_blocks`, `score`, `user_id`, `user_name`) VALUES
(1, '2025-06-04 14:00:00.000000', 150, 12000, 1, 'Juan'),
(2, '2025-06-04 14:10:00.000000', 130, 11500, 2, 'Ana'),
(3, '2025-06-04 14:20:00.000000', 160, 12500, 3, 'Luis'),
(4, '2025-06-04 14:30:00.000000', 170, 13000, 4, 'Carmen'),
(5, '2025-06-04 14:40:00.000000', 140, 11800, 5, 'Pedro');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ratings`
--

DROP TABLE IF EXISTS `ratings`;
CREATE TABLE `ratings` (
  `id` bigint(20) NOT NULL,
  `listing_id` bigint(20) DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ratings`
--

INSERT INTO `ratings` (`id`, `listing_id`, `rating`, `user_id`) VALUES
(1, 1, 5, 2),
(2, 1, 4.9, 3),
(3, 2, 4.8, 1),
(4, 2, 4.7, 5),
(5, 3, 4.6, 4),
(6, 3, 4.5, 2),
(7, 4, 4.7, 3),
(8, 4, 4.6, 5),
(9, 5, 4.8, 1),
(10, 5, 4.9, 4);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `listings`
--
-- ALTER TABLE `listings`
--   ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `listing_images`
--
ALTER TABLE `listing_images`