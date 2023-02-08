select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE cd_lote = 2014091801
  and CD_TP_OPER = 63
  and CD_CARTAO IS NOT NULL
  AND  EXISTS (
   SELECT 1 FROM TB_PERFIL_INFORMACAO
   WHERE CD_IDENTF_PERFIL = 25
     AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
     AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
     AND CD_VARIAVEL_TERCEIRA = T.CD_CARTAO
     AND QT_TOTAL > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 205 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_qCancelamento')
   )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 205