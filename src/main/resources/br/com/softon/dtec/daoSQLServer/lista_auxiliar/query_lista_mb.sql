select 
	l.cd_lista_auxiliar, 
	l.nm_lista_auxiliar, 
	l.cd_tp_lista_auxiliar, 
	l.cd_classe_lista_auxiliar, 
	i.ds_conteudo, 
	i.nm_motivo,
	t.cd_transacao
from tb_conteudo_lista_auxiliar i
join tb_lista_auxiliar l on i.cd_lista_auxiliar = l.cd_lista_auxiliar and (dateadd(day,i.nu_prazo_validd_dia,i.dt_inclusao) >= getdate() or i.nu_prazo_validd_dia is null)
join tb_trans_anlse t on 
t.cd_lote = ? AND t.cd_sublote = ? AND 
i.ds_conteudo = t.nm_clie
union
select 
	l.cd_lista_auxiliar, 
	l.nm_lista_auxiliar, 
	l.cd_tp_lista_auxiliar, 
	l.cd_classe_lista_auxiliar, 
	i.ds_conteudo, 
	i.nm_motivo,
	t.cd_transacao
from tb_conteudo_lista_auxiliar i
join tb_lista_auxiliar l on i.cd_lista_auxiliar = l.cd_lista_auxiliar and (dateadd(day,i.nu_prazo_validd_dia,i.dt_inclusao) >= getdate() or i.nu_prazo_validd_dia is null)
join tb_trans_anlse t on 
t.cd_lote = ? AND t.cd_sublote = ? AND 
i.ds_conteudo = t.cd_doc_identf_clie
union
select 
	l.cd_lista_auxiliar, 
	l.nm_lista_auxiliar, 
	l.cd_tp_lista_auxiliar, 
	l.cd_classe_lista_auxiliar, 
	i.ds_conteudo, 
	i.nm_motivo,
	t.cd_transacao
from tb_conteudo_lista_auxiliar i
join tb_lista_auxiliar l on i.cd_lista_auxiliar = l.cd_lista_auxiliar and (dateadd(day,i.nu_prazo_validd_dia,i.dt_inclusao) >= getdate() or i.nu_prazo_validd_dia is null)
join tb_trans_anlse t on 
t.cd_lote = ? AND t.cd_sublote = ? AND 
i.ds_conteudo = t.cd_doc_identf_destorig
union
select 
	l.cd_lista_auxiliar, 
	l.nm_lista_auxiliar, 
	l.cd_tp_lista_auxiliar, 
	l.cd_classe_lista_auxiliar, 
	i.ds_conteudo, 
	i.nm_motivo,
	t.cd_transacao
from tb_conteudo_lista_auxiliar i
join tb_lista_auxiliar l on i.cd_lista_auxiliar = l.cd_lista_auxiliar and (dateadd(day,i.nu_prazo_validd_dia,i.dt_inclusao) >= getdate() or i.nu_prazo_validd_dia is null)
join tb_trans_anlse t on 
t.cd_lote = ? AND t.cd_sublote = ? AND 
i.ds_conteudo = t.nm_destorig