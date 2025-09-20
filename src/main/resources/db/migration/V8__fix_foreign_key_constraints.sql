-- Remove constraint antiga se existir
ALTER TABLE t_mtf_moto DROP CONSTRAINT IF EXISTS fkey78h2dny811hvp0yyyjcmle6;

-- Remove qualquer referência à tabela antiga se existir
DROP TABLE IF EXISTS t_mtf_posicao_patio CASCADE;

-- Recria a constraint correta para referenciar t_mtf_setor_patio
ALTER TABLE t_mtf_moto DROP CONSTRAINT IF EXISTS fk_moto_setor;
ALTER TABLE t_mtf_moto ADD CONSTRAINT fk_moto_setor
    FOREIGN KEY (cd_id_setor) REFERENCES t_mtf_setor_patio (cd_id_setor);
