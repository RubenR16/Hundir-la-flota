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