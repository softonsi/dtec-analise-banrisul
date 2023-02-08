
select count(*) as total from
(
select t.CD_DOC_IDENTF_CLIE, t.CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where cd_lote = 2014091801
  and CD_TP_OPER = 6 /*pagamento*/
  and CD_GRP_CONSORCIO is not null   /*indica que o contrato é consorcio*/
  and DT_LIQUID is not null  /*quitacao*/
  and exists
      (select 1 from TB_TRANS_ANLSE	
       where cd_lote = 2014091801
         and t.CD_CONTRATO = CD_CONTRATO
         and t.NM_SIST_ORIG = NM_SIST_ORIG 
         and CD_TP_OPER = 21 /*cotas de consorcio*/
		 and DATEDIFF(month,DT_TRANS,t.DT_TRANS) <= 1 /*aquisicao seguida de quitacao*/  
	    ) 
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 405

