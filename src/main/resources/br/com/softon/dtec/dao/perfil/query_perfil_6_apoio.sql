SELECT 
p.dt_perfil,
p.ic_idntr_perfil,
p.nu_estabelecimento,
p.vr_desvio_diario,
p.vr_desvio_transacao,
p.vr_medio_diario,
p.vr_medio_transacao
 FROM perfil.saftb216_perfil_estabelecimento_apoio p
INNER JOIN (select nu_estabelecimento from analise.saftb831_trnso_analise 
				where nu_lote = ? and nu_sublote = ?
				group by nu_estabelecimento) t
ON p.nu_estabelecimento = t.nu_estabelecimento;