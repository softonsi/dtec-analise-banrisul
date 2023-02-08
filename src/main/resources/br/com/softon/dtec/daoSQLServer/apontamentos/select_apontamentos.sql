SELECT 
DT_APONTAMENTO,
CD_DOC_IDENTF_CLIE,
CD_TP_IDENTF_CLIE,
CD_STTUS_EVENTO_ATUAL,
VL_APONTAMENTO,
VL_PONTO_CORTE,
DT_ATUALZ_CALCULO,
FL_CARENCIA,
FL_PONTO_CORTE,
FL_SUSP_LD,
CD_LOTE 
FROM TB_APONTAMENTO a
WHERE 
	CD_STTUS_EVENTO_ATUAL NOT IN (
		SELECT CD_STTUS_EVENTO FROM TB_STTUS_EVENTO WHERE FL_FIM_INVTGA = 1
		) AND
	CD_STTUS_EVENTO_ATUAL <> 1 AND 
	SUBSTR(CAST(CD_LOTE AS VARCHAR(10)), 1, 6) = SUBSTR(CAST(? AS VARCHAR(10)), 1, 6)