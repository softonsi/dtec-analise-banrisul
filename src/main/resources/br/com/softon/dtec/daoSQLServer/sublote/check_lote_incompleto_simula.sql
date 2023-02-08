select cd_lote from tb_controle_simula l
  where dt_inicio_anlse is not null and dt_fim_anlse is null and
  (select count(cd_sublote) from tb_sub_lote_simula where (cd_lote = l.cd_lote and cd_identf_simula = l.cd_identf_simula and
  cd_processamento != 'X') or (cd_lote = l.cd_lote and cd_identf_simula = l.cd_identf_simula and cd_processamento is null)) = 0
  order by cd_lote