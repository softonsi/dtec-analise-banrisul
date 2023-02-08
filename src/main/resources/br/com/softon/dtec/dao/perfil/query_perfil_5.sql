SELECT 
p.dt_perfil,
p.ic_idntr_perfil,
p.nu_cedente,
p.vr_desvio_diario,
p.vr_desvio_transacao,
p.vr_medio_diario,
p.vr_medio_transacao
 FROM perfil.saftb205_perfil_cedente p
INNER JOIN (select nu_cedente from analise.saftb831_trnso_analise 
				where nu_lote = ? and nu_sublote = ? 
				group by nu_cedente) t
ON p.nu_cedente = t.nu_cedente;