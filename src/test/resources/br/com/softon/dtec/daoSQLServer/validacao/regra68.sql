select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND CD_DOC_IDENTF_BNEFC IS NOT NULL
  AND CD_TP_IDENTF_BNEFC IS NOT NULL
  AND EXISTS (
	select 1 from tb_ppe
	where
		CD_DOC_IDENTF = t.CD_DOC_IDENTF_BNEFC
		AND CD_TP_IDENTF = t.CD_TP_IDENTF_BNEFC
  )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 68