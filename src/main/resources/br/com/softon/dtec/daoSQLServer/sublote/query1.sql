select cd_lote, cd_sublote from tb_sub_lote
where
    cd_processamento is null and
    dt_fim_carga_analise is not null
order by cd_lote asc, cd_sublote asc
