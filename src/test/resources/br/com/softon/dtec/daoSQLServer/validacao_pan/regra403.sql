

select count(*) as total from
(
select t.CD_DOC_IDENTF_CLIE, t.CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where CD_TP_OPER = 16 /*lace de consorcio*/
  and cd_lote = 2014091801 
  and VL_OPER > (VL_PRINC_CONTRATO  * ( select vl_param/100 from tb_regra_parametro where cd_regra = 403 and CD_VERSAO_SISTEMA = 3 and nm_campo_param = 'pm_percContrato' )) 		
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 403


