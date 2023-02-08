select count(*) as total from
(
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao from tb_trans_anlse t
where 
	    cd_Tp_Oper 		= 20
	and CD_TP_PESSOA    = 'F'
	and CD_LOTE 		= 2014091801
	and CD_CONTRATO 	is not null
	and DT_CAD_CONTRATO is not null
	and (
		select count(1) + 1 from tb_contrato
		where CD_CONTRATO 	is not null
		and DT_CAD_CONTRATO is not null
		and CD_CONTRATO <> t.CD_CONTRATO
		and DT_CAD_CONTRATO >= DATEADD(day, (SELECT VL_PARAM * (-1) FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 43 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_tpCurtoPF'), t.DT_CAD_CONTRATO)
		and DT_CAD_CONTRATO <= t.DT_CAD_CONTRATO
		and t.CD_DOC_IDENTF_CLIE = CD_DOC_IDENTF_CLIE
		and t.CD_TP_IDENTF_CLIE  = CD_TP_IDENTF_CLIE
	) >= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 43 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_QtdeContratoPF') 
UNION
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao from tb_trans_anlse t
where 
	    cd_Tp_Oper 		= 20
	and CD_TP_PESSOA    = 'J'
	and CD_LOTE 		= 2014091801
	and CD_CONTRATO 	is not null
	and DT_CAD_CONTRATO is not null
	and (
		select count(1) + 1 from tb_contrato
		where CD_CONTRATO 	is not null
		and DT_CAD_CONTRATO 	is not null
		and CD_CONTRATO <> t.CD_CONTRATO
		and DT_CAD_CONTRATO >= DATEADD(day, (SELECT VL_PARAM * (-1) FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 43 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_tpCurtoPJ'), t.DT_CAD_CONTRATO)
		and DT_CAD_CONTRATO <= t.DT_CAD_CONTRATO
		and t.CD_DOC_IDENTF_CLIE = CD_DOC_IDENTF_CLIE
		and t.CD_TP_IDENTF_CLIE  = CD_TP_IDENTF_CLIE
	) >= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 43 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_QtdeContratoPJ') 
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 43      