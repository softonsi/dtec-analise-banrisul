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
	    and cd_ano_mes <> (extract(year from sysdate) || lpad(extract(month from sysdate),2,0))
		and cd_variavel_primeira = t.cd_banco
		and cd_variavel_segunda  = t.cd_ag
		and cd_variavel_terceira = t.cd_cta
		having count(*) >
  	       (Select Vl_Param From Tb_Regra_Parametro Where Cd_Versao_Sistema = 3 And Cd_Regra = 69 And Nm_Campo_Param = 'pm_Qt_tp_Diferentes')
      )
union
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'F'
	and CD_LOTE    = 2014091801
	and CD_TP_OPER = 20
	and DT_NASCTO_FUND is not null
	and cd_contrato is not null
	and Trunc((Months_Between(Sysdate, DT_NASCTO_FUND))/12) < 18
) S; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 69

