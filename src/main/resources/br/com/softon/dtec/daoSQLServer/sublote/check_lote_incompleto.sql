select cd_lote from tb_lote_proces l
  where cd_status_proces in (70,80) and
  (select count(cd_sublote) from tb_sub_lote where cd_lote = l.cd_lote and 
  cd_processamento != 'X' or cd_lote = l.cd_lote and cd_processamento is null) = 0
  order by cd_lote