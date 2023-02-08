select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
from TB_TRANS_ANLSE t
where CD_LOTE = 2014091801
  and CD_NATUR_OPER = 2
  and CD_PAIS_DESTORIG is not null
  and CD_PAIS_DESTORIG <> 1058
  and exists 
      (select 1 from TB_TRANS_ANLSE a
       where t.CD_DOC_IDENTF_CLIE = a.CD_DOC_IDENTF_CLIE
         and t.CD_TP_IDENTF_CLIE  = a.CD_TP_IDENTF_CLIE
         and a.CD_TP_OPER         = 20 /*operação de credito*/
         and a.CD_PAIS_OPER       = 1058      
         and t.VL_OPER >= (a.VL_OPER * (select vl_param/100 from TB_REGRA_PARAMETRO where CD_REGRA = 92 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_percValor') )
         and t.VL_OPER <= a.VL_OPER
         and a.DT_TRANS < t.DT_TRANS
        ) 
) as a; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 92

				 
