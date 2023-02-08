update tb_controle_simula 
	set 
		dt_fim_anlse = ?, 
		qt_clie_proces = (select count(distinct cd_doc_identf_clie || cd_tp_identf_clie) from tb_trans where cd_lote = ? or cd_mes_ano = ?), 
		qt_clie_susp = (select count(distinct cd_doc_identf_clie || cd_tp_identf_clie) from tb_apontamento_simula where cd_lote = ? and CD_IDENTF_SIMULA = ? and fl_susp_ld = 1)
	where CD_IDENTF_SIMULA = ?