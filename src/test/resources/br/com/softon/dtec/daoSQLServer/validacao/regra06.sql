select count(*) as total from
(select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_NATUR_OPER = 1
  AND exists (
select 1 from TB_PERFIL_INFORMACAO p
where cd_identf_perfil = 1
	and cd_Variavel_Primeira    = t.cd_doc_identf_clie 
	and	cd_Variavel_Segunda     = t.cd_tp_identf_clie 
	and cd_Variavel_Terceira    = 1 
	and vl_Total  > (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_vlCons' and cd_regra = 6 and cd_versao_sistema = 3)
  	AND vl_Total > ( t.VL_RENDA_FAT * (SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 6 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percRendaFat') )
group by cd_Variavel_Primeira, cd_Variavel_Segunda
having count(*) >= 1)
  AND exists (
select 1 from TB_PERFIL_INFORMACAO p
where cd_identf_perfil = 2
	and cd_Variavel_Primeira    = t.cd_doc_identf_clie 
	and	cd_Variavel_Segunda     = t.cd_tp_identf_clie 
group by cd_Variavel_Primeira, cd_Variavel_Segunda
having count(*) >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QtTiposDiferentes' and cd_regra = 6 and cd_versao_sistema = 3))
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 6