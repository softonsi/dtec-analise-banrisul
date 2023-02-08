select count(*) as total from
(
select CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_TP_PESSOA = 'F'
	and FL_FUNCIONARIO = 1
	and CD_LOTE = 2014091801
	and exists (
	select 1 from tb_perfil_informacao p
	where p.cd_identf_perfil     = 1
	  and p.cd_variavel_primeira = t.cd_doc_identf_clie
	  and p.cd_variavel_segunda  = t.cd_tp_identf_clie
	  and exists (
		select 1 from tb_perfil_mes_calendario pm
		where pm.cd_identf_perfil     = 1
		  and pm.cd_variavel_primeira = t.cd_doc_identf_clie
		  and pm.cd_variavel_segunda  = t.cd_tp_identf_clie
		  and pm.cd_variavel_terceira = p.cd_variavel_terceira
		  and p.vl_total > pm.vl_total
	  	)
	  )
  			) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 59