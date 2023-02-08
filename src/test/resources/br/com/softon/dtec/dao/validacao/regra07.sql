select count(*) as total from
(SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE FROM TB_TRANS_ANLSE
WHERE CD_TP_OPER IN (27, 11, 5, 8)
  AND VL_OPER > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 7 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_vlOper')
ORDER BY 1); 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 7