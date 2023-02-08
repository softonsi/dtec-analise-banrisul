select count(*) as total from
(
SELECT distinct CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO 
FROM TB_TRANS_ANLSE t, TB_LISTA_AUXILIAR l, TB_CONTEUDO_LISTA_AUXILIAR i 
WHERE CD_LOTE = 2014091801
  AND L.CD_CLASSE_LISTA_AUXILIAR = 6 /*terrorismo*/
  AND L.CD_TP_LISTA_AUXILIAR = 4 /*cliente*/
  AND l.fl_Apagado = 0
  AND i.dt_inclusao is not null  
  AND L.CD_LISTA_AUXILIAR = I.CD_LISTA_AUXILIAR
  AND i.DS_CONTEUDO = t.CD_DOC_IDENTF_CLIE	 
  AND dateadd(day,i.nu_prazo_validd_dia,i.dt_inclusao) >= getdate()
) s; 
select count(1) as total from tb_detlh_apontamento where cd_regra = 110


