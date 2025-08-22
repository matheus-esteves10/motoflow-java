-- Add the new column for status
ALTER TABLE t_mtf_moto ADD COLUMN nm_status VARCHAR(20) NOT NULL DEFAULT 'DISPONIVEL';

-- Migrate data from fl_alugada to nm_status
UPDATE t_mtf_moto
SET nm_status = CASE
                    WHEN fl_alugada = 'S' THEN 'ALUGADA'
                    WHEN fl_alugada = 'N' THEN 'DISPONIVEL'
                    ELSE 'MANUTENCAO'
    END;

-- Drop the old column
ALTER TABLE t_mtf_moto DROP COLUMN fl_alugada;