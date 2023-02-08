SELECT 
p.co_idntr_conta,
p.co_idntr_conta_orgm_dstno,
p.dt_perfil,
p.ic_idntr_perfil
 FROM perfil.saftb213_perfil_cnta_dstno_apoio p
INNER JOIN (select co_idntr_conta, co_idntr_conta_orgm_dstno from analise.saftb831_trnso_analise 
				where nu_lote = ? and nu_sublote = ? 
				group by co_idntr_conta, co_idntr_conta_orgm_dstno) t
ON 
	p.co_idntr_conta = t.co_idntr_conta AND 
	p.co_idntr_conta_orgm_dstno = t.co_idntr_conta_orgm_dstno;