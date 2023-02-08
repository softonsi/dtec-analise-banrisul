
select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where CD_LOTE = 2014091801
  and FL_CNSTT_TRUST = 1
  and CD_PAIS_RESID <> 1058
  and CD_TP_OPER = 4
  and VL_OPER is not null  
  and VL_OPER > (select vl_param from TB_REGRA_PARAMETRO where CD_REGRA = 70 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_vlSignificativo') 
) as a; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 70



