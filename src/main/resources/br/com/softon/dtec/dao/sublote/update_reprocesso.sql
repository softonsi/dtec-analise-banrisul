update tb_sub_lote set 
        qt_tentativa = 
        case 
            when qt_tentativa is null 
            then 1 
            else qt_tentativa + 1 
        end, 
        dt_inicio_analise = sysdate
 where cd_lote = ? 
    and cd_sublote = ? 
    and cd_processamento = 'P' 
    and nm_maquina = '%s'