-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-06-2025 a las 10:30:34
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
-- Base de datos: `breakout_db`
--
CREATE DATABASE IF NOT EXISTS `breakout_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `breakout_db`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comments`
--

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `title` varchar(250) DEFAULT NULL,
  `text` text DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `user_id` bigint(20) UNSIGNED DEFAULT NULL,
  `listing_id` bigint(20) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `comments`
--

INSERT INTO `comments` (`id`, `user_name`, `title`, `text`, `created_at`, `user_id`, `listing_id`) VALUES
(1, 'sophie', 'Great Game!', 'Amazing gameplay and visuals!', '2025-03-25 23:57:54', 6, 1),
(2, 'mia', 'Great Game!', 'Amazing gameplay and visuals!', '2025-06-07 23:57:54', 10, 1),
(3, 'daniel', 'Great Game!', 'The world feels immersive!', '2025-05-29 23:57:54', 10, 1),
(4, 'grace', 'Great Game!', 'Great multiplayer fun.', '2025-05-29 23:57:54', 6, 1),
(5, 'jordan', 'Great Game!', 'Amazing gameplay and visuals!', '2025-06-01 23:57:54', 6, 2),
(6, 'sophie', 'Great Game!', 'Great multiplayer fun.', '2025-04-16 23:57:54', 8, 2),
(8, 'daniel', 'Great Game!', 'Great multiplayer fun.', '2025-05-08 23:57:54', 6, 2),
(9, 'sophie', 'Great Game!', 'Amazing gameplay and visuals!', '2025-04-13 23:57:54', 9, 2),
(20, 'taylor', 'Great Game!', 'Great multiplayer fun.', '2025-05-22 23:57:55', 8, 5),
(21, 'jordan', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-03-21 23:57:55', 8, 5),
(22, 'olivia', 'Great Game!', 'Great multiplayer fun.', '2025-05-21 23:57:55', 5, 5),
(23, 'Alex Accounts', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-03-13 23:57:55', 9, 5),
(24, 'taylor', 'Great Game!', 'The world feels immersive!', '2025-03-26 23:57:55', 10, 5),
(25, 'mia', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-04-17 23:57:55', 7, 5),
(53, 'taylor', 'Great Game!', 'The world feels immersive!', '2025-04-08 23:57:57', 8, 11),
(54, 'olivia', 'Great Game!', 'The world feels immersive!', '2025-04-16 23:57:57', 8, 11),
(55, 'daniel', 'Great Game!', 'Loved the new tracks, very creative.', '2025-05-20 23:57:57', 8, 11),
(56, 'alex', 'Great Game!', 'The world feels immersive!', '2025-05-17 23:57:57', 1, 11),
(62, 'taylor', 'Great Game!', 'Loved the new tracks, very creative.', '2025-04-22 23:57:57', 7, 12),
(63, 'ethan', 'Great Game!', 'Great multiplayer fun.', '2025-04-06 23:57:57', 5, 12),
(64, 'taylor', 'Great Game!', 'Loved the new tracks, very creative.', '2025-03-27 23:57:57', 11, 12),
(76, 'daniel', 'Great Game!', 'Great multiplayer fun.', '2025-06-04 23:57:57', 8, 14),
(77, 'sophie', 'Great Game!', 'Great multiplayer fun.', '2025-06-04 23:57:57', 11, 14),
(80, 'taylor', 'Great Game!', 'Amazing gameplay and visuals!', '2025-03-24 23:57:57', 10, 14),
(81, 'taylor', 'Great Game!', 'The world feels immersive!', '2025-04-12 23:57:58', 9, 15),
(82, 'grace', 'Great Game!', 'Loved the new tracks, very creative.', '2025-06-04 23:57:58', 11, 15),
(83, 'sophie', 'Great Game!', 'Great multiplayer fun.', '2025-05-14 23:57:58', 10, 15),
(84, 'Alex Accounts', 'Great Game!', 'The world feels immersive!', '2025-05-21 23:57:58', 8, 15),
(85, 'taylor', 'Great Game!', 'Great multiplayer fun.', '2025-04-03 23:57:58', 9, 15),
(86, 'Alex Accounts', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-05-21 23:57:58', 8, 15),
(91, 'Alex Accounts', 'Great Game!', 'Great multiplayer fun.', '2025-03-22 23:57:58', 7, 16),
(92, 'grace', 'Great Game!', 'Loved the new tracks, very creative.', '2025-04-29 23:57:58', 4, 16),
(94, 'olivia', 'Great Game!', 'Great multiplayer fun.', '2025-05-15 23:57:58', 10, 16),
(95, 'olivia', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-05-14 23:57:58', 1, 16),
(109, 'Alex Accounts', 'Great Game!', 'Loved the new tracks, very creative.', '2025-04-12 23:57:58', 10, 18),
(111, 'daniel', 'Great Game!', 'Loved the new tracks, very creative.', '2025-04-15 23:57:58', 5, 18),
(113, 'alex', 'Great Game!', 'Amazing gameplay and visuals!', '2025-05-22 23:57:58', 9, 18),
(114, 'alex', 'Great Game!', 'Loved the new tracks, very creative.', '2025-05-12 23:57:58', 11, 18),
(116, 'alex', 'Great Game!', 'The world feels immersive!', '2025-03-16 23:57:59', 4, 19),
(117, 'daniel', 'Great Game!', 'Great multiplayer fun.', '2025-05-01 23:57:59', 11, 19),
(118, 'ethan', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-04-07 23:57:59', 11, 19),
(119, 'ethan', 'Great Game!', 'Amazing gameplay and visuals!', '2025-04-28 23:57:59', 5, 19),
(120, 'olivia', 'Great Game!', 'Great multiplayer fun.', '2025-03-16 23:57:59', 1, 19),
(121, 'mia', 'Great Game!', 'Loved the new tracks, very creative.', '2025-04-22 23:57:59', 1, 19),
(143, 'grace', 'Great Game!', 'Loved the new tracks, very creative.', '2025-06-09 23:58:00', 9, 23),
(144, 'taylor', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-04-07 23:58:00', 9, 23),
(151, 'alex', 'Great Game!', 'Amazing gameplay and visuals!', '2025-05-16 23:58:00', 11, 24),
(152, 'henry', 'Great Game!', 'Amazing gameplay and visuals!', '2025-05-04 23:58:00', 5, 24),
(153, 'jordan', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-05-08 23:58:00', 7, 24),
(154, 'olivia', 'Great Game!', 'The world feels immersive!', '2025-05-13 23:58:00', 8, 24),
(155, 'ethan', 'Great Game!', 'Amazing gameplay and visuals!', '2025-03-30 23:58:00', 7, 24),
(156, 'henry', 'Great Game!', 'Loved the new tracks, very creative.', '2025-04-15 23:58:00', 4, 25),
(157, 'daniel', 'Great Game!', 'Amazing gameplay and visuals!', '2025-04-11 23:58:00', 6, 25),
(158, 'alex', 'Great Game!', 'The world feels immersive!', '2025-04-10 23:58:00', 10, 25),
(159, 'taylor', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-04-26 23:58:00', 9, 25),
(163, 'sophie', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-05-02 23:58:00', 11, 26),
(164, 'olivia', 'Great Game!', 'Great multiplayer fun.', '2025-04-29 23:58:00', 1, 26),
(165, 'ethan', 'Great Game!', 'Amazing gameplay and visuals!', '2025-05-25 23:58:00', 6, 26),
(166, 'ethan', 'Great Game!', 'The world feels immersive!', '2025-03-13 23:58:00', 4, 26),
(167, 'daniel', 'Great Game!', 'Amazing gameplay and visuals!', '2025-04-11 23:58:00', 10, 26),
(168, 'jordan', 'Great Game!', 'The world feels immersive!', '2025-04-04 23:58:01', 1, 27),
(169, 'henry', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-05-02 23:58:01', 1, 27),
(170, 'taylor', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-04-05 23:58:01', 1, 27),
(171, 'olivia', 'Great Game!', 'Loved the new tracks, very creative.', '2025-04-16 23:58:01', 4, 27),
(172, 'taylor', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-04-02 23:58:01', 9, 27),
(173, 'jordan', 'Great Game!', 'Loved the new tracks, very creative.', '2025-05-11 23:58:01', 1, 28),
(174, 'olivia', 'Great Game!', 'The world feels immersive!', '2025-05-23 23:58:01', 1, 28),
(176, 'mia', 'Great Game!', 'Loved the new tracks, very creative.', '2025-04-15 23:58:01', 6, 28),
(300, 'Alex Accounts', 'Great Game!', 'Great multiplayer fun.', '2025-03-12 23:58:02', 4, 31),
(301, 'taylor', 'Great Game!', 'The world feels immersive!', '2025-06-08 23:58:02', 1, 31),
(302, 'mia', 'Great Game!', 'Loved the new tracks, very creative.', '2025-06-06 23:58:02', 7, 31),
(303, 'grace', 'Great Game!', 'Great multiplayer fun.', '2025-03-14 23:58:02', 7, 31),
(305, 'ethan', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-03-14 23:58:02', 7, 31),
(312, 'taylor', 'Great Game!', 'The world feels immersive!', '2025-05-13 23:58:02', 1, 33),
(313, 'taylor', 'Great Game!', 'The world feels immersive!', '2025-04-19 23:58:02', 4, 33),
(314, 'henry', 'Great Game!', 'Amazing gameplay and visuals!', '2025-04-04 23:58:02', 5, 33),
(316, 'daniel', 'Great Game!', 'The world feels immersive!', '2025-05-06 23:58:02', 7, 33),
(318, 'ethan', 'Great Game!', 'Amazing gameplay and visuals!', '2025-04-03 23:58:02', 6, 34),
(319, 'henry', 'Great Game!', 'The world feels immersive!', '2025-04-26 23:58:02', 11, 34),
(320, 'jordan', 'Great Game!', 'Amazing gameplay and visuals!', '2025-05-04 23:58:02', 9, 34),
(321, 'mia', 'Great Game!', 'Great multiplayer fun.', '2025-05-21 23:58:02', 7, 34),
(322, 'jordan', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-03-17 23:58:03', 7, 35),
(323, 'alex', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-05-03 23:58:03', 5, 35),
(325, 'mia', 'Great Game!', 'Amazing gameplay and visuals!', '2025-04-19 23:58:03', 4, 35),
(326, 'ethan', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-04-03 23:58:03', 7, 35),
(327, 'ethan', 'Great Game!', 'Loved the new tracks, very creative.', '2025-03-23 23:58:03', 5, 35),
(334, 'henry', 'Great Game!', 'The world feels immersive!', '2025-04-24 23:58:03', 9, 37),
(335, 'alex', 'Great Game!', 'Great multiplayer fun.', '2025-04-02 23:58:03', 9, 37),
(336, 'ethan', 'Great Game!', 'The world feels immersive!', '2025-05-18 23:58:03', 4, 37),
(337, 'ethan', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-05-12 23:58:03', 5, 37),
(338, 'henry', 'Great Game!', 'Great multiplayer fun.', '2025-06-05 23:58:03', 11, 37),
(339, 'ethan', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-03-15 23:58:03', 7, 38),
(341, 'jordan', 'Great Game!', 'Loved the new tracks, very creative.', '2025-04-17 23:58:03', 9, 38),
(342, 'jordan', 'Great Game!', 'Great multiplayer fun.', '2025-05-05 23:58:03', 8, 38),
(351, 'ethan', 'Great Game!', 'Amazing gameplay and visuals!', '2025-03-16 23:58:05', 8, 44),
(352, 'daniel', 'Great Game!', 'Great multiplayer fun.', '2025-04-27 23:58:05', 5, 44),
(353, 'taylor', 'Great Game!', 'The world feels immersive!', '2025-03-22 23:58:05', 11, 44),
(355, 'jordan', 'Great Game!', 'The world feels immersive!', '2025-06-06 23:58:05', 9, 44),
(368, 'mia', 'Great Game!', 'The world feels immersive!', '2025-03-16 23:58:06', 7, 47),
(369, 'sophie', 'Great Game!', 'Power-ups feel balanced and fun.', '2025-04-14 23:58:06', 11, 47),
(370, 'sophie', 'Great Game!', 'Loved the new tracks, very creative.', '2025-05-09 23:58:06', 1, 47),
(371, 'grace', 'Great Game!', 'Loved the new tracks, very creative.', '2025-05-03 23:58:06', 8, 47),
(377, 'henry', 'Great Game!', 'Great multiplayer fun.', '2025-05-08 23:58:06', 1, 49),
(378, 'jordan', 'Great Game!', 'Amazing gameplay and visuals!', '2025-06-05 23:58:06', 1, 49),
(379, 'ethan', 'Great Game!', 'Great multiplayer fun.', '2025-04-10 23:58:06', 9, 49),
(380, 'mia', 'Great Game!', 'The world feels immersive!', '2025-05-06 23:58:06', 9, 49),
(381, 'grace', 'Great Game!', 'Amazing gameplay and visuals!', '2025-04-15 23:58:06', 6, 49),
(382, 'henry', 'Great Game!', 'Loved the new tracks, very creative.', '2025-04-04 23:58:06', 8, 50),
(384, 'grace', 'Great Game!', 'Loved the new tracks, very creative.', '2025-04-30 23:58:06', 11, 50),
(385, 'Alex Accounts', 'Great Game!', 'Amazing gameplay and visuals!', '2025-05-08 23:58:06', 9, 50),
(387, 'daniel', 'Great Game!', 'The world feels immersive!', '2025-03-17 23:58:06', 10, 50);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `listings`
--

DROP TABLE IF EXISTS `listings`;
CREATE TABLE `listings` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `title` varchar(100) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `official_url` varchar(255) NOT NULL,
  `video` varchar(255) DEFAULT NULL,
  `rating` double DEFAULT NULL,
  `user_id` bigint(20) UNSIGNED DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `listings`
--

INSERT INTO `listings` (`id`, `title`, `description`, `user_name`, `official_url`, `video`, `rating`, `user_id`, `created_at`, `updated_at`) VALUES
(1, 'Nightmare Kart: The Old Karts', 'An upcoming free expansion to Nightmare Kart with a brand new mini-campaign, racers, karts, tracks, power-ups, and game modes.', 'alex', 'https://store.steampowered.com/app/nightmare-kart', 'https://i.ytimg.com/vi/MeyYsPcYU8s/hqdefault.jpg', 1.7, 1, '2024-11-22 23:57:54', '2025-05-28 23:57:54'),
(2, 'The Undying Beast', 'There was a flash of light, a choir of glass, and the sound of sirens approaching. Then for what seemed like eternity, a black hollow void. Suddenly you found yourself within a new plane. Dead trees and fog is all that surrounds you. You hear no birds or ', 'alex', 'https://store.steampowered.com/app/undying-beast', '', 4.4, 1, '2024-11-29 23:57:54', '2025-05-29 23:57:54'),
(5, 'Ace wo Nerae!', 'A tennis game for the Super Famicom based on the 1970s manga and anime of the same name.', 'mia', 'https://store.steampowered.com/app/ace-wo-nerae', 'https://i.ytimg.com/vi/QQvVD6IuwYQ/hqdefault.jpg', 4.9, 6, '2024-11-26 23:57:55', '2025-05-25 23:57:55'),
(11, 'Steel Salvo', 'Pilot a hulking mech and protect Triton from robot hordes. With the help of your team, vanquish the robot overlord!  \nExperience super-fast FPS mecha action in this short but challenging single-player game!', 'mia', 'https://store.steampowered.com/app/steel-salvo', 'https://i.ytimg.com/vi/oQidN4hYPhk/hqdefault.jpg', 2.9, 6, '2024-07-02 23:57:57', '2025-06-07 23:57:57'),
(12, 'Sexy Memory Puzzle: Gay Pool', '', 'grace', 'https://store.steampowered.com/app/sexy-memory-puzzle', '', 1.5, 6, '2024-09-29 23:57:57', '2025-05-22 23:57:57'),
(14, 'Killer Instinct: Anniversary Edition', 'The 10th Anniversary Update brings with it two versions of KI:\n\nThe Killer Instinct Base Game will be FREE TO PLAY on all platforms: Xbox consoles (One, Series X|S), Windows PC, and Steam. The free KI base game will feature one free weekly rotating Fighte', 'alex', 'https://store.steampowered.com/app/killer-instinct', 'https://i.ytimg.com/vi/j95W6ac0Pz0/hqdefault.jpg', 3.2, 1, '2024-11-24 23:57:57', '2025-05-12 23:57:57'),
(15, 'Data Loss', 'Corporations suck!! Money\'Surance Corp especially sucks, so you\'re taking it upon yourself to hack the Money\'Surance Corp Tower, steal money from the higher ups, and erase the debts of everyone subject to its monopoly.', 'alex', 'https://store.steampowered.com/app/data-loss', '', 2.3, 1, '2024-07-05 23:57:58', '2025-06-06 23:57:58'),
(16, 'Chowder: Rump-A-Thump', 'Chowder is helping Mung Daal with three different items, thumpster,cutterfly,and spinzle with his rump(end). There are different foods for you to get for the orders. There are lots of hazards for you to dodge,avoid,and keep away.', 'alex', 'https://store.steampowered.com/app/chowder-rump-a-thump', 'https://i.ytimg.com/vi/1pkb-f6tZK8/hqdefault.jpg', 4.3, 1, '2025-03-02 23:57:58', '2025-05-24 23:57:58'),
(18, 'Merge Games Horror Bundle', 'What do dark Nordic fables, intergalactic cruises and Lovecraftian gore all have in common? Gruesome, gory and ghoulish monsters! Dive into the horror-themed worlds of Bramble: The Mountain King, Morbid: The Seven Acolytes and Quantum Error.', 'alex', 'https://store.steampowered.com/bundle/merge-games-horror', '', 4.9, 1, '2024-07-02 23:57:58', '2025-05-27 23:57:58'),
(19, 'Flashy Maze', '', 'taylor', 'https://store.steampowered.com/app/flashy-maze', '', 2.6, 5, '2025-02-25 23:57:59', '2025-06-01 23:57:59'),
(23, 'Soul Rush', '', 'taylor', 'https://store.steampowered.com/app/soul-rush', '', 1.6, 5, '2025-02-23 23:58:00', '2025-05-24 23:58:00'),
(24, 'The Tomb of Corruption', 'Explore procedurally generated dungeons and slash hordes of monsters in THE TOMB OF CORRUPTION! - A 3D action RPG with rogue-lite elements. Fully customize your melee and ranged weapon with hundreds of upgrades, unlock skills, upgrade your armor and get r', 'grace', 'https://store.steampowered.com/app/tomb-of-corruption', 'https://i.ytimg.com/vi/GiwZD2KHHI4/hqdefault.jpg', 2.6, 6, '2024-12-16 23:58:00', '2025-05-14 23:58:00'),
(25, 'Haunt the House: Terrortown', 'A side-scrolling action puzzle game starring ghosts! Leave the dusty halls of an abandoned clock tower to haunt your way through a town in the dead of night! Possess objects with your ghostly soul, to scare people away from a museum, a hospital, a theatre', 'mia', 'https://store.steampowered.com/app/haunt-the-house-terrortown', 'https://i.ytimg.com/vi/Bboxbt5eBqQ/hqdefault.jpg', 77.7, 6, '2025-01-23 23:58:00', '2025-05-15 23:58:00'),
(26, 'Dark Castle', 'The Genesis port of Dark Castle features colored graphics, and music during gameplay.', 'alex', 'https://store.steampowered.com/app/dark-castle', 'https://i.ytimg.com/vi/lpJrvAhTiOw/hqdefault.jpg', 4.1, 1, '2025-04-29 23:58:00', '2025-05-15 23:58:00'),
(27, 'Mortal Kombat HD Arcade Kollection', 'Before the announcement of Mortal Kombat: Arcade Kollection, different online stores started listing Mortal Kombat HD: Arcade Kollection. It was supposed to be a HD remake of the first three Mortal Kombat games.', 'mia', 'https://store.steampowered.com/app/mortal-kombat-hd-arcade-kollection', '', 4.2, 6, '2024-07-02 23:58:00', '2025-05-27 23:58:00'),
(28, 'Mortal Kombat 1', 'Discover a reborn Mortal Kombat Universe created by the Fire God Liu Kang featuring reimagined versions of iconic characters as you\'ve never seen them before, with an all new fighting system, game modes, bone krushing moves and fatalities!', 'jordan', 'https://store.steampowered.com/app/mortal-kombat-1', '', 4.1, 4, '2024-07-02 23:58:01', '2025-05-27 23:58:01'),
(29, 'Mortal Kombat 1 - Kombat Pack', 'The Kombat Pack includes:\n- 6 new playable characters\n- 5 new Kameo Fighters\n- 1 week early access to all DLC characters', 'grace', 'https://store.steampowered.com/app/mortal-kombat-1-kombat-pack', '', 4.2, 6, '2024-07-02 23:58:01', '2025-05-27 23:58:01'),
(31, 'Naruto x Boruto: Ninja Tribes', 'Naruto x Boruto: Ninja Tribes is based on the popular Naruto and Boruto anime series and brings together fan-favorite characters spanning multiple generations of both franchises for one-tap team-based battles.\n\nA turn-based RPG being developed by Bandai N', 'taylor', 'https://store.steampowered.com/app/naruto-boruto-ninja-tribes', '', 4.3, 5, '2024-07-05 23:58:02', '2025-05-30 23:58:02'),
(33, 'Tom Clancy\'s Ghost Recon: Wildlands - The Peruvian Connection', 'Travel to the high mountains of Bolivia to break the deadly alliance between the Santa Blanca and the Peruvian cartels.', 'ethan', 'https://store.steampowered.com/app/ghost-recon-wildlands-peruvian', '', 3.6, 7, '2024-12-31 23:58:02', '2025-05-17 23:58:02'),
(34, 'Sesame Street Mecha Builders', 'Embark on exciting adventures with Mecha Elmo, Cookie Monster, and Abby Cadabby! Explore science, engineering, creativity, and math through engaging games and activities designed for kids aged 2-6. Endless fun and learning await!', 'alex', 'https://store.steampowered.com/app/sesame-street-mecha-builders', 'https://i.ytimg.com/vi/Y6rT_pYzmVc/hqdefault.jpg', 3.8, 1, '2024-11-02 23:58:02', '2025-05-24 23:58:02'),
(35, 'LocoRoco Cocoreccho', 'LocoRoco Cocoreccho! (or Oideyo LocoRoco!! BuuBuu Cocoreccho! (おいでよロコロコ！！ BuuBuu Cocoreccho!?), in Japan, meaning \"LocoRoco Come On Over(!)! BuuBuu Cocoreccho!\") is a video game in the LocoRoco series released on the PlayStation Network for PlayStation 3 ', 'alex', 'https://store.steampowered.com/app/locoroco-cocoreccho', 'https://i.ytimg.com/vi/Q7ta-dYOdPk/hqdefault.jpg', 1.7, 1, '2024-11-12 23:58:03', '2025-06-07 23:58:03'),
(37, 'Blackguards', 'What happens when the only hope of a threatened world lies not with heroes in shining armor, but in the hands of a band of misfits and criminals? Blackguards, a new turn-based strategy RPG, explores this very question. You will discover over 180 unique he', 'olivia', 'https://store.steampowered.com/app/blackguards', 'https://i.ytimg.com/vi/5l4YIPm4vF4/hqdefault.jpg', 64.1, 8, '2024-08-24 23:58:03', '2025-05-21 23:58:03'),
(38, 'Doodle Champs', 'Doodle Champs is the ultimate experience for artists and doodlers alike! Play an exciting assortment of doodle-tastic party games with endless replayability, or just draw on a shared online canvas with your pals!', 'alex', 'https://store.steampowered.com/app/doodle-champs', '', 1.3, 1, '2024-09-07 23:58:03', '2025-05-31 23:58:03'),
(39, 'Phones Not Allowed', 'A game about a teacher who confiscates phones from students.', 'mia', 'https://store.steampowered.com/app/phones-not-allowed', '', 3.2, 6, '2024-11-14 23:58:04', '2025-05-30 23:58:04'),
(40, 'Soulstice: Deluxe Edition', 'Soulstice invites players to uncover the dark truth of the Chimera, a hybrid warrior born of two souls. Briar and Lute are sisters who have been reborn as a Chimera. The transformation has granted Briar superhuman strength and resilience, while Lute, who', 'alex', 'https://store.steampowered.com/app/soulstice', 'https://i.ytimg.com/vi/714YvWDV_mI/hqdefault.jpg', 4.1, 1, '2024-11-14 23:58:04', '2025-05-30 23:58:04'),
(41, 'Soulstice: Digital Artbook', 'Explore the dark fantasy world of Soulstice through this collection of exclusive artwork. Discover character concepts, environments, weapons, and more!', 'ethan', 'https://store.steampowered.com/app/soulstice-artbook', '', 3.8, 7, '2024-11-14 23:58:04', '2025-05-30 23:58:04'),
(44, 'One Last Breath', 'A 2,5D adventure set in a world in which almost no life is left you will play as Gaia, a being born from nature\'s last wish with power to influence the surrounding nature in order to face a dark world while being chased by strange creatures.', 'alex', 'https://store.steampowered.com/app/one-last-breath', 'https://i.ytimg.com/vi/jtJ6FfQwSg8/hqdefault.jpg', 1.9, 1, '2024-10-04 23:58:05', '2025-05-28 23:58:05'),
(47, 'My Hero Academia: The Strongest Hero', 'The game is an open-world action RPG set in the My Hero Academia universe. The game features a decked out story mode where players can build their own fighting teams and take on classic villains like Shigaraki and Nomu.', 'olivia', 'https://store.steampowered.com/app/my-hero-academia-strongest-hero', 'https://i.ytimg.com/vi/f2tnChLs13A/hqdefault.jpg', 1.1, 8, '2024-12-11 23:58:06', '2025-05-24 23:58:06'),
(49, 'Qube Qross', 'Qube Qross is a challenging and logical puzzle game, like Nonogram in 3D. The puzzles can be solved by the hints of numbers in the left and right sides of walls, players need to get where and how many cubes are located, by the intersection of numbers from', 'ethan', 'https://store.steampowered.com/app/qube-qross', 'https://i.ytimg.com/vi/56GgewObkUs/hqdefault.jpg', 1.1, 7, '2025-05-12 23:58:06', '2025-06-05 23:58:06'),
(50, 'Graveyard Keeper: Game of Crone', 'You\'ll have to help the escaped prisoners of the Inquisition survive in the wilderness by providing them with everything they need. To develop their camp to a fortified settlement while keeping in mind its benefits. To protect those who entrusted you with', 'mia', 'https://store.steampowered.com/app/graveyard-keeper-game-of-crone', 'https://i.ytimg.com/vi/ueemNvZcU8A/hqdefault.jpg', 6.6, 6, '2025-01-27 23:58:06', '2025-05-29 23:58:06'),
(76, 'B', 'yr5eyrygry4gy45ry45gyrtgyubrtybhgrybhry', 'Alex Accounts 2', 'https://localhost:80808969', NULL, 4.5, 12, '2025-06-13 08:13:42', '2025-06-13 08:14:37');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `listing_images`
--

DROP TABLE IF EXISTS `listing_images`;
CREATE TABLE `listing_images` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `listing_id` bigint(20) UNSIGNED NOT NULL,
  `image_url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `listing_images`
--

INSERT INTO `listing_images` (`id`, `listing_id`, `image_url`) VALUES
(1, 1, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co9d8y.jpg'),
(2, 1, 'https://images.igdb.com/igdb/image/upload/t_720p/scv9xd.jpg'),
(3, 1, 'https://images.igdb.com/igdb/image/upload/t_720p/scv9xe.jpg'),
(4, 1, 'https://images.igdb.com/igdb/image/upload/t_720p/scv9xh.jpg'),
(5, 1, 'https://images.igdb.com/igdb/image/upload/t_720p/scv9xf.jpg'),
(6, 1, 'https://images.igdb.com/igdb/image/upload/t_720p/scv9xg.jpg'),
(7, 2, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co5hwv.jpg'),
(8, 2, 'https://images.igdb.com/igdb/image/upload/t_720p/scebrt.jpg'),
(9, 2, 'https://images.igdb.com/igdb/image/upload/t_720p/scebru.jpg'),
(10, 2, 'https://images.igdb.com/igdb/image/upload/t_720p/scebrv.jpg'),
(17, 5, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co2kux.jpg'),
(18, 5, 'https://images.igdb.com/igdb/image/upload/t_720p/sc8rt5.jpg'),
(19, 5, 'https://images.igdb.com/igdb/image/upload/t_720p/sc8rt4.jpg'),
(20, 5, 'https://images.igdb.com/igdb/image/upload/t_720p/sc8rt3.jpg'),
(47, 11, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co2xds.jpg'),
(48, 11, 'https://images.igdb.com/igdb/image/upload/t_720p/sc98c3.jpg'),
(49, 11, 'https://images.igdb.com/igdb/image/upload/t_720p/sc98c4.jpg'),
(50, 11, 'https://images.igdb.com/igdb/image/upload/t_720p/sc98c5.jpg'),
(51, 12, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co78y5.jpg'),
(52, 12, 'https://images.igdb.com/igdb/image/upload/t_720p/sce765.jpg'),
(53, 12, 'https://images.igdb.com/igdb/image/upload/t_720p/sce767.jpg'),
(54, 12, 'https://images.igdb.com/igdb/image/upload/t_720p/sce768.jpg'),
(55, 12, 'https://images.igdb.com/igdb/image/upload/t_720p/sce76a.jpg'),
(63, 14, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co7f7k.jpg'),
(64, 14, 'https://images.igdb.com/igdb/image/upload/t_720p/scpowf.jpg'),
(65, 14, 'https://images.igdb.com/igdb/image/upload/t_720p/scpowg.jpg'),
(66, 14, 'https://images.igdb.com/igdb/image/upload/t_720p/scpowh.jpg'),
(67, 14, 'https://images.igdb.com/igdb/image/upload/t_720p/scpowi.jpg'),
(68, 15, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co29t9.jpg'),
(69, 15, 'https://images.igdb.com/igdb/image/upload/t_720p/sc8bzb.jpg'),
(70, 15, 'https://images.igdb.com/igdb/image/upload/t_720p/sc8bzd.jpg'),
(71, 15, 'https://images.igdb.com/igdb/image/upload/t_720p/sc8bzc.jpg'),
(72, 16, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co4l46.jpg'),
(73, 16, 'https://images.igdb.com/igdb/image/upload/t_720p/scg9le.jpg'),
(74, 16, 'https://images.igdb.com/igdb/image/upload/t_720p/scg9lf.jpg'),
(81, 18, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co7d6t.jpg'),
(82, 18, 'https://images.igdb.com/igdb/image/upload/t_720p/scp9fz.jpg'),
(83, 18, 'https://images.igdb.com/igdb/image/upload/t_720p/co7d6t_1.jpg'),
(84, 19, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co2q64.jpg'),
(85, 19, 'https://images.igdb.com/igdb/image/upload/t_720p/co2q64_1.jpg'),
(86, 19, 'https://images.igdb.com/igdb/image/upload/t_720p/co2q64_2.jpg'),
(96, 23, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co9ofe.jpg'),
(97, 23, 'https://images.igdb.com/igdb/image/upload/t_720p/scwand.jpg'),
(98, 23, 'https://images.igdb.com/igdb/image/upload/t_720p/scwane.jpg'),
(99, 23, 'https://images.igdb.com/igdb/image/upload/t_720p/scwanf.jpg'),
(100, 23, 'https://images.igdb.com/igdb/image/upload/t_720p/scwang.jpg'),
(101, 23, 'https://images.igdb.com/igdb/image/upload/t_720p/scwan9.jpg'),
(102, 23, 'https://images.igdb.com/igdb/image/upload/t_720p/scwana.jpg'),
(103, 24, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co7uvf.jpg'),
(104, 24, 'https://images.igdb.com/igdb/image/upload/t_720p/scqyzi.jpg'),
(105, 24, 'https://images.igdb.com/igdb/image/upload/t_720p/scqyzj.jpg'),
(106, 24, 'https://images.igdb.com/igdb/image/upload/t_720p/scqyzk.jpg'),
(107, 24, 'https://images.igdb.com/igdb/image/upload/t_720p/scqyzl.jpg'),
(108, 24, 'https://images.igdb.com/igdb/image/upload/t_720p/scqyzm.jpg'),
(109, 24, 'https://images.igdb.com/igdb/image/upload/t_720p/scqyzg.jpg'),
(110, 25, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co4280.jpg'),
(111, 25, 'https://images.igdb.com/igdb/image/upload/t_720p/xxhwa0x7wbybaprgm7zz.jpg'),
(112, 25, 'https://images.igdb.com/igdb/image/upload/t_720p/ihwqjvqlmqrdhk9gcs5k.jpg'),
(113, 25, 'https://images.igdb.com/igdb/image/upload/t_720p/qnbivlafial6arn9cf4o.jpg'),
(114, 25, 'https://images.igdb.com/igdb/image/upload/t_720p/j5ttfldsar0t3ti51hms.jpg'),
(115, 25, 'https://images.igdb.com/igdb/image/upload/t_720p/k5shtnjpkbvhroksmujd.jpg'),
(116, 26, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co9lhj.jpg'),
(117, 26, 'https://images.igdb.com/igdb/image/upload/t_720p/scvzrb.jpg'),
(118, 26, 'https://images.igdb.com/igdb/image/upload/t_720p/scvzrc.jpg'),
(119, 26, 'https://images.igdb.com/igdb/image/upload/t_720p/scvzrd.jpg'),
(120, 26, 'https://images.igdb.com/igdb/image/upload/t_720p/scvzrf.jpg'),
(121, 26, 'https://images.igdb.com/igdb/image/upload/t_720p/scvzra.jpg'),
(122, 26, 'https://images.igdb.com/igdb/image/upload/t_720p/scvzre.jpg'),
(123, 27, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co20mf.jpg'),
(124, 27, 'https://images.igdb.com/igdb/image/upload/t_720p/co20mf_1.jpg'),
(125, 27, 'https://images.igdb.com/igdb/image/upload/t_720p/co20mf_2.jpg'),
(126, 28, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co7185.jpg'),
(127, 28, 'https://images.igdb.com/igdb/image/upload/t_720p/scnsb9.jpg'),
(128, 28, 'https://images.igdb.com/igdb/image/upload/t_720p/scnsba.jpg'),
(129, 28, 'https://images.igdb.com/igdb/image/upload/t_720p/scnsbb.jpg'),
(130, 28, 'https://images.igdb.com/igdb/image/upload/t_720p/scnsbc.jpg'),
(131, 31, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co1pwh.jpg'),
(132, 31, 'https://images.igdb.com/igdb/image/upload/t_720p/sce5qo.jpg'),
(133, 31, 'https://images.igdb.com/igdb/image/upload/t_720p/sce5qp.jpg'),
(134, 31, 'https://images.igdb.com/igdb/image/upload/t_720p/sce5qq.jpg'),
(135, 31, 'https://images.igdb.com/igdb/image/upload/t_720p/sce5qr.jpg'),
(136, 31, 'https://images.igdb.com/igdb/image/upload/t_720p/sce5qs.jpg'),
(137, 31, 'https://images.igdb.com/igdb/image/upload/t_720p/sce5qt.jpg'),
(144, 33, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co3c7g.jpg'),
(145, 33, 'https://images.igdb.com/igdb/image/upload/t_720p/sc6z2w.jpg'),
(146, 33, 'https://images.igdb.com/igdb/image/upload/t_720p/sc6z2x.jpg'),
(147, 33, 'https://images.igdb.com/igdb/image/upload/t_720p/sc6z2y.jpg'),
(148, 34, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co9rrw.jpg'),
(149, 34, 'https://images.igdb.com/igdb/image/upload/t_720p/scwpo6.jpg'),
(150, 34, 'https://images.igdb.com/igdb/image/upload/t_720p/scwpo7.jpg'),
(151, 34, 'https://images.igdb.com/igdb/image/upload/t_720p/scwpo8.jpg'),
(152, 35, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co3irb.jpg'),
(153, 35, 'https://images.igdb.com/igdb/image/upload/t_720p/x0c1z0bjy9ygcbjszm1q.jpg'),
(154, 35, 'https://images.igdb.com/igdb/image/upload/t_720p/sccb3w.jpg'),
(155, 35, 'https://images.igdb.com/igdb/image/upload/t_720p/sccb3x.jpg'),
(156, 35, 'https://images.igdb.com/igdb/image/upload/t_720p/sccb3y.jpg'),
(157, 35, 'https://images.igdb.com/igdb/image/upload/t_720p/sccb3z.jpg'),
(158, 35, 'https://images.igdb.com/igdb/image/upload/t_720p/sccb40.jpg'),
(165, 37, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co26en.jpg'),
(166, 37, 'https://images.igdb.com/igdb/image/upload/t_720p/vnaz5cl6lbfihz66nzqa.jpg'),
(167, 37, 'https://images.igdb.com/igdb/image/upload/t_720p/fyztmlahv4459jcjjpmo.jpg'),
(168, 37, 'https://images.igdb.com/igdb/image/upload/t_720p/phpnsxo6l0xdqym0wspo.jpg'),
(169, 37, 'https://images.igdb.com/igdb/image/upload/t_720p/tb9uq12ohsdamd2fg1il.jpg'),
(170, 37, 'https://images.igdb.com/igdb/image/upload/t_720p/bjvivkhqxahk9tgyjrij.jpg'),
(171, 38, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co9u9o.jpg'),
(172, 38, 'https://images.igdb.com/igdb/image/upload/t_720p/sci3qo.jpg'),
(173, 38, 'https://images.igdb.com/igdb/image/upload/t_720p/sci3qp.jpg'),
(174, 38, 'https://images.igdb.com/igdb/image/upload/t_720p/sci3qq.jpg'),
(175, 38, 'https://images.igdb.com/igdb/image/upload/t_720p/sci3qr.jpg'),
(176, 38, 'https://images.igdb.com/igdb/image/upload/t_720p/sci3qs.jpg'),
(177, 38, 'https://images.igdb.com/igdb/image/upload/t_720p/sci3qt.jpg'),
(183, 44, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co7mjq.jpg'),
(184, 44, 'https://images.igdb.com/igdb/image/upload/t_720p/scfmxp.jpg'),
(185, 44, 'https://images.igdb.com/igdb/image/upload/t_720p/scfmxv.jpg'),
(186, 44, 'https://images.igdb.com/igdb/image/upload/t_720p/scfmy0.jpg'),
(187, 44, 'https://images.igdb.com/igdb/image/upload/t_720p/scfmy4.jpg'),
(188, 44, 'https://images.igdb.com/igdb/image/upload/t_720p/scfmy7.jpg'),
(189, 44, 'https://images.igdb.com/igdb/image/upload/t_720p/scfmyc.jpg'),
(199, 47, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co2yn1.jpg'),
(200, 47, 'https://images.igdb.com/igdb/image/upload/t_720p/sc9dtj.jpg'),
(201, 47, 'https://images.igdb.com/igdb/image/upload/t_720p/sc9dtk.jpg'),
(202, 47, 'https://images.igdb.com/igdb/image/upload/t_720p/sc9dtm.jpg'),
(203, 47, 'https://images.igdb.com/igdb/image/upload/t_720p/sc9dto.jpg'),
(204, 47, 'https://images.igdb.com/igdb/image/upload/t_720p/sc9dti.jpg'),
(205, 47, 'https://images.igdb.com/igdb/image/upload/t_720p/sc9dtl.jpg'),
(210, 49, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co38tl.jpg'),
(211, 49, 'https://images.igdb.com/igdb/image/upload/t_720p/sc9lxu.jpg'),
(212, 49, 'https://images.igdb.com/igdb/image/upload/t_720p/sc9lxq.jpg'),
(213, 49, 'https://images.igdb.com/igdb/image/upload/t_720p/sc9lxy.jpg'),
(214, 49, 'https://images.igdb.com/igdb/image/upload/t_720p/sc9lxs.jpg'),
(215, 49, 'https://images.igdb.com/igdb/image/upload/t_720p/sc9lxw.jpg'),
(216, 50, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co5pew.jpg'),
(217, 50, 'https://images.igdb.com/igdb/image/upload/t_720p/scb1tk.jpg'),
(218, 50, 'https://images.igdb.com/igdb/image/upload/t_720p/scb1tm.jpg'),
(219, 50, 'https://images.igdb.com/igdb/image/upload/t_720p/scb1to.jpg'),
(220, 50, 'https://images.igdb.com/igdb/image/upload/t_720p/scb1tr.jpg'),
(221, 50, 'https://images.igdb.com/igdb/image/upload/t_720p/scb1tt.jpg'),
(286, 16, 'https://images.igdb.com/igdb/image/upload/t_720p/scg9lg.jpg'),
(287, 18, 'https://images.igdb.com/igdb/image/upload/t_720p/co7d6t_2.jpg'),
(292, 29, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co4s6n.jpg'),
(293, 29, 'https://images.igdb.com/igdb/image/upload/t_720p/scgqi4.jpg'),
(294, 29, 'https://images.igdb.com/igdb/image/upload/t_720p/scgqi5.jpg'),
(298, 39, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co4s6n.jpg'),
(299, 39, 'https://images.igdb.com/igdb/image/upload/t_720p/scgqi4.jpg'),
(300, 39, 'https://images.igdb.com/igdb/image/upload/t_720p/scgqi5.jpg'),
(301, 40, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co4s6n.jpg'),
(302, 40, 'https://images.igdb.com/igdb/image/upload/t_720p/scgqi4.jpg'),
(303, 40, 'https://images.igdb.com/igdb/image/upload/t_720p/scgqi5.jpg'),
(304, 41, 'https://images.igdb.com/igdb/image/upload/t_cover_big/co4s6n.jpg'),
(305, 41, 'https://images.igdb.com/igdb/image/upload/t_720p/scgqi4.jpg'),
(306, 41, 'https://images.igdb.com/igdb/image/upload/t_720p/scgqi5.jpg'),
(1022, 76, 'https://res.cloudinary.com/dmza7ndjr/image/upload/v1749795267/listings/image/Captura%20de%20pantalla%202025-06-12%20112004.png_20250613081432.png'),
(1023, 76, 'https://res.cloudinary.com/dmza7ndjr/image/upload/v1749795268/listings/image/Captura%20de%20pantalla%202025-06-12%20133120.png_20250613081434.png'),
(1024, 76, 'https://res.cloudinary.com/dmza7ndjr/image/upload/v1749795269/listings/image/Captura%20de%20pantalla%202025-06-12%20224024.png_20250613081435.png'),
(1025, 76, 'https://res.cloudinary.com/dmza7ndjr/image/upload/v1749795270/listings/image/Captura%20de%20pantalla%202025-06-12%20224739.png_20250613081436.png'),
(1026, 76, 'https://res.cloudinary.com/dmza7ndjr/image/upload/v1749795271/listings/image/Captura%20de%20pantalla%202025-06-12%20224817.png_20250613081436.png');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `listing_tags`
--

DROP TABLE IF EXISTS `listing_tags`;
CREATE TABLE `listing_tags` (
  `listing_id` bigint(20) UNSIGNED NOT NULL,
  `tag` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `listing_tags`
--

INSERT INTO `listing_tags` (`listing_id`, `tag`) VALUES
(1, 'Action'),
(1, 'Adventure'),
(1, 'Fantasy'),
(1, 'Multiplayer'),
(1, 'Open World'),
(1, 'RPG'),
(2, 'Fantasy'),
(2, 'Medieval'),
(2, 'RPG'),
(2, 'Story Rich'),
(2, 'Strategy'),
(2, 'Tactical'),
(2, 'Turn-based'),
(5, 'Atmospheric'),
(5, 'Dark'),
(5, 'First-person'),
(5, 'Horror'),
(5, 'Mystery'),
(5, 'Psychological'),
(5, 'Survival'),
(11, 'Action'),
(11, 'FPS'),
(11, 'Military'),
(11, 'Modern'),
(11, 'Multiplayer'),
(11, 'Shooter'),
(11, 'Tactical'),
(12, 'Arcade'),
(12, 'Competitive'),
(12, 'Fast-paced'),
(12, 'Multiplayer'),
(12, 'Racing'),
(12, 'Sports'),
(14, 'Action'),
(14, 'Arcade'),
(14, 'Classic'),
(14, 'Competitive'),
(14, 'Fighting'),
(14, 'PvP'),
(14, 'Retro'),
(15, 'Cyberpunk'),
(15, 'Hacking'),
(15, 'Single-player'),
(15, 'Stealth'),
(15, 'Story Rich'),
(15, 'Strategy'),
(16, 'Action'),
(16, 'Adventure'),
(16, 'Exploration'),
(16, 'Fantasy'),
(16, 'Magic'),
(16, 'Open World'),
(16, 'RPG'),
(18, 'Atmospheric'),
(18, 'Dark'),
(18, 'Gore'),
(18, 'Horror'),
(18, 'Mystery'),
(18, 'Psychological'),
(18, 'Survival'),
(19, '2D'),
(19, 'Difficult'),
(19, 'Indie'),
(19, 'Pixel Art'),
(19, 'Platformer'),
(19, 'Retro'),
(23, 'Action'),
(23, 'Arcade'),
(23, 'Casual'),
(23, 'Colorful'),
(23, 'Fast-paced'),
(23, 'Score Attack'),
(24, 'Action'),
(24, 'Dungeon Crawler'),
(24, 'Fantasy'),
(24, 'Loot'),
(24, 'Procedural'),
(24, 'Roguelite'),
(24, 'RPG'),
(25, '2D'),
(25, 'Adventure'),
(25, 'Casual'),
(25, 'Cute'),
(25, 'Family Friendly'),
(25, 'Ghost'),
(25, 'Puzzle'),
(26, 'Action'),
(26, 'Adventure'),
(26, 'Exploration'),
(26, 'Open World'),
(26, 'Single-player'),
(26, 'Story Rich'),
(27, 'Arcade'),
(27, 'Classic'),
(27, 'Collection'),
(27, 'Competitive'),
(27, 'Fighting'),
(27, 'Gore'),
(27, 'Retro'),
(28, 'Historical'),
(28, 'Military'),
(28, 'Strategy'),
(28, 'Tactical'),
(28, 'Turn-based'),
(28, 'War'),
(29, 'Adventure'),
(29, 'Anime'),
(29, 'Multiple Endings'),
(29, 'Romance'),
(29, 'Story Rich'),
(29, 'Visual Novel'),
(31, 'Action'),
(31, 'Adventure'),
(31, 'Anime'),
(31, 'Fighting'),
(31, 'Mobile'),
(31, 'Ninja'),
(31, 'Story Rich'),
(33, 'Action'),
(33, 'DLC'),
(33, 'Military'),
(33, 'Open World'),
(33, 'Shooter'),
(33, 'Stealth'),
(33, 'Tactical'),
(34, 'Casual'),
(34, 'Colorful'),
(34, 'Educational'),
(34, 'Family Friendly'),
(34, 'Kids'),
(34, 'Robots'),
(35, 'Colorful'),
(35, 'Cute'),
(35, 'Family Friendly'),
(35, 'Platformer'),
(35, 'PlayStation'),
(35, 'Puzzle'),
(37, 'Dark Fantasy'),
(37, 'Fantasy'),
(37, 'RPG'),
(37, 'Story Rich'),
(37, 'Strategy'),
(37, 'Turn-based'),
(38, 'Adventure'),
(38, 'Atmospheric'),
(38, 'Mystery'),
(38, 'Puzzle'),
(38, 'Single-player'),
(38, 'Story Rich'),
(39, 'Casual'),
(39, 'Education'),
(39, 'Family Friendly'),
(39, 'Funny'),
(39, 'Management'),
(39, 'Simulation'),
(40, 'Action'),
(40, 'Deluxe Edition'),
(40, 'Fantasy'),
(40, 'Hack and Slash'),
(40, 'RPG'),
(40, 'Story Rich'),
(41, 'Fantasy'),
(41, 'Magic'),
(41, 'RPG'),
(41, 'Story Rich'),
(41, 'Strategy'),
(41, 'Tactical'),
(41, 'Turn-based'),
(44, '2.5D'),
(44, 'Adventure'),
(44, 'Atmospheric'),
(44, 'Nature'),
(44, 'Platformer'),
(44, 'Story Rich'),
(47, 'Action'),
(47, 'Adventure'),
(47, 'Open World'),
(47, 'Post-apocalyptic'),
(47, 'Story Rich'),
(47, 'Survival'),
(49, '3D'),
(49, 'Brain Training'),
(49, 'Casual'),
(49, 'Logic'),
(49, 'Minimalist'),
(49, 'Puzzle'),
(50, 'Dark Humor'),
(50, 'DLC'),
(50, 'Management'),
(50, 'Medieval'),
(50, 'RPG'),
(50, 'Simulation'),
(50, 'Story Rich'),
(76, 'jyjk'),
(76, 'ryb');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `player`
--

DROP TABLE IF EXISTS `player`;
CREATE TABLE `player` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `user_id` bigint(20) UNSIGNED DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `destroyed_blocks` int(11) DEFAULT NULL,
  `date_game` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `player`
--

INSERT INTO `player` (`id`, `user_name`, `user_id`, `score`, `destroyed_blocks`, `date_game`) VALUES
(1, 'alex', 1, 15000, 150, '2025-06-09 14:30:00'),
(4, 'jordan', 4, 13750, 137, '2025-06-09 16:45:00'),
(5, 'taylor', 5, 16250, 162, '2025-06-09 17:30:00'),
(6, 'mia', 6, 14500, 145, '2025-06-09 18:15:00'),
(7, 'ethan', 7, 17500, 175, '2025-06-09 19:00:00'),
(8, 'grace', 8, 11000, 110, '2025-06-09 19:45:00'),
(9, 'olivia', 9, 19500, 195, '2025-06-09 20:30:00'),
(10, 'alex', 1, 20000, 200, '2025-06-09 21:15:00'),
(11, 'ale', 11, 0, 0, '2025-06-14 10:29:03'),
(12, 'ale', 11, 1000, 10, '2025-06-14 10:29:03'),
(13, 'ale', 11, 2000, 20, '2025-06-14 10:29:03'),
(14, 'ale', 11, 3000, 30, '2025-06-14 10:29:03'),
(15, 'ale', 11, 4000, 40, '2025-06-14 10:29:03'),
(16, 'ale', 11, 5000, 50, '2025-06-14 10:29:03'),
(17, 'ale', 11, 6000, 60, '2025-06-14 10:29:03'),
(18, 'ale', 11, 7000, 70, '2025-06-14 10:29:03'),
(19, 'ale', 11, 8000, 80, '2025-06-14 10:29:03'),
(20, 'ale', 11, 9000, 90, '2025-06-14 10:29:03'),
(21, 'ale', 11, 10000, 100, '2025-06-14 10:29:03'),
(22, 'ale', 11, 11000, 110, '2025-06-14 10:29:03'),
(23, 'ale', 11, 12000, 120, '2025-06-14 10:29:03'),
(24, 'ale', 11, 13000, 130, '2025-06-14 10:29:03'),
(25, 'ale', 11, 14000, 140, '2025-06-14 10:29:03'),
(26, 'ale', 11, 15000, 150, '2025-06-14 10:29:03'),
(27, 'ale', 11, 16000, 160, '2025-06-14 10:29:03'),
(28, 'ale', 11, 17000, 170, '2025-06-14 10:29:03'),
(29, 'ale', 11, 18000, 180, '2025-06-14 10:29:03'),
(30, 'ale', 11, 19000, 190, '2025-06-14 10:29:03'),
(31, 'ale', 11, 20000, 200, '2025-06-14 10:29:03'),
(32, 'ale', 11, 21000, 210, '2025-06-14 10:29:03');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ratings`
--

DROP TABLE IF EXISTS `ratings`;
CREATE TABLE `ratings` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `rating` double DEFAULT NULL,
  `user_id` bigint(20) UNSIGNED DEFAULT NULL,
  `listing_id` bigint(20) UNSIGNED DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ratings`
--

INSERT INTO `ratings` (`id`, `rating`, `user_id`, `listing_id`) VALUES
(1, 4.5, 1, 1),
(4, 4.2, 4, 2),
(5, 3.5, 5, 2),
(6, 4.8, 6, 2),
(13, 4.9, 4, 5),
(14, 4.3, 5, 5),
(15, 4.6, 6, 5),
(22, 4.8, 4, 2),
(25, 4.3, 7, 5),
(39, 4.5, 15, 76);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `profile_pic` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL,
  `created_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO `users` (`id`, `user_name`, `email`, `password`, `profile_pic`, `role`, `created_at`) VALUES
(1, 'alex', 'alex@example.com', 'secret', '/img/default.png', 'USER', '2025-06-09 00:17:36'),
(4, 'jordan', 'jordan@example.com', 'secret', '/img/default.png', 'USER', '2025-06-09 00:17:36'),
(5, 'taylor', 'taylor@example.com', 'secret', '/img/default.png', 'USER', '2025-06-09 00:17:36'),
(6, 'mia', 'mia@example.com', 'secret', '/img/default.png', 'USER', '2025-06-09 00:17:36'),
(7, 'ethan', 'ethan@example.com', 'secret', '/img/default.png', 'USER', '2025-06-09 00:17:36'),
(8, 'grace', 'grace@example.com', 'secret', '/img/default.png', 'USER', '2025-06-09 00:17:36'),
(9, 'olivia', 'olivia@example.com', 'secret', '/img/default.png', 'USER', '2025-06-09 00:17:36'),
(10, 'henry', 'henry@example.com', 'secret', '/img/default.png', 'USER', '2025-06-09 00:17:36'),
(11, 'Alex Accounts', 'aarcos664@gmail.com', '$2a$10$uuLRH9q9uNXHlqG1VXoMQOEz6tXV1TfJ/eu9p6ZTIYHwccTlpsTwq', 'https://res.cloudinary.com/dmza7ndjr/image/upload/v1749764215/listings/image/Captura%20de%20pantalla%202025-03-11%20161232.png_20250612233659.png', 'ADMIN', '2025-06-13 03:19:00'),
(12, 'Alex Accounts 2', 'death6263@gmail.com', '$2a$10$2SM.ds2jwL2SCXZKH1sL4eKg4IiGv5D9AWbQkz325e3CFnT2RGaIq', 'https://res.cloudinary.com/dmza7ndjr/image/upload/v1749757400/listings/image/Captura%20de%20pantalla%202025-03-11%20161232.png_20250612214324.png', 'USER', '2025-06-12 21:35:54'),
(15, 'Alexitoo', 'kirbiby@gmail.com', '$2a$10$936tVFY6nfjWPdSrvj5n9eHEOmI/p3wwo9gX1.6IRj6dU1JuViu1S', '/img/default.png', 'USER', '2025-06-13 07:00:01');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_fav_listings`
--

DROP TABLE IF EXISTS `user_fav_listings`;
CREATE TABLE `user_fav_listings` (
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `fav_listings` bigint(20) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `user_fav_listings`
--

INSERT INTO `user_fav_listings` (`user_id`, `fav_listings`) VALUES
(11, 76),
(15, 76);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `listing_id` (`listing_id`);

--
-- Indices de la tabla `listings`
--
ALTER TABLE `listings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indices de la tabla `listing_images`
--
ALTER TABLE `listing_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `listing_id` (`listing_id`);

--
-- Indices de la tabla `listing_tags`
--
ALTER TABLE `listing_tags`
  ADD PRIMARY KEY (`listing_id`,`tag`);

--
-- Indices de la tabla `player`
--
ALTER TABLE `player`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indices de la tabla `ratings`
--
ALTER TABLE `ratings`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `listing_id` (`listing_id`);

--
-- Indices de la tabla `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `user_name` (`user_name`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indices de la tabla `user_fav_listings`
--
ALTER TABLE `user_fav_listings`
  ADD PRIMARY KEY (`user_id`,`fav_listings`),
  ADD KEY `fav_listings` (`fav_listings`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `comments`
--
ALTER TABLE `comments`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=392;

--
-- AUTO_INCREMENT de la tabla `listings`
--
ALTER TABLE `listings`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT de la tabla `listing_images`
--
ALTER TABLE `listing_images`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1027;

--
-- AUTO_INCREMENT de la tabla `player`
--
ALTER TABLE `player`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `ratings`
--
ALTER TABLE `ratings`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=40;

--
-- AUTO_INCREMENT de la tabla `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `comments`
--
ALTER TABLE `comments`
  ADD CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`listing_id`) REFERENCES `listings` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `listings`
--
ALTER TABLE `listings`
  ADD CONSTRAINT `listings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `listing_images`
--
ALTER TABLE `listing_images`
  ADD CONSTRAINT `listing_images_ibfk_1` FOREIGN KEY (`listing_id`) REFERENCES `listings` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `listing_tags`
--
ALTER TABLE `listing_tags`
  ADD CONSTRAINT `listing_tags_ibfk_1` FOREIGN KEY (`listing_id`) REFERENCES `listings` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `player`
--
ALTER TABLE `player`
  ADD CONSTRAINT `player_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `ratings`
--
ALTER TABLE `ratings`
  ADD CONSTRAINT `ratings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `ratings_ibfk_2` FOREIGN KEY (`listing_id`) REFERENCES `listings` (`id`) ON DELETE CASCADE;

--
-- Filtros para la tabla `user_fav_listings`
--
ALTER TABLE `user_fav_listings`
  ADD CONSTRAINT `user_fav_listings_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `user_fav_listings_ibfk_2` FOREIGN KEY (`fav_listings`) REFERENCES `listings` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;