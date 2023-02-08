select 1 from tb_controle_simula
  where cd_identf_simula = ? and cd_lote = ? and
  (select count(cd_sublote) from tb_sub_lote_simula 
  	where 
  		cd_lote = ? and 
  		cd_processamento != 'X' or cd_lote = ? and 
  		cd_processamento is null and
  		cd_identf_simula = ?
  		) = 0