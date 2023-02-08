/* Recebimento de créditos entre contas super, pertencentes a lista restritiva preventiva. */

select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA = 'F'
  AND CD_NATUR_OPER = 1
  AND FL_CS_DESTORIG = 1
  AND CD_LOTE = 2014091801
  AND EXISTS
      (SELECT 1 FROM TB_CONTEUDO_LISTA_AUXILIAR 
	    WHERE CD_LISTA_AUXILIAR = 3
		  AND CD_CTA_DESTORIG = DS_CONTEUDO
	  )  
  AND EXISTS
      (SELECT 1 FROM TB_PERFIL_MES_CALENDARIO
	     WHERE CD_IDENTF_PERFIL = 4
		   AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
		   AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
	  	   AND CD_ANO_MES = CASE WHEN MONTH(T.DT_TRANS) < 10
                                    THEN CAST(YEAR(T.DT_TRANS) AS VARCHAR) + '0' + CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                                    ELSE CAST(YEAR(T.DT_TRANS) AS VARCHAR) +       CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                               END 	  		
		   AND (VL_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VTotalCredito_PF' and cd_regra = 7 and cd_versao_sistema = 3)
		        OR
		        QT_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QCredito_PF' and cd_regra = 7 and cd_versao_sistema = 3)		   
		        )
       )
UNION
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA = 'J'
  AND CD_NATUR_OPER = 1
  AND FL_CS_DESTORIG = 1
  AND CD_LOTE = 2014091801
  AND EXISTS
      (SELECT 1 FROM TB_CONTEUDO_LISTA_AUXILIAR 
	    WHERE CD_LISTA_AUXILIAR = 3
		  AND CD_CTA_DESTORIG = DS_CONTEUDO
	  )  
  AND EXISTS
      (SELECT 1 FROM TB_PERFIL_MES_CALENDARIO
	     WHERE CD_IDENTF_PERFIL = 4
		   AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
		   AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
	  	   AND cd_ano_mes = CASE WHEN MONTH(T.DT_TRANS) < 10
                                    THEN CAST(YEAR(T.DT_TRANS) AS VARCHAR) + '0' + CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                                    ELSE CAST(YEAR(T.DT_TRANS) AS VARCHAR) +       CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                               END 	  		
		   AND (VL_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VTotalCredito_PJ' and cd_regra = 7 and cd_versao_sistema = 3)
		        OR
		        QT_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_QCredito_PJ' and cd_regra = 7 and cd_versao_sistema = 3)		   
		        )
       )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 7
	   
 
