select l.cd_lista_auxiliar, l.nm_lista_auxiliar, l.cd_tp_lista_auxiliar, l.cd_classe_lista_auxiliar, i.ds_conteudo, i.nm_motivo, null as CD_TRANSACAO
from
   tb_conteudo_lista_auxiliar i, tb_lista_auxiliar l
where
   i.cd_lista_auxiliar = l.cd_lista_auxiliar and
   (dateadd(day,i.nu_prazo_validd_dia,i.dt_inclusao) >= getdate() or i.nu_prazo_validd_dia is null);