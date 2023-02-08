
/* Saques realizados em UF´s diferentes do cadastro, nos últimos 3 meses calendários fechados. Esta regra não se aplica a FOPA.*/
/*Os critérios são “e”, ou seja “x operações e R$ y” */

select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T1
WHERE CD_TP_PESSOA  = 'F'
  AND CD_TP_OPER    = 7
  AND CD_NATUR_OPER = 2
  AND CD_TP_CTA    <> 9
  AND SG_UF_ABERT_CTA <> SG_UF_AG_OPER
  AND CD_LOTE = 2014091801   AND CD_SUBLOTE = 1
  AND EXISTS (
  SELECT 1 from TB_PERFIL_MES_CALENDARIO P
  WHERE P.CD_VARIAVEL_PRIMEIRA = T1.CD_DOC_IDENTF_CLIE
    AND P.CD_VARIAVEL_SEGUNDA  = T1.CD_TP_IDENTF_CLIE
    AND CD_IDENTF_PERFIL = 9
    AND CD_ANO_MES >= LEFT(CONVERT(varchar, DATEADD (MONTH , -2 , T1.DT_TRANS),112),6)
    AND CD_ANO_MES <= LEFT(CONVERT(varchar, DATEADD (MONTH ,  0 , T1.DT_TRANS),112),6)
  HAVING (SUM(VL_TOTAL) >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VSaque_PF' and cd_regra = 16 and cd_versao_sistema = 3)
	   OR
	   SUM(QT_TOTAL) >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QSaque_PF' and cd_regra = 16 and cd_versao_sistema = 3)
       ) 
  )
       
UNION
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T1
WHERE CD_TP_PESSOA  = 'J'
  AND CD_TP_OPER    = 7
  AND CD_NATUR_OPER = 2
  AND CD_TP_CTA    <> 9
  AND SG_UF_ABERT_CTA IS NOT NULL
  AND SG_UF_AG_OPER IS NOT NULL
  AND SG_UF_ABERT_CTA <> SG_UF_AG_OPER
  AND CD_LOTE = 2014091801  AND CD_SUBLOTE = 1
  AND EXISTS (
SELECT 1 FROM TB_PERFIL_MES_CALENDARIO P
WHERE P.CD_VARIAVEL_PRIMEIRA = T1.CD_DOC_IDENTF_CLIE
  AND P.CD_VARIAVEL_SEGUNDA  = T1.CD_TP_IDENTF_CLIE
  AND CD_IDENTF_PERFIL = 9
  AND CD_ANO_MES >= LEFT(CONVERT(varchar, DATEADD (MONTH , -2 , T1.DT_TRANS),112),6)
  AND CD_ANO_MES <= LEFT(CONVERT(varchar, DATEADD (MONTH ,  0 , T1.DT_TRANS),112),6)
  HAVING (SUM(VL_TOTAL) >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VSaque_PJ' and cd_regra = 16 and cd_versao_sistema = 3)
	   OR
	   SUM(QT_TOTAL) >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QSaque_PJ' and cd_regra = 16 and cd_versao_sistema = 3)
       ) 
  )
 ) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 16
  
  					