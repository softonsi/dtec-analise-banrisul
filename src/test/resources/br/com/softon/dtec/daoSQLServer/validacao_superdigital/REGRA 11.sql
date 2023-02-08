/* Quantidade de contas com o mesmo CEP (excluir FOPA)*/

select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_LOTE = 2014091801  
  AND CD_TP_PESSOA = 'F'
  AND CD_TP_OPER IN (61,62)
  AND CD_TP_CTA <> 9
  AND EXISTS  
      (SELECT 1 FROM TB_CAD_CLIE 
       WHERE T.CD_DOC_IDENTF_CLIE <> CD_DOC_IDENTF_CLIE
         AND T.CD_CEP_RESID = CD_CEP_RESID
         HAVING COUNT(*) >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QMesmoCEP_PF' and cd_regra = 11 and cd_versao_sistema = 3)
        ) 
UNION
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_LOTE = 2014091801  
  AND CD_TP_PESSOA = 'J'
  AND CD_TP_OPER IN (61,62)
  AND CD_TP_CTA <> 9
  AND EXISTS  
      (SELECT 1 FROM TB_CAD_CLIE 
       WHERE T.CD_DOC_IDENTF_CLIE <> CD_DOC_IDENTF_CLIE
         AND T.CD_CEP_RESID = CD_CEP_RESID
         HAVING COUNT(*) >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QMesmoCEP_PJ' and cd_regra = 11 and cd_versao_sistema = 3)
        ) 
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 11 
  
  				