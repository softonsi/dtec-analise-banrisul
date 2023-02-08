select 1 from tb_lote_proces
  where cd_lote = ? and
  (select count(cd_sublote) from tb_sub_lote where cd_lote = ? and cd_processamento != 'X' or cd_lote = ? and cd_processamento is null) = 0