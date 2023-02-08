select l.cd_lista_auxiliar, l.nm_lista_auxiliar, l.cd_tp_lista_auxiliar, l.cd_classe_lista_auxiliar, i.ds_conteudo, i.nm_motivo, null as CD_TRANSACAO
from tb_conteudo_lista_auxiliar i, tb_lista_auxiliar l
where (i.cd_lista_auxiliar = l.cd_lista_auxiliar and (i.dt_inclusao + i.nu_prazo_validd_dia >= sysdate or i.nu_prazo_validd_dia is null))
    AND l.cd_lista_auxiliar NOT IN (201,202,203,204,205)
UNION ALL
select l.cd_lista_auxiliar, l.nm_lista_auxiliar, l.cd_tp_lista_auxiliar, l.cd_classe_lista_auxiliar, i.ds_conteudo, i.nm_motivo
from tb_conteudo_lista_auxiliar i, tb_lista_auxiliar l, TB_TRANS_ANLSE T
where (i.cd_lista_auxiliar = l.cd_lista_auxiliar and (i.dt_inclusao + i.nu_prazo_validd_dia >= sysdate or i.nu_prazo_validd_dia is null))
    AND T.CD_LOTE = ? AND T.CD_SUBLOTE = ? AND I.DS_CONTEUDO = T.NM_CLIE AND l.cd_lista_auxiliar IN (201,202,203,204,205);