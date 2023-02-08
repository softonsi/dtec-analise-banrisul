select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE    = 2014091801
  and CD_TP_PESSOA = 'F'
  And Cd_Tp_Titular_Cta In (1,2)
  and vl_sld_medio > (vl_renda_fat * 
         (Select Vl_Param/100 From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 69 And Nm_Campo_Param = 'pm_percRenda'))
union
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE    = 2014091801
  and CD_TP_PESSOA = 'F'
  and cd_tp_titular_cta in (1,2)
  And Exists
    (select 1 from tb_perfil_mes_calendario 
	  where cd_identf_perfil = 6
	    and cd_ano_mes = CASE WHEN MONTH(t.dt_trans) < 10
                         THEN CAST(YEAR(t.dt_trans) AS VARCHAR) + '0' + CAST(MONTH(t.dt_trans) AS VARCHAR)
                         ELSE CAST(YEAR(t.dt_trans) AS VARCHAR) +       CAST(MONTH(t.dt_trans) AS VARCHAR)
                         END
	                     
		and cd_variavel_primeira = t.cd_banco
		and cd_variavel_segunda  = t.cd_ag
		and cd_variavel_terceira = t.cd_cta
		having count(*) >
  	       (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 69 And Nm_Campo_Param = 'pm_QtTipoDiferentes')
      )
union
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'F'
  and cd_tp_titular_cta in (1,2)
  And Exists
    (select 1 from tb_perfil_mes_calendario 
	  where cd_identf_perfil = 6
	    and cd_ano_mes = CASE WHEN MONTH(t.dt_trans) < 10
                         THEN CAST(YEAR(t.dt_trans) AS VARCHAR) + '0' + CAST(MONTH(t.dt_trans) AS VARCHAR)
                         ELSE CAST(YEAR(t.dt_trans) AS VARCHAR) +       CAST(MONTH(t.dt_trans) AS VARCHAR)
                         END

		and cd_variavel_primeira = t.cd_banco
		and cd_variavel_segunda  = t.cd_ag
		and cd_variavel_terceira = t.cd_cta
		having sum(vl_total) >
  	       (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 69 And Nm_Campo_Param = 'pm_VlMesCalendario')
      )
) S; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 69



