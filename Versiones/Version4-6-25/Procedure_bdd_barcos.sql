-- Procedimiento para desbanear los usuarios cuando llega a la fecha de desbaneo
DELIMITER //

CREATE PROCEDURE levantar_baneos_expirados()
BEGIN
  -- Desactivar todos los baneos caducados
  UPDATE Baneados
  SET activo = FALSE
  WHERE activo = TRUE AND fecha_fin_baneo <= NOW();

  -- Actualizar usuarios.baneado = FALSE solo si ya no tienen baneos activos
  UPDATE usuarios
  SET baneado = FALSE
  WHERE nombre_usuario IN (
    SELECT nombre_usuario
    FROM Baneados
    GROUP BY nombre_usuario
    HAVING SUM(activo) = 0
  );
END //

DELIMITER ;

-- drop PROCEDURE levantar_baneos_expirados;


-- Procedimiento para banear usuarios
DELIMITER //

CREATE PROCEDURE Banear_Usuario(usuario VARCHAR(20))
BEGIN
  UPDATE usuarios
  SET baneado = 1
  WHERE nombre_usuario = usuario;

END //

DELIMITER ;

-- drop PROCEDURE Banear_Usuario;
-- call Banear_Usuario("BANEAME");
