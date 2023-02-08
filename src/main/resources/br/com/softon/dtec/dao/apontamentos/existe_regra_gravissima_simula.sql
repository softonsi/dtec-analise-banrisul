select 1 from tb_regra_simula r
where exists (
	select 1 from tb_detlh_apontamento_simula
	where  
		cd_identf_simula = ? and
		cd_tp_identf_clie = ? and
		cd_doc_identf_clie = ? and
		cd_regra = r.cd_regra and
		cd_versao_sistema = r.cd_versao_sistema
) and
	vl_ponto = 4
	and cd_identf_simula = ?