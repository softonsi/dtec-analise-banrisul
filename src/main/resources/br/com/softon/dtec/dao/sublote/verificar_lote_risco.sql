select cd_lote_risco from tb_lote_risco 
where SUBSTR(CAST(cd_lote_risco AS VARCHAR(10)), 1, 6) = ':cd_lote' and
	cd_status_proces = 90 and
	fl_atulz_ld = 1 and fl_mensal = 1