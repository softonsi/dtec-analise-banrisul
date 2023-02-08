update tb_lote_proces
  set dt_fim_proces = getdate(), cd_status_proces = 90
  where cd_lote = ? and
  (select count(cd_sublote) from tb_sub_lote where cd_lote = ? and cd_processamento != 'X' or cd_lote = ? and cd_processamento is null) = 0