ALTER TABLE t_mtf_operador
    ADD COLUMN role VARCHAR(50) NOT NULL DEFAULT 'OPERADOR';

-- Inserir um novo operador admin
INSERT INTO t_mtf_operador (nm_operador, ds_senha, cd_id_patio, role)
VALUES (
           'Administrador Geral',
           '$2a$10$Wz5qGQ8uBblP9RC0rT7bROk6jzWq.NUlrbJG7jAzcWkKX8u3NmpoK', --admin123 com bcrypt
           1,
           'ADMIN'
       );