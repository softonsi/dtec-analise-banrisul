select cd_lote, cd_sublote from tb_sub_lote
 where
    cd_processamento = 'P' and
    nm_maquina = '%s'
 order by cd_lote asc, cd_sublote asc
