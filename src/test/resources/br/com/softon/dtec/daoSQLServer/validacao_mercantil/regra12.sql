SELECT COUNT(*) AS TOTAL FROM
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE T
WHERE T.CD_LOTE = 2014091801
	AND FL_INFO_COAF = 1
) S; 
SELECT COUNT(1) AS TOTAL FROM TB_DETLH_APONTAMENTO WHERE CD_LOTE = 2014091801 AND CD_REGRA = 12
