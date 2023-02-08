/* Somatório de todas as operações a débito, em contas abertas em região fronteiriza de risco, no valor igual ou superior ao parametrizado, no mês calendário fechado. */

select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA = 'F'
  AND CD_NATUR_OPER = 2
  AND NM_CID_ABERT_CTA IS NOT NULL
  AND SG_UF_ABERT_CTA IS NOT NULL
  AND CD_LOTE = 2014091801
  AND EXISTS
      (SELECT 1 FROM TB_CONTEUDO_LISTA_AUXILIAR 
	    WHERE CD_LISTA_AUXILIAR = 2
		  AND UPPER(LTRIM(RTRIM(NM_CID_ABERT_CTA + '-' + SG_UF_ABERT_CTA))) = UPPER(LTRIM(RTRIM(DS_CONTEUDO)))
	  )  
  AND EXISTS
      (SELECT 1 FROM TB_PERFIL_MES_CALENDARIO
	     WHERE CD_IDENTF_PERFIL = 2
		   AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
		   AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
		   AND CD_VARIAVEL_TERCEIRA = 2
	  	   and cd_ano_mes = CASE WHEN MONTH(T.DT_TRANS) < 10
                                 THEN CAST(YEAR(T.DT_TRANS) AS VARCHAR) + '0' + CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                                 ELSE CAST(YEAR(T.DT_TRANS) AS VARCHAR) +       CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                            END 	  		
		   AND VL_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VDebito_PF' and cd_regra = 4 and cd_versao_sistema = 3)
       )
UNION
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA = 'J'
  AND CD_NATUR_OPER = 2
  AND NM_CID_ABERT_CTA IS NOT NULL
  AND SG_UF_ABERT_CTA IS NOT NULL
  AND CD_LOTE = 2014091801
  AND EXISTS
      (SELECT 1 FROM TB_CONTEUDO_LISTA_AUXILIAR 
	    WHERE CD_LISTA_AUXILIAR = 2
		  AND UPPER(LTRIM(RTRIM(NM_CID_ABERT_CTA + '-' + SG_UF_ABERT_CTA))) = UPPER(LTRIM(RTRIM(DS_CONTEUDO)))
	  )  
  AND EXISTS
      (SELECT 1 FROM TB_PERFIL_MES_CALENDARIO
	     WHERE CD_IDENTF_PERFIL = 2
		   AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
		   AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
		   AND CD_VARIAVEL_TERCEIRA = 2
	  	   and cd_ano_mes = CASE WHEN MONTH(T.DT_TRANS) < 10
                                    THEN CAST(YEAR(T.DT_TRANS) AS VARCHAR) + '0' + CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                                    ELSE CAST(YEAR(T.DT_TRANS) AS VARCHAR) +       CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                               END 	  		
		   AND VL_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VDebito_PJ' and cd_regra = 4 and cd_versao_sistema = 3)
       )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 4

	   
	   



