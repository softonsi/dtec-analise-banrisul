select count(*) as total from
(
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao from tb_trans_anlse t
where CD_LOTE 		= 2014091801
	and cd_Forma_Oper 	in (4,8) 
	and cd_ag_oper 	is not null
	and vl_oper 	is not null
	and vl_oper      	> (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 17 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRenda')
	and exists (
		select 1 from tb_agencia where cd_ag = t.cd_ag_oper and fl_front = 1
	)
	and exists (
		select 1 from tb_natur_declr_oper where cd_natur_declr_oper = t.cd_natur_declr_oper and vl_medio_natur_oper is not null
		and t.vl_oper >= (vl_medio_natur_oper * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 17 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRenda'))
	)
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 21