select cd_lote, cd_sublote from tb_sub_lote
where
    cd_processamento = 'X'
order by cd_lote desc, cd_sublote desc
