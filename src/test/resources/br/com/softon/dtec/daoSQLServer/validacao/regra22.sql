select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE
WHERE CD_FORMA_OPER IN (4, 8)
  AND VL_OPER > 
  (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 22 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_ValorAltoMoedaEstrangeira')
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 22