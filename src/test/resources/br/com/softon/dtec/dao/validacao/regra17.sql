select count(*) as total from
(
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao from tb_trans_anlse t
where 
	cd_Tp_pessoa 		= 'F'
	and CD_LOTE 		= 2014091801
	and cd_Forma_Oper 	= 7 
	and cd_ag_oper 	is not null
	and vl_oper 	is not null
	and vl_renda_fat 	is not null
	and vl_oper      	>= (vl_renda_fat * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 17 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRenda')  ) 
	and exists (
		select 1 from tb_agencia where cd_ag = t.cd_ag_oper and fl_front = 1
	)
union
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao from tb_trans_anlse t
where 
	cd_Tp_pessoa 		= 'J'
	and CD_LOTE 		= 2014091801
	and cd_Forma_Oper 	= 7 
	and cd_ag_oper 	is not null
	and vl_oper 	is not null
	and vl_renda_fat 	is not null
	and vl_oper      	>= (vl_renda_fat * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 17 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percFat')  ) 
	and exists (
		select 1 from tb_agencia where cd_ag = t.cd_ag_oper and fl_front = 1
	)
); 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 17
