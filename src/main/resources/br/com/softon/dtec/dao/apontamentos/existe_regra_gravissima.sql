select 1 from tb_regra r
where exists (
	select 1 from tb_detlh_apontamento
	where  
		dt_apontamento = ? and
		cd_tp_identf_clie = ? and
		cd_doc_identf_clie = ? and
		cd_lote_apontamento = ? and
		cd_regra = r.cd_regra and
		cd_versao_sistema = r.cd_versao_sistema
) and
	vl_ponto = 4