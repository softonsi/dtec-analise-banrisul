
select count(*) as total from
(
select t.CD_DOC_IDENTF_CLIE, t.CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where cd_lote = 2014091801
  and CD_TP_OPER = 6  /*pagamento*/
  and CD_GRP_CONSORCIO is not null /*indica que o contrato é consorcio*/
  and DT_PRIM_VCTO_CONTRATO is not null
  and QT_MES_PAGO is not null
  and QT_MES_PAGO > DATEDIFF(month,DT_TRANS,DT_PRIM_VCTO_CONTRATO)
  
  /* calcula percentual de antecipação*/
  /*não dividir o parametro pm_percAntecipacao por 100*/
  and (100 - ((DATEDIFF(month,DT_TRANS,DT_PRIM_VCTO_CONTRATO)/QT_MES_PAGO) * 100)) > ( select vl_param from tb_regra_parametro where cd_regra = 404 and CD_VERSAO_SISTEMA = 3 and nm_campo_param = 'pm_percAntecipacao' )
  
  /* valor da antecipação, quantidade de parcelas antecipadas vezes o valor da parcela)*/  
  and ((QT_MES_PAGO - DATEDIFF(month,DT_TRANS,DT_PRIM_VCTO_CONTRATO)) * VL_PCELA_CONTRATO) > (VL_RENDA_FAT * (select vl_param/100 from tb_regra_parametro where cd_regra = 404 and CD_VERSAO_SISTEMA = 3 and nm_campo_param = 'pm_percRendaFaturamento') )
  
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 404

