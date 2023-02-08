select count(*) as total from
(
select t.CD_DOC_IDENTF_CLIE, t.CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t,
(select cd_doc_identf_clie, CD_TP_IDENTF_CLIE, SUM(CAST(QT_COTA_CONSORCIO AS NUMERIC)) QContas, SUM(VL_PRINC_CONTRATO) valor
 from TB_CONTRATO
 where DT_LIQUID_CONTRATO is null
   and CD_GRP_CONSORCIO is not null
 group by cd_doc_identf_clie, CD_TP_IDENTF_CLIE) c
where t.CD_DOC_IDENTF_CLIE = c.CD_DOC_IDENTF_CLIE
  and t.CD_TP_IDENTF_CLIE  = c.CD_TP_IDENTF_CLIE
  and cd_lote = 2014091801 
  and t.CD_TP_OPER = 21
  and c.QContas > (select vl_param from TB_REGRA_PARAMETRO where NM_CAMPO_PARAM = 'pm_QtdeContrato' and CD_VERSAO_SISTEMA = 3 and CD_REGRA = 400)
  and c.valor > (t.vl_renda_fat * (select vl_param/100 from TB_REGRA_PARAMETRO where NM_CAMPO_PARAM = 'pm_percRendaFaturamento' and CD_VERSAO_SISTEMA = 3 and CD_REGRA = 400))
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 400
