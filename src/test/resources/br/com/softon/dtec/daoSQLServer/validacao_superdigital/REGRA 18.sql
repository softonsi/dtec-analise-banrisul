/* Contas abertas em regiões de risco com status A – Apto para utilização*/


select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_OPER     = 61
  AND CD_STTUS_CTA   = 1
  AND NM_CID_ABERT_CTA IS NOT NULL
  AND SG_UF_ABERT_CTA IS NOT NULL
  AND CD_LOTE = 2014091801 
  AND EXISTS
      (SELECT 1 FROM TB_CONTEUDO_LISTA_AUXILIAR
        WHERE CD_LISTA_AUXILIAR = 4
          AND UPPER(LTRIM(RTRIM(DS_CONTEUDO))) = UPPER(LTRIM(RTRIM(T.NM_CID_ABERT_CTA + '-' + T.SG_UF_ABERT_CTA)))
       )
 ) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 18
			
  				