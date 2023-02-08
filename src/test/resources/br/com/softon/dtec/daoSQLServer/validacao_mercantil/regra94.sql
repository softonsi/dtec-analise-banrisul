select count(1) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE T1
WHERE CD_LOTE = 2014091801
  AND CD_TP_OPER = 6
  AND CD_CONTRATO IS NOT NULL
  AND DT_LIQUID IS NOT NULL
  AND CD_PAIS_OPER = 1058  
  AND EXISTS
    (SELECT 1 FROM TB_CONTRATO P
     WHERE CD_CONTRATO IS NOT NULL
       AND DT_LIQUID_CONTRATO IS NOT NULL
       AND T1.CD_CONTRATO = P.CD_CONTRATO
       AND T1.NM_SIST_ORIG = P.NM_SIST_ORIG      
       AND P.CD_DOC_IDENTF_CLIE <> T1.CD_DOC_IDENTF_CLIE
      )      
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 94