SELECT 
p.co_idntr_conta,
p.dt_perfil,
p.ic_idntr_perfil,
p.nu_cedente
 FROM perfil.saftb204_perfil_cnta_cedente p
INNER JOIN (select co_idntr_conta, nu_cedente from analise.saftb831_trnso_analise 
				where nu_lote = ? and nu_sublote = ? 
				group by co_idntr_conta, nu_cedente) t
ON 
	p.co_idntr_conta = t.co_idntr_conta AND 
	p.nu_cedente = t.nu_cedente;