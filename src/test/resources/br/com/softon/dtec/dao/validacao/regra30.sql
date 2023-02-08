select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE
WHERE CD_TP_PESSOA = 'J'
  AND VL_OPER IS NOT NULL
  AND VL_RENDA_FAT IS NOT NULL
  AND VL_PATRIM IS NOT NULL
  AND (
  	VL_OPER > ( VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 30 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percFat') ) OR
  	VL_OPER > ( VL_PATRIM * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 30 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percPatrim') ) 
  )
  AND CD_LOTE = 2014091801
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'J'
  AND CD_LOTE 		= 2014091801
  AND exists (
  	select 1 from tb_med_ramo_ativid where cd_ramo_ativid = t.cd_ramo_ativid and cd_tp_identf_clie = t.cd_tp_identf_clie and t.vl_oper > (vl_med_credito * 
  		(SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 30 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRamo_Ativid')  )
  )  
); 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 30