select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE cd_lote = 2014091801
	and cd_tp_oper in (25,26)
	and vl_renda_fat is not null
	and exists (
		select 1 from tb_perfil_mes_calendario p
		where cd_identf_perfil = 8
			and cd_Variavel_Primeira    = t.cd_doc_identf_clie 
			and cd_Variavel_Segunda     = t.cd_tp_identf_clie 
			and vl_Total is not null
			and vl_Total >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_vlCons_CartaoPPago' and cd_regra = 8 and cd_versao_sistema = 3)
			and vl_Total >= (t.vl_renda_fat * (select vl_param/100 from tb_regra_parametro where nm_campo_param = 'pm_perc_CartaoPPago' and cd_regra = 8 and cd_versao_sistema = 3) )
	  		and p.cd_ano_mes = CASE WHEN MONTH(GETDATE()) < 10
                                    THEN CAST(YEAR(GETDATE()) AS VARCHAR) + '0' + CAST(MONTH(GETDATE()) AS VARCHAR)
                                    ELSE CAST(YEAR(GETDATE()) AS VARCHAR) +       CAST(MONTH(GETDATE()) AS VARCHAR)
                               END 	  		
	)	
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 8