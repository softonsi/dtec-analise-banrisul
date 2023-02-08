select count(*) as total from
(select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE exists (
select 1 from tb_perfil_mes_calendario p
where cd_identf_perfil = 7
	and cd_Variavel_Primeira    = t.cd_doc_identf_clie 
	and	cd_Variavel_Segunda     = t.cd_tp_identf_clie 
	and cd_Variavel_Terceira    = 1 
	and vl_Total  > (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_vlConsMes' and cd_regra = 5 and cd_versao_sistema = 3)
group by t.cd_doc_identf_clie, t.cd_tp_identf_clie
having count(*) > 0)
); 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 5