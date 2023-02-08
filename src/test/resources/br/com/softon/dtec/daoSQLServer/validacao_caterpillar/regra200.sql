select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND CD_TP_OPER = 39
  AND  (
    select COUNT(1) from TB_TRANS_ANLSE
    where CD_LOTE = 2014091801 
       AND CD_TP_OPER = 39
       AND EXISTS  
	     (select count(1) from tb_trans_anlse 
	      where cd_transacao <> t.cd_transacao
		    and cd_contrato = t.cd_contrato
		    and nu_parcela = t.NU_PARCELA		
		    and nm_sist_orig = t.nm_sist_orig
		    and dt_trans < t.dt_trans
		    and cd_tp_oper = 6
		    and vl_oper is not null
		    and vl_pcela_contrato is not null
		    and vl_oper > vl_pcela_contrato
		    and dt_trans >= dateadd(day, -(SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA  = 200 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_QtdeDiasRecuo'), t.dt_trans)
		  )  		 
	) > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA  = 200 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_QtdeEstorno')
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 200 ;