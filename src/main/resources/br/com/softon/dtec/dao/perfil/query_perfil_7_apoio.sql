SELECT 
p.co_idntr_conta,
p.dt_perfil,
p.ic_idntr_perfil,
p.co_servico,
p.vr_desvio_transacao,
p.vr_medio_transacao
 FROM perfil.saftb217_perfil_cnta_tpo_trnso_apoio p
INNER JOIN (select co_idntr_conta, co_servico from analise.saftb831_trnso_analise 
				where nu_lote = ? and nu_sublote = ? 
				group by co_idntr_conta, co_servico) t
ON 
	p.co_idntr_conta = t.co_idntr_conta AND 
	p.co_servico = t.co_servico;