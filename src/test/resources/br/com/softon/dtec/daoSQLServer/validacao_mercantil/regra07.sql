SELECT COUNT(*) AS TOTAL FROM
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO, vl_oper
FROM TB_TRANS_ANLSE T
WHERE CD_LOTE = 2014091801
    AND CD_NATUR_OPER = 2
	AND CD_TP_OPER IN (5,8,11,27)
        AND VL_OPER > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE NM_CAMPO_PARAM = 'pm_vlOper' AND CD_REGRA = 7 AND CD_VERSAO_SISTEMA = 3)
) S; 
SELECT COUNT(1) AS TOTAL FROM TB_DETLH_APONTAMENTO WHERE CD_LOTE = 2014091801 AND CD_REGRA = 7


