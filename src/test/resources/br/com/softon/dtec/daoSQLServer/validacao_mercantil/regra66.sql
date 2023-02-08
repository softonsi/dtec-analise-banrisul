
select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where CD_LOTE = 2014091801
  and CD_TP_OPER = 4 /*deposito*/
  and CD_FORMA_OPER = 2 /*cheque*/
  and FL_CHEQUE_ENDOSSO = 1
  and exists
     (select 1 from TB_TRANS_ANLSE 
      where CD_DOC_IDENTF_CLIE = t.CD_DOC_IDENTF_CLIE
        and CD_TP_IDENTF_CLIE  = t.CD_TP_IDENTF_CLIE
        and CD_TP_OPER = 4 /*deposito*/
        and CD_FORMA_OPER = 2 /*cheque*/
        and FL_CHEQUE_ENDOSSO = 1
        having SUM(vl_oper) > (select vl_param from TB_REGRA_PARAMETRO where CD_REGRA = 66 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_ValorExpressivo')
      )  
) as a; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 66



