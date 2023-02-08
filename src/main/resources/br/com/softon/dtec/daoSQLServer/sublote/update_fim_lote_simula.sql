update tb_controle_simula 
	set 
		dt_fim_anlse = ?, 
		qt_clie_proces = (select count(distinct cast(cd_doc_identf_clie as varchar)+cast(cd_tp_identf_clie as varchar)) from tb_trans_anlse where cd_lote = ?), 
		qt_clie_susp = ?
	where CD_IDENTF_SIMULA = ?