

select count(*) as total from
(
select cd_doc_identf_clie, cd_tp_identf_clie, cd_transacao
from tb_conteudo_lista_auxiliar i,  TB_TRANS_ANLSE T
where i.CD_LISTA_AUXILIAR = 10
  and i.DS_CONTEUDO = T.CD_DOC_IDENTF_CLIE
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 310

