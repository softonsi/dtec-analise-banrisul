
select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where CD_LOTE = 2014091801
  and CD_TP_PESSOA = 'F'
  and CD_NATUR_OPER = 2
  and CD_TP_OPER in (6,5,8,9)
  and SG_UF_RESID <> SG_UF_AG_DESTORIG
  and VL_OPER > (select vl_param from TB_REGRA_PARAMETRO where CD_REGRA = 65 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_VlFornecedor') 
) as a; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 65

