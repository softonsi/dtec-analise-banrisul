select min(lote) from 
	(SELECT DISTINCT SUBSTR(CAST(CD_LOTE AS VARCHAR(10)), 1, 6) lote
	  FROM TB_LOTE_PROCES
	  WHERE CD_STATUS_PROCES = 90
		AND SUBSTR(CAST(CD_LOTE AS VARCHAR(10)), 1, 6) NOT IN
	   (SELECT SUBSTR(CAST(CD_LOTE AS VARCHAR(10)), 1, 6)
	     FROM TB_LOTE_PROCES
	     WHERE CD_STATUS_PROCES <> 90
	       AND LENGTH(CD_LOTE) = 10)
	) a
	WHERE a.lote not in
	    (select cd_lote from tb_lote_proces where cd_status_proces = 90)
	  and a.lote < (SELECT DISTINCT MAX(TO_CHAR(DT_TRANS, 'YYYYMM')) FROM TB_TRANS_ANLSE)