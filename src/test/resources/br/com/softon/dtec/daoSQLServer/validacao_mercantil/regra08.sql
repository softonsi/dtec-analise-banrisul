SELECT COUNT(*) AS TOTAL FROM
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE T
WHERE CD_LOTE = 2014091801
	AND CD_TP_OPER IN (25,26)
	AND VL_RENDA_FAT IS NOT NULL
	AND EXISTS (
		SELECT 1 FROM TB_PERFIL_MES_CALENDARIO P
		WHERE CD_IDENTF_PERFIL = 8
			AND CD_VARIAVEL_PRIMEIRA    = T.CD_DOC_IDENTF_CLIE 
			AND CD_VARIAVEL_SEGUNDA     = T.CD_TP_IDENTF_CLIE 
			AND VL_TOTAL IS NOT NULL
			AND VL_TOTAL >= (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_vlCons_CataoPPago' AND CD_REGRA = 8 AND CD_VERSAO_SISTEMA = 3)
			AND VL_TOTAL >= (T.VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'PM_PERC_CARTAOPPAGO' AND CD_REGRA = 8 AND CD_VERSAO_SISTEMA = 3) )
	  		AND P.CD_ANO_MES = CASE WHEN MONTH(T.DT_TRANS) < 10
                                    THEN CAST(YEAR(T.DT_TRANS) AS VARCHAR) + '0' + CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                                    ELSE CAST(YEAR(T.DT_TRANS) AS VARCHAR) +       CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                               END 	  		
	)	
) S; 
SELECT COUNT(1) AS TOTAL FROM TB_DETLH_APONTAMENTO WHERE CD_LOTE = 2014091801 AND CD_REGRA = 8

