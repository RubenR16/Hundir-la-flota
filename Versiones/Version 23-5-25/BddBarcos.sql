-- drop database hundir_la_flota;

-- Crear base de datos
CREATE DATABASE hundir_la_flota;
USE hundir_la_flota;

-- Crear tabla de usuarios (con nombre_usuario como clave primaria)
CREATE TABLE usuarios (
  nombre_usuario VARCHAR(20) PRIMARY KEY,
  password_usuario VARCHAR(100) NOT NULL,
  partidas_jugadas INT DEFAULT 0,
  barcos_hundidos INT DEFAULT 0,
  puntos_totales INT DEFAULT 0,
  casillas_agua_golpeadas INT DEFAULT 0
);

-- Crear tabla de ranking
CREATE TABLE ranking (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  nombre_usuario VARCHAR(20) NOT NULL,
  puntuacion INT DEFAULT 0,
  UNIQUE KEY unique_nombre_usuario_ranking (nombre_usuario),
  FOREIGN KEY (nombre_usuario) REFERENCES usuarios(nombre_usuario)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

-- Crear tabla de partidas
CREATE TABLE partidas (
  id_partida BIGINT PRIMARY KEY AUTO_INCREMENT,
  usuario_1 VARCHAR(20) NOT NULL,
  usuario_2 VARCHAR(20) NOT NULL,
  fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_fin TIMESTAMP NULL,
  ganador VARCHAR(20) NULL,
  FOREIGN KEY (usuario_1) REFERENCES usuarios(nombre_usuario)
    ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (usuario_2) REFERENCES usuarios(nombre_usuario)
    ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (ganador) REFERENCES usuarios(nombre_usuario)
    ON UPDATE CASCADE ON DELETE SET NULL
);

-- Consultas de prueba
SELECT * FROM usuarios;
SELECT * FROM ranking;




DELIMITER //

CREATE TRIGGER trg_detectar_cambios_estadisticas
AFTER UPDATE ON usuarios
FOR EACH ROW
BEGIN
  IF OLD.partidas_jugadas <> NEW.partidas_jugadas OR
     OLD.barcos_hundidos <> NEW.barcos_hundidos OR
     OLD.puntos_totales <> NEW.puntos_totales OR
     OLD.casillas_agua_golpeadas <> NEW.casillas_agua_golpeadas THEN

    UPDATE ranking
    SET puntuacion = (select puntos_totales as PT from usuarios where nombre_usuario = NEW.nombre_usuario)
    WHERE nombre_usuario = NEW.nombre_usuario;

  END IF;
END //

DELIMITER ;




DELIMITER //

CREATE TRIGGER trg_insertar_en_ranking
AFTER INSERT ON usuarios
FOR EACH ROW
BEGIN
  INSERT INTO ranking (nombre_usuario, puntuacion)
  VALUES (NEW.nombre_usuario, 0);
END //

DELIMITER ;


-- drop trigger trg_detectar_cambios_estadisticas;

UPDATE usuarios
SET
  partidas_jugadas = 15,
  barcos_hundidos = 8,
  puntos_totales = 1200,
  casillas_agua_golpeadas = 20
WHERE nombre_usuario = 'a';


SHOW TRIGGERS LIKE 'usuarios';
