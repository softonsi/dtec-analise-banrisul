select count(*) as total from
(
select t.CD_DOC_IDENTF_CLIE, t.CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t,

(select cd_doc_identf_clie, CD_TP_IDENTF_CLIE, SUM(CAST(QT_COTA_CONSORCIO AS NUMERIC)) QAtivos
 from TB_CONTRATO  where DT_LIQUID_CONTRATO is null and CD_GRP_CONSORCIO is not null
 group by cd_doc_identf_clie, CD_TP_IDENTF_CLIE) a,
 
 (select cd_doc_identf_clie, CD_TP_IDENTF_CLIE, SUM(CAST(QT_COTA_CONSORCIO AS NUMERIC)) Qfinalizados
 from TB_CONTRATO  where DT_LIQUID_CONTRATO is not null and CD_GRP_CONSORCIO is not null
 group by cd_doc_identf_clie, CD_TP_IDENTF_CLIE) f
 
where t.CD_DOC_IDENTF_CLIE = a.CD_DOC_IDENTF_CLIE
  and t.CD_TP_IDENTF_CLIE  = a.CD_TP_IDENTF_CLIE
  
  and t.CD_DOC_IDENTF_CLIE = f.CD_DOC_IDENTF_CLIE
  and t.CD_TP_IDENTF_CLIE  = f.CD_TP_IDENTF_CLIE  
  
  and cd_lote = 2014091801 
  and t.CD_TP_OPER = 21
  
  and a.QAtivos > (f.QFinalizados * (select vl_param/100 from TB_REGRA_PARAMETRO where NM_CAMPO_PARAM = 'pm_PercCotas' and CD_VERSAO_SISTEMA = 3 and CD_REGRA = 401))
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 401

