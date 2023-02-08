
select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE t.CD_LOTE = 2014091801
	and vl_oper is not null
	and cd_tp_cartao = 3
	and (cd_forma_oper in (11,12) or cd_tp_oper in (25,26))
	and exists (
	select 1 from tb_perfil_mes_calendario p
	where p.cd_identf_perfil     = 13
	  and p.cd_variavel_primeira = t.cd_cartao
	  and p.cd_variavel_segunda  = t.cd_pais_oper
	  and p.cd_variavel_terceira = t.sg_uf_ag_oper   
      and p.cd_ano_mes <> CASE WHEN MONTH(GETDATE()) < 10
                              THEN CAST(YEAR(GETDATE()) AS VARCHAR) + '0' + CAST(MONTH(GETDATE()) AS VARCHAR)
                              ELSE CAST(YEAR(GETDATE()) AS VARCHAR) +       CAST(MONTH(GETDATE()) AS VARCHAR)
                         END 	
	  and p.vl_medio_diario is not null 
	  and t.vl_oper > (p.vl_medio_diario * 
	  	(SELECT VL_PARAM/100 FROM TB_REGRA_PARAMETRO WHERE CD_REGRA = 83 AND CD_VERSAO_SISTEMA = 3 AND NM_CAMPO_PARAM = 'pm_percPerfil') )
	)
union
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE t.CD_LOTE = 2014091801
	and vl_oper is not null
	and cd_tp_cartao = 3
	and (cd_forma_oper in (11,12) or cd_tp_oper in (25,26)	)
	and cd_cartao     is not null
	and cd_pais_oper  is not null
	and sg_uf_ag_oper is not null 
	and not exists (
	select 1 from tb_perfil_mes_calendario p
	where p.cd_identf_perfil     = 13
	  and p.cd_variavel_primeira = t.cd_cartao
	  and p.cd_variavel_segunda  = t.cd_pais_oper
	  and p.cd_variavel_terceira = t.sg_uf_ag_oper 
      and p.cd_ano_mes <> CASE WHEN MONTH(GETDATE()) < 10
                              THEN CAST(YEAR(GETDATE()) AS VARCHAR) + '0' + CAST(MONTH(GETDATE()) AS VARCHAR)
                              ELSE CAST(YEAR(GETDATE()) AS VARCHAR) +       CAST(MONTH(GETDATE()) AS VARCHAR)
                         END 	
	)
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 83
