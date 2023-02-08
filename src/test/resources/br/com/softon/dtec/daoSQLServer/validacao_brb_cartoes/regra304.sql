select count(*) as total from
(
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao
from tb_conteudo_lista_auxiliar i, tb_lista_auxiliar l, TB_TRANS_ANLSE T
where (i.cd_lista_auxiliar = l.cd_lista_auxiliar and (dateadd(day,i.nu_prazo_validd_dia,i.dt_inclusao) >= getdate() or i.nu_prazo_validd_dia is null)) AND 
      T.CD_LOTE = 2014091801 AND T.CD_SUBLOTE = 1 AND I.DS_CONTEUDO = T.NM_CLIE AND l.cd_lista_auxiliar = 202 AND
      ((i.NU_DIA IS NOT NULL AND i.NU_MES IS NOT NULL AND 
       i.NU_ANO IS NOT NULL AND (day(T.DT_NASCTO_FUND) = i.NU_DIA AND month(T.DT_NASCTO_FUND)= i.NU_MES AND year(T.DT_NASCTO_FUND)= i.NU_ANO
       )) OR year(T.DT_NASCTO_FUND) = i.NU_ANO)
	
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 304


