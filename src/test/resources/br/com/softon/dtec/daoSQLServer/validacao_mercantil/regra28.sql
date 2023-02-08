select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where CD_LOTE = 2014091801
  and CD_TP_OPER = 4 /*deposito*/
  and CD_FORMA_OPER = 7 /*especie */
  and CD_IDENTF_SIT_CEDULA = 1 
) as a; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 28
