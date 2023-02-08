select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE
WHERE CD_TP_PESSOA = 'F'
	AND FL_AGENTE_PUBLICO = 1
	AND VL_OPER IS NOT NULL
	AND VL_RENDA_FAT IS NOT NULL
  	AND VL_OPER > (VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 52 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRenda'))
union
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'F'
	AND FL_AGENTE_PUBLICO = 1
	and not exists (
	select 1 from tb_perfil_mes_calendario p
	where p.cd_identf_perfil     = 9
	  and p.cd_variavel_primeira = t.cd_doc_identf_clie
	  and p.cd_variavel_segunda  = t.cd_tp_identf_clie
	  and p.cd_variavel_terceira = t.cd_tp_oper
	  and p.cd_variavel_quarta   = t.cd_forma_oper )
); 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 52