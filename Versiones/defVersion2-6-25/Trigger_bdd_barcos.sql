USE hundir_la_flota;

DELIMITER //
CREATE TRIGGER trg_banear_usuario
AFTER UPDATE ON usuarios
FOR EACH ROW
BEGIN
  -- Solo si antes no estaba baneado y ahora sí lo está
  IF OLD.baneado = FALSE AND NEW.baneado = TRUE THEN
    INSERT INTO Baneados (nombre_usuario, motivo, fecha_baneo, fecha_fin_baneo)
    VALUES (NEW.nombre_usuario, 'Baneo manual desde admin', curdate(), DATE_ADD(curdate(), INTERVAL 7 DAY));
  END IF;
END //
DELIMITER ;

 -- drop trigger trg_banear_usuario;
