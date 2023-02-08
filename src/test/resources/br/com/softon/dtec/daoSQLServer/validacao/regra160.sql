select count(1) as total from
(
SELECT DISTINCT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE T, TB_PERFIL_INFORMACAO I, TB_PERFIL_INFORMACAO M
WHERE CD_TP_PESSOA = 'F'
  AND FL_FUNCIONARIO = 1
  AND I.CD_IDENTF_PERFIL = 1
  AND I.CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
  AND I.CD_VARIAVEL_SEGUNDA = T.CD_TP_IDENTF_CLIE
  AND M.CD_IDENTF_PERFIL = 11
  AND M.CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
  AND M.CD_VARIAVEL_SEGUNDA = T.CD_TP_IDENTF_CLIE  
  AND I.CD_VARIAVEL_TERCEIRA = M.CD_VARIAVEL_TERCEIRA
  AND I.VL_TOTAL > (M.VL_TOTAL * (select vl_param/100 from TB_REGRA_PARAMETRO where CD_REGRA = 160 and CD_VERSAO_SISTEMA = 3 and NM_CAMPO_PARAM = 'pm_percPerfil'))
  ) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 160

