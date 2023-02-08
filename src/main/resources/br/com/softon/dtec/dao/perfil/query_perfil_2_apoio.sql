SELECT 
p.co_idntr_conta,
p.co_terminal,
p.dt_perfil,
p.ic_idntr_perfil,
p.nu_estabelecimento
 FROM perfil.saftb212_perfil_cnta_estbo_trmnl_apoio p
INNER JOIN (select co_idntr_conta, nu_estabelecimento, co_terminal from analise.saftb831_trnso_analise 
				where nu_lote = ? and nu_sublote = ? and co_terminal NOT IN ('SIIBC','BMC','TERML-GEN-SIAUT','00000000') 
				group by co_idntr_conta, nu_estabelecimento, co_terminal) t
ON 
	p.co_idntr_conta = t.co_idntr_conta AND 
	p.nu_estabelecimento = t.nu_estabelecimento;