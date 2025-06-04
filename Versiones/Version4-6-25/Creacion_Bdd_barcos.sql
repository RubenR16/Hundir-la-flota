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
  casillas_agua_golpeadas INT DEFAULT 0,
  baneado boolean default false
);

-- Crear tabla de Baneados
create table Baneados (

	nombre_usuario VARCHAR(20),
    motivo varchar(255),
    fecha_baneo DATETIME DEFAULT CURRENT_TIMESTAMP,
    fecha_fin_baneo DATETIME,
    activo BOOLEAN DEFAULT TRUE,
    primary key(nombre_usuario,fecha_baneo),
    CONSTRAINT ban_nom_fk FOREIGN KEY (nombre_usuario) REFERENCES usuarios(nombre_usuario) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Crear tabla de partidas
CREATE TABLE partidas (
  id_partida BIGINT PRIMARY KEY AUTO_INCREMENT,
  usuario_1 VARCHAR(20) NOT NULL,
  usuario_2 VARCHAR(20) NOT NULL,
  fecha_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  fecha_fin TIMESTAMP NULL,
  ganador VARCHAR(20) NULL,
  
  CONSTRAINT par_us1_fk FOREIGN KEY (usuario_1) REFERENCES usuarios(nombre_usuario) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT par_us2_fk FOREIGN KEY (usuario_2) REFERENCES usuarios(nombre_usuario) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT par_gan_fk FOREIGN KEY (ganador) REFERENCES usuarios(nombre_usuario) ON UPDATE CASCADE ON DELETE SET NULL
);

-- Consultas de prueba
/*SELECT * FROM usuarios;
select * from partidas;
SELECT * FROM Baneados;*/

