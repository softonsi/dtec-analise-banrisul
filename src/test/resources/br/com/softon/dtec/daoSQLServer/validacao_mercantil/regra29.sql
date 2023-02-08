
select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where CD_LOTE = 2014091801
  and CD_TP_PESSOA = 'J'
  and CD_TP_OPER = 4 /*deposito*/
  and CD_FORMA_OPER = 7 /*especie*/
  and VL_OPER <= (select vl_param from TB_REGRA_PARAMETRO where CD_REGRA = 29 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_ValorPequeno')
  and CD_RAMO_ATIVID not IN (SELECT * from splitstring((select DS_PARAM from tb_regra_parametro where cd_regra = 29 and cd_versao_sistema = 3 and NM_CAMPO_PARAM = 'pm_Lista_Setores')))
  and exists
     (select 1 from TB_TRANS_ANLSE 
      where CD_DOC_IDENTF_CLIE = t.CD_DOC_IDENTF_CLIE
        and CD_TP_IDENTF_CLIE  = t.CD_TP_IDENTF_CLIE
        and CD_TP_OPER    = 4 /*deposito*/
        and CD_FORMA_OPER = 7 /*especie*/
        and VL_OPER <= (select vl_param from TB_REGRA_PARAMETRO where CD_REGRA = 29 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_ValorPequeno')
        having (count(*) >= (select vl_param from TB_REGRA_PARAMETRO where CD_REGRA = 29 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_QOcorrênciaPJ')
                or
                sum(vl_oper) >= (select vl_param from TB_REGRA_PARAMETRO where CD_REGRA = 29 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_VTotal_PJ')
                )
      ) 
union
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where CD_LOTE = 2014091801
  and CD_TP_PESSOA  = 'F'
  and CD_TP_OPER    = 4 /*deposito*/
  and CD_FORMA_OPER = 7 /*especie*/
  and VL_OPER <= (select vl_param from TB_REGRA_PARAMETRO where CD_REGRA = 29 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_ValorPequeno')
  and not EXISTS
      (SELECT 1 FROM TB_CLIE_RENDA
        where CD_DOC_IDENTF_CLIE = t.CD_DOC_IDENTF_CLIE
         and CD_TP_IDENTF_CLIE  = t.CD_TP_IDENTF_CLIE
         and CD_OCUP not IN (SELECT * from splitstring((select DS_PARAM from tb_regra_parametro where cd_regra = 29 and cd_versao_sistema = 3 and NM_CAMPO_PARAM = 'pm_Lista_Setores')))
         )
  and exists
     (select 1 from TB_TRANS_ANLSE 
      where CD_DOC_IDENTF_CLIE = t.CD_DOC_IDENTF_CLIE
        and CD_TP_IDENTF_CLIE  = t.CD_TP_IDENTF_CLIE
        and CD_TP_OPER    = 4 /*deposito*/
        and CD_FORMA_OPER = 7 /*especie*/
        and VL_OPER <= (select vl_param from TB_REGRA_PARAMETRO where CD_REGRA = 29 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_ValorPequeno')
        having (count(*) >= (select vl_param from TB_REGRA_PARAMETRO where CD_REGRA = 29 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_QOcorrênciaPF')
                or
                sum(vl_oper) >= (select vl_param from TB_REGRA_PARAMETRO where CD_REGRA = 29 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_VTotal_PF')
                )
      )  
      
       
) as a; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 29



