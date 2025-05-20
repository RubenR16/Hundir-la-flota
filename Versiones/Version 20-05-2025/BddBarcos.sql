create database hundir_la_flota;
-- drop database hundir_la_flota;
-- Crear tabla usuarios
CREATE TABLE usuarios (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre_usuario varchar(20) UNIQUE NOT NULL,
  password_usuario varchar(100) NOT NULL,
  partidas_jugadas INT DEFAULT 0,
  barcos_hundidos INT DEFAULT 0,
  puntos_totales INT DEFAULT 0,
  casillas_agua_golpeadas INT DEFAULT 0
);

-- Crear tabla ranking
CREATE TABLE ranking (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  usuario_id BIGINT NOT NULL,
  puntuacion INT DEFAULT 0,
  UNIQUE KEY unique_usuario_id_ranking (usuario_id),
  FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Crear tabla partidas
CREATE TABLE partidas (
  id_partida BIGINT PRIMARY KEY AUTO_INCREMENT,
  id_usuario_1 BIGINT NOT NULL,
  id_usuario_2 BIGINT NOT NULL,
  fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_fin TIMESTAMP NULL,
  ganador BIGINT NULL,
  UNIQUE KEY unique_id_usuario_2 (id_usuario_2),
  FOREIGN KEY (id_usuario_1) REFERENCES usuarios(id),
  FOREIGN KEY (id_usuario_2) REFERENCES usuarios(id),
  FOREIGN KEY (ganador) REFERENCES usuarios(id)
    ON UPDATE CASCADE
    ON DELETE SET NULL
);

select * from usuarios;