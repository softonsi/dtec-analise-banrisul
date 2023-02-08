select count(*) as total from
(
SELECT CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE, CD_TRANSACAO FROM TB_TRANS_ANLSE t
	WHERE CD_LOTE = 2014091801
		AND exists (
			select 1 from TB_LISTA_AUXILIAR l, TB_CONTEUDO_LISTA_AUXILIAR i 
				where l.cd_tp_lista_auxiliar = 5
					and (i.DS_CONTEUDO = t.CD_DOC_IDENTF_CLIE 
					or i.DS_CONTEUDO = t.CD_DOC_IDENTF_DESTORIG)
					and   i.cd_lista_auxiliar = l.cd_lista_auxiliar
					and   i.dt_inclusao + i.nu_prazo_validd_dia >= sysdate
		)
  			); 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 54