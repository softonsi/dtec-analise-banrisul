/* Operações de saques em paraísos fiscais, cuja somatória seja igual ou superior ao parâmetro, nos últimos 3 meses calendário fechado*/

select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA  = 'F'
  AND CD_TP_OPER    = 7
  AND CD_LOTE = 2014091801 
  AND EXISTS (
SELECT 1
FROM TB_PERFIL_MES_CALENDARIO P
WHERE CD_TP_PESSOA  = 'F'
and CD_IDENTF_PERFIL = 10 AND P.CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE AND P.CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
  AND CD_TP_OPER    = 7
  AND CD_LOTE = 2014091801 
  AND EXISTS (SELECT 1 FROM TB_PAIS WHERE CD_PAIS = T.CD_PAIS_OPER AND FL_PARAIS_FISCAL = 1)
  AND CD_ANO_MES >= LEFT(CONVERT(varchar, DATEADD (MONTH , -2 , T.DT_TRANS),112),6) 
  AND CD_ANO_MES <= LEFT(CONVERT(varchar, DATEADD (MONTH ,  0 , T.DT_TRANS),112),6)
       
having 
  (sum(p.vl_total) >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VSaque_PF' and cd_regra = 17 and cd_versao_sistema = 3)
   OR
   sum(p.qt_total) >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QSaque_PF' and cd_regra = 17 and cd_versao_sistema = 3)
   ))     
       
UNION ALL
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA  = 'J'
  AND CD_TP_OPER    = 7
  AND CD_LOTE = 2014091801 
  AND EXISTS (
SELECT 1
FROM TB_PERFIL_MES_CALENDARIO P
WHERE CD_TP_PESSOA  = 'J'
	and CD_IDENTF_PERFIL = 10 AND P.CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE AND P.CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
  AND CD_TP_OPER    = 7
  AND CD_LOTE = 2014091801 
  AND EXISTS (SELECT 1 FROM TB_PAIS WHERE CD_PAIS = T.CD_PAIS_OPER AND FL_PARAIS_FISCAL = 1)
  AND CD_ANO_MES >= LEFT(CONVERT(varchar, DATEADD (MONTH , -2 , T.DT_TRANS),112),6) 
  AND CD_ANO_MES <= LEFT(CONVERT(varchar, DATEADD (MONTH ,  0 , T.DT_TRANS),112),6)
            
having 
  (sum(p.vl_total) >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VSaque_PJ' and cd_regra = 17 and cd_versao_sistema = 3)
   OR
   sum(p.qt_total) >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QSaque_PJ' and cd_regra = 17 and cd_versao_sistema = 3)
   ))
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 17