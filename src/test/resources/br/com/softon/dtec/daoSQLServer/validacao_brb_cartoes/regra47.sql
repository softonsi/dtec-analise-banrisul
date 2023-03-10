select count(*) as total from
(
SELECT t.CD_DOC_IDENTF_CLIE, t.CD_TP_IDENTF_CLIE, t.CD_TRANSACAO, t.NM_ENDER_RESID, t.CD_CEP_RESID
FROM TB_TRANS_ANLSE t
WHERE T.CD_LOTE = 2014091801 
  AND T.CD_TP_PESSOA = 'J'
  AND T.NM_ENDER_RESID is not NULL 
  AND T.CD_CEP_RESID is not NULL
  AND EXISTS 
      (SELECT 1 FROM TB_CAD_CLIE c
       WHERE c.NM_ENDER_RESID is not NULL 
         AND c.CD_CEP_RESID   is not NULL  	
         AND c.NM_ENDER_RESID = t.NM_ENDER_RESID
         AND c.CD_CEP_RESID   = t.CD_CEP_RESID  
         AND c.CD_TP_PESSOA   = 'J'
         AND c.CD_DOC_IDENTF_CLIE <> t.CD_DOC_IDENTF_CLIE
       HAVING COUNT(*) > 1)
) s; 
select count(1) as total from TB_DETLH_APONTAMENTO WHERE CD_REGRA = 47
