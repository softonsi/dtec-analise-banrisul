select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE t.CD_LOTE = 2014091801
	and vl_oper is not null
	and vl_renda_fat is not null
	and vl_oper > (SELECT VL_PARAM FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 11 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_vlDefinidoLei') 
	and vl_oper > (t.vl_renda_fat * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 11 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRendaFat') )
	and not exists (
	select 1 from tb_perfil_mes_calendario p
	where p.cd_identf_perfil     = 9
	  and p.cd_variavel_primeira = t.cd_doc_identf_clie
	  and p.cd_variavel_segunda  = t.cd_tp_identf_clie 
	  and p.cd_variavel_terceira = t.cd_tp_oper 
	  and p.cd_variavel_quarta   = t.cd_forma_oper )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 11