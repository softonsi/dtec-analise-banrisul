select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND DT_TRANS        is not null
  AND CD_CARTAO       is not null
  AND DT_FECHA_FATURA is not NULL
  AND CD_TP_OPER = 39
  AND exists (
  	select count(1) from TB_TRANS_ANLSE
  	where DT_TRANS        is not null
      AND CD_CARTAO       = t.CD_CARTAO
      AND DT_FECHA_FATURA = t.DT_FECHA_FATURA
      AND CD_TRANSACAO    <> t.CD_TRANSACAO
      AND CD_TP_OPER      = 30
      AND VL_OPER         = t.VL_OPER	  
	  AND datediff(second,t.dt_trans,dt_trans) <=
			(SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 1178 AND CD_VERSAO_SISTEMA = 1 AND NM_CAMPO_PARAM = 'pm_tpRecuo')
	  having COUNT(1) > 0
	)
) s; 
select count(1) as total from TB_DETLH_APONTAMENTO WHERE CD_REGRA = 1031


 