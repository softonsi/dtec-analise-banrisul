select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where CD_LOTE = 2014091801
  and CD_TP_PESSOA = 'F'
  and CD_NATUR_OPER = 1 /*Crédito*/
  and CD_FORMA_OPER = 8 /*Moeda Estrangeira Especie*/ 
  and CD_PAIS_RESID <> 1058 /*Brasil*/
  and CD_PAIS_OPER = 1058   /*Brasil*/
  and exists 
    (select 1 from TB_TRANS_ANLSE
     where t.CD_DOC_IDENTF_CLIE = CD_DOC_IDENTF_CLIE
       and t.CD_TP_IDENTF_CLIE  = CD_TP_IDENTF_CLIE
       and CD_TP_OPER = 11 /*ordem de pagamento */
       and VL_OPER = t.VL_OPER
       and DT_TRANS < t.DT_TRANS
       and DS_DECLR_PROPSTO is null
       having SUM(vl_oper) > (select vl_param from TB_REGRA_PARAMETRO where CD_REGRA = 34 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_ValorExpressivo')
     )
) as a; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 34