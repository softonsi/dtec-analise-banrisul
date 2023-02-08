select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND CD_TP_OPER = 39
  AND exists (
	select 1 from tb_trans_anlse 
	where cd_transacao <> t.cd_transacao
		and cd_contrato = t.cd_contrato
		and nm_sist_orig = t.nm_sist_orig
		and dt_trans < t.dt_trans
		and cd_tp_oper = 6
		and vl_oper is not null
		and vl_pcela_contrato is not null
		and vl_oper > vl_pcela_contrato
 ) 
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 200