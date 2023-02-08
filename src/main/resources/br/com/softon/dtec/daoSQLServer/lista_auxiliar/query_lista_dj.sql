SELECT l.cd_lista_auxiliar, null as CD_TRANSACAO, l.nm_lista_auxiliar, l.cd_tp_lista_auxiliar, l.cd_classe_lista_auxiliar, i.ds_conteudo, i.nm_motivo
from tb_conteudo_lista_auxiliar i, tb_lista_auxiliar l
where (i.cd_lista_auxiliar = l.cd_lista_auxiliar and (dateadd(day,i.nu_prazo_validd_dia,i.dt_inclusao) >= getdate() or i.nu_prazo_validd_dia is null))
    AND l.cd_lista_auxiliar NOT IN (201,202,203,204,205)
UNION ALL
select l.cd_lista_auxiliar, T.CD_TRANSACAO, l.nm_lista_auxiliar, l.cd_tp_lista_auxiliar, l.cd_classe_lista_auxiliar, i.ds_conteudo, i.nm_motivo
from tb_conteudo_lista_auxiliar i, tb_lista_auxiliar l, TB_TRANS_ANLSE T
where (i.cd_lista_auxiliar = l.cd_lista_auxiliar and (dateadd(day,i.nu_prazo_validd_dia,i.dt_inclusao) >= getdate() or i.nu_prazo_validd_dia is null)) AND
      T.CD_LOTE = ? AND T.CD_SUBLOTE = ? AND I.DS_CONTEUDO = T.NM_CLIE AND l.cd_lista_auxiliar IN (201,202,203,204,205) AND
      ((i.NU_DIA IS NOT NULL AND i.NU_MES IS NOT NULL AND i.NU_ANO IS NOT NULL AND (
                                                                                    day(T.DT_NASCTO_FUND)        = i.NU_DIA AND
                                                                                    month(T.DT_NASCTO_FUND)        = i.NU_MES AND
                                                                                    year(T.DT_NASCTO_FUND)        = i.NU_ANO
                                                                                    )
      ) OR year(T.DT_NASCTO_FUND) = i.NU_ANO)