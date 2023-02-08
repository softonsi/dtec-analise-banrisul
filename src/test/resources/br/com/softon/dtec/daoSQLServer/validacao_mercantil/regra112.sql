select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO
FROM TB_TRANS_ANLSE t
WHERE CD_LOTE = 2014091801 
  AND CD_DOC_IDENTF_CLIE IS NOT NULL
  AND NM_CLIE IS NOT NULL  
  
  AND NOT EXISTS (
	select 1 from TB_LISTA_AUXILIAR l, TB_CONTEUDO_LISTA_AUXILIAR i 
	WHERE i.CD_LISTA_AUXILIAR = 5 /*lista branca*/
      AND l.fl_Apagado = 0
      AND i.dt_inclusao is not null  	  
	  AND L.CD_LISTA_AUXILIAR = I.CD_LISTA_AUXILIAR
	  AND i.DS_CONTEUDO = t.CD_DOC_IDENTF_CLIE	 
	  AND dateadd(day,i.nu_prazo_validd_dia,i.dt_inclusao) >= getdate()
  )
  
  AND NOT EXISTS (
	select 1 from TB_LISTA_AUXILIAR l, TB_CONTEUDO_LISTA_AUXILIAR i 
	WHERE i.CD_LISTA_AUXILIAR = 3 /*lista AML*/
      AND l.fl_Apagado = 0
      AND i.dt_inclusao is not null  	  
	  AND L.CD_LISTA_AUXILIAR = I.CD_LISTA_AUXILIAR
	  AND i.DS_CONTEUDO = t.NM_CLIE	 
	  AND dateadd(day,i.nu_prazo_validd_dia,i.dt_inclusao) >= getdate()
  )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 112