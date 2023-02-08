select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where CD_LOTE = 2014091801
  and FL_ORGNZ_SLUCRO = 1
  and EXISTS (select 1 from TB_MED_RAMO_ATIVID where CD_RAMO_ATIVID = t.CD_RAMO_ATIVID and VL_MED_CREDITO is not null and t.VL_SLD_MEDIO > VL_MED_CREDITO)
) as a; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 67