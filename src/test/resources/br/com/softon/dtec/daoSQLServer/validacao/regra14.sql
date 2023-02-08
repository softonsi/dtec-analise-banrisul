select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_NATUR_OPER = 1
	and CD_FORMA_OPER = 7
	and CD_TP_OPER = 4
	and t.CD_LOTE = 2014091801
	and exists (
	select 1 from tb_perfil_informacao p
	where p.cd_identf_perfil     = 2
	  and p.cd_variavel_primeira = t.cd_doc_identf_clie
	  and p.cd_variavel_segunda  = t.cd_tp_identf_clie
	  and p.cd_variavel_terceira = 4
	  and p.cd_variavel_quarta   = 7
	  and exists (
		select 1 from tb_perfil_mes_calendario pm
		where pm.cd_identf_perfil     = 9
		  and pm.cd_variavel_primeira = t.cd_doc_identf_clie
		  and pm.cd_variavel_segunda  = t.cd_tp_identf_clie
		  and pm.cd_variavel_terceira = 4
		  and pm.cd_variavel_quarta   = 7
		  and pm.qt_total < (p.qt_total * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 14 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percQtdDepEspecie') ) 
	  	)
	  and exists (
		select 1 from tb_perfil_informacao p3
		where p3.cd_identf_perfil     = 3
		  and p3.cd_variavel_primeira = t.cd_doc_identf_clie
		  and p3.cd_variavel_segunda  = t.cd_tp_identf_clie
		  and p.vl_total > (p3.vl_total * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 14 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percDepTransf_inf') )
		  and p.vl_total < (p3.vl_total * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 14 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percDepTransf_sup') )
		  )
	)
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 14