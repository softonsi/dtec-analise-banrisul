select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE vl_oper > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 13 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_VlEspeciePF') 
	and CD_TP_PESSOA = 'F'
	and CD_FORMA_OPER = 7
	and t.CD_LOTE = 2014091801
	and not exists (
	select 1 from tb_perfil_mes_calendario p
	where p.cd_identf_perfil     = 9
	  and p.cd_variavel_primeira = t.cd_doc_identf_clie
	  and p.cd_variavel_segunda  = t.cd_tp_identf_clie
	  and p.cd_variavel_terceira = t.cd_tp_oper
	  and p.cd_variavel_quarta   = t.cd_forma_oper)
union
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE vl_oper > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 13 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_VlEspeciePJ') 
	and CD_TP_PESSOA = 'J'
	and CD_FORMA_OPER = 7
	and t.CD_LOTE = 2014091801
	and not exists (
	select 1 from tb_perfil_mes_calendario p
	where p.cd_identf_perfil     = 9
	  and p.cd_variavel_primeira = t.cd_doc_identf_clie
	  and p.cd_variavel_segunda  = t.cd_tp_identf_clie
	  and p.cd_variavel_terceira = t.cd_tp_oper
	  and p.cd_variavel_quarta   = t.cd_forma_oper)
union
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE vl_oper > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 13 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_VlEspeciePJ') 
	and CD_TP_PESSOA = 'J'
	and CD_SETOR not in ('A','G','H','I','R','S','T') 
	and CD_FORMA_OPER = 7
	and t.CD_LOTE = 2014091801
	); 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 13