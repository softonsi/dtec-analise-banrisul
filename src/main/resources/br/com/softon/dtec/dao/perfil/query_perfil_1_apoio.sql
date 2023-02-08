SELECT 
p.co_idntr_conta,
p.co_terminal,
p.dt_perfil,
p.ic_idntr_perfil,
p.nu_agencia_vinculacao
 FROM perfil.saftb211_perfil_cnta_agnca_trmnl_apoio p
INNER JOIN (select co_idntr_conta, nu_agencia_vinculacao, co_terminal from analise.saftb831_trnso_analise 
				where nu_lote = ? and nu_sublote = ? and co_terminal NOT IN ('SIIBC','BMC','TERML-GEN-SIAUT','00000000') 
				group by co_idntr_conta, nu_agencia_vinculacao, co_terminal) t
ON 
	p.co_idntr_conta = t.co_idntr_conta AND 
	p.nu_agencia_vinculacao = t.nu_agencia_vinculacao;