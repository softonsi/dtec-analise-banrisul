/* Quantidade de contas com o mesmo endereço (exato)*/

select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA = 'F'
  AND CD_TP_OPER IN (61,62)
  AND CD_LOTE = 2014091801  
  AND EXISTS
     (SELECT 1 FROM TB_CAD_CLIE 
	   WHERE T.CD_DOC_IDENTF_CLIE <> CD_DOC_IDENTF_CLIE
		 AND T.NM_ENDER_RESID = NM_ENDER_RESID
		 AND T.NM_CID_RESID   = NM_CID_RESID
		 AND T.SG_UF_RESID    = SG_UF_RESID
		 AND T.CD_PAIS_RESID  = CD_PAIS_RESID
		 HAVING COUNT(*) >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QMesmoEndereco_PF' and cd_regra = 10 and cd_versao_sistema = 3)
	  )
UNION
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA = 'J'
  AND CD_TP_OPER IN (61,62)
  AND CD_LOTE = 2014091801  
  AND EXISTS
     (SELECT 1 FROM TB_CAD_CLIE 
	   WHERE T.CD_DOC_IDENTF_CLIE <> CD_DOC_IDENTF_CLIE
		 AND T.NM_ENDER_RESID = NM_ENDER_RESID
		 AND T.NM_CID_RESID   = NM_CID_RESID
		 AND T.SG_UF_RESID    = SG_UF_RESID
		 AND T.CD_PAIS_RESID  = CD_PAIS_RESID
		 HAVING COUNT(*) >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QMesmoEndereco_PJ' and cd_regra = 10 and cd_versao_sistema = 3)
	  )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 10  
		
 


