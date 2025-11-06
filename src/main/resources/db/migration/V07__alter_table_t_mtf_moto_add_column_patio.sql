ALTER TABLE t_mtf_moto ADD COLUMN cd_id_patio BIGINT;);

ALTER TABLE t_mtf_moto ADD CONSTRAINT fk_moto_patio
FOREIGN KEY (cd_id_patio) REFERENCES t_mtf_patio(cd_id_patio);
