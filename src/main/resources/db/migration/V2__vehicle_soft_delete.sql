-- Borrado lógico: valet_requests referencia vehicles(id), así que un vehículo con
-- servicios asociados no se puede borrar físicamente sin perder el historial.
ALTER TABLE vehicles ADD COLUMN deleted BOOLEAN NOT NULL DEFAULT FALSE;
