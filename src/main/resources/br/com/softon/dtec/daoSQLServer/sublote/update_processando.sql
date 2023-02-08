UPDATE tb_sub_lote
	SET 
		cd_processamento = 'P',
        qt_tentativa =
	        case
	            when qt_tentativa is null
	            then 1
	            else qt_tentativa + 1
	        end,
        dt_inicio_analise = getdate(),
        nm_maquina = '%s'
WHERE 
	cd_lote = ? and 
    cd_sublote = ? and
	cd_processamento IS NULL and 
	dt_fim_carga_analise is not null
