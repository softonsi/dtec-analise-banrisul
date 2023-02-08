select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE cd_lote = 2014091801
	and cd_natur_oper = 1
	and cd_tp_oper = 4
	and (
		dt_trans is null or 
		vl_oper is null or
		NM_PSSOA_REALZ_TRANS is null or
		CD_DOC_IDENTF_REALZ_TRANS is null
	)
union 
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE cd_lote = 2014091801
	and cd_tp_oper = 17
	and (
		dt_trans is null or 
		vl_oper is null or
		NM_DESTORIG is null or
		CD_DOC_IDENTF_DESTORIG is null or
		CD_BCO_DESTORIG is null or
		CD_AG_DESTORIG is null or
		CD_CTA_DESTORIG is null
	)  
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE
WHERE CD_LOTE = 2014091801
  AND CD_TP_OPER = 7
  AND CD_FORMA_OPER = 12
  AND CD_PAIS_RESID = 986  
  AND (
	dt_trans is null or 
	vl_oper is null or
  	NM_BNEFC_CARTAO IS NULL OR 
  	CD_DOC_IDENTF_BNEFC IS NULL
  )
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE
WHERE CD_LOTE = 2014091801
  AND CD_TP_OPER = 7
  AND CD_FORMA_OPER = 12
  AND CD_PAIS_RESID <> 986  
  AND (
	dt_trans is null or 
	vl_oper is null or
  	NM_BNEFC_CARTAO IS NULL OR 
  	CD_DOC_IDENTF_BNEFC IS NULL OR
  	CD_PAIS_PASSRTE_BNEFC IS NULL
  )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 10