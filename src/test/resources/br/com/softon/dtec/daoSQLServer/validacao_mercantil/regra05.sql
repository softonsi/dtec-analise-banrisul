select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE cd_lote = 2014091801
	and cd_natur_oper in (1,2)
	and exists (
		select 1 from tb_perfil_mes_calendario p
		where cd_identf_perfil = 7
			and cd_Variavel_Primeira    = t.cd_doc_identf_clie 
			and cd_Variavel_Segunda     = t.cd_tp_identf_clie 
			and cd_Variavel_Terceira    = t.cd_natur_oper 
			and vl_Total > (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_vlConsMes' and cd_regra = 5 and cd_versao_sistema = 3)
	  		and p.cd_ano_mes = CASE WHEN MONTH(t.dt_trans) < 10
                                                   THEN CAST(YEAR(t.dt_trans) AS VARCHAR) + '0' + CAST(MONTH(t.dt_trans) AS VARCHAR)
                                                   ELSE CAST(YEAR(t.dt_trans) AS VARCHAR) +       CAST(MONTH(t.dt_trans) AS VARCHAR)
                                                END
	        )
union
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE cd_lote = 2014091801
	and cd_natur_oper in (1,2)
	and cd_grp_financ is not null
	and cd_grp_financ > 0
	and exists (
		select 1 from tb_perfil_mes_calendario p
		where cd_identf_perfil = 12
			and cd_Variavel_Primeira    = t.cd_grp_financ 
			and cd_Variavel_Segunda     = t.cd_natur_oper 
			and vl_Total > (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_vlConsMesGrp' and cd_regra = 5 and cd_versao_sistema = 3)
			and p.cd_ano_mes = CASE WHEN MONTH(t.dt_trans) < 10
                                                   THEN CAST(YEAR(t.dt_trans) AS VARCHAR) + '0' + CAST(MONTH(t.dt_trans) AS VARCHAR)
                                                   ELSE CAST(YEAR(t.dt_trans) AS VARCHAR) +       CAST(MONTH(t.dt_trans) AS VARCHAR)
                                                END
	        )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 5