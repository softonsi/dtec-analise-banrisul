UPDATE TB_APONTAMENTO
	SET
		CD_STTUS_EVENTO_ATUAL = ?,
		FL_CARENCIA = ?,
		FL_PONTO_CORTE = ?,
		FL_SUSP_LD = ?,
		DT_ULTM_PARECER = ?
	WHERE
		DT_APONTAMENTO = ? AND
		CD_TP_IDENTF_CLIE = ? AND
		CD_DOC_IDENTF_CLIE = ? AND
		CD_LOTE = ?