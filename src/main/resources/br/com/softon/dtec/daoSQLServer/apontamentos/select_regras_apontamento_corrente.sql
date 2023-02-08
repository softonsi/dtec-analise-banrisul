select distinct cd_regra from tb_detlh_apontamento
where  
	dt_apontamento = ? and
	cd_tp_identf_clie = ? and
	cd_doc_identf_clie = ?