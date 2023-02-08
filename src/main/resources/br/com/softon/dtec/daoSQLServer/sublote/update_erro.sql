update tb_sub_lote
   SET 
   		cd_processamento = null,
   		nm_maquina = null,
   		dt_inicio_analise = null,
   		dt_fim_analise = null
   WHERE cd_lote = ? and cd_sublote = ?
