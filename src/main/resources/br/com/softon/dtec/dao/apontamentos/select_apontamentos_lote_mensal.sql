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
	CD_STTUS_EVENTO_ATUAL = 2 AND
	TO_CHAR(DT_APONTAMENTO, 'YYYYMM') <= ?
order by DT_APONTAMENTO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE
 OFFSET ? ROWS
 FETCH NEXT ? ROWS ONLY