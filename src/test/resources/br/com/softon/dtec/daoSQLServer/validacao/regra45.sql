select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND CD_TP_PESSOA = 'J'
  AND NM_ENDER_RESID is not NULL 
  AND (
  	select count(1) from tb_cad_clie 
  	where CD_TP_PESSOA = 'J'
	  AND CD_DOC_IDENTF_CLIE <> t.CD_DOC_IDENTF_CLIE
	  AND NM_ENDER_RESID is not NULL 
	  AND NM_ENDER_RESID = t.NM_ENDER_RESID   
	)  >= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 45 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_QtdeClie')  
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 45