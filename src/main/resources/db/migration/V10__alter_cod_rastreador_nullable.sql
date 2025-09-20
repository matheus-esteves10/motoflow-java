-- Alterando a coluna nr_cod_rastreador para permitir valores nulos
ALTER TABLE t_mtf_moto ALTER COLUMN nr_cod_rastreador DROP NOT NULL;

