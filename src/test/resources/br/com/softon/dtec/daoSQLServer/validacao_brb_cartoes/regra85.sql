select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO, CD_CARTAO, DT_TRANS
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND DT_TRANS IS NOT NULL
  AND CD_TP_OPER = 7
  AND CD_VIA_OPER = 1
  AND CD_FORMA_OPER in ( 11, 12, 13)
  AND exists (
	select 1 from tb_trans_anlse 
	where cd_lote = 2014091801
		and dt_trans is not null
		and cd_cartao = t.cd_cartao
		and cd_transacao <> t.cd_transacao
		and cd_tp_oper in (25,26)
		and t.DT_TRANS > DT_TRANS
		and datediff(second,dt_trans,t.dt_trans) <=
		(SELECT VL_PARAM * 1000 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 85 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_tpRecuo')
) 
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 85

