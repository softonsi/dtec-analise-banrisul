/*Cargas e/ou transferencias de outas contas super, abertas em regiões fronteiriças de risco, que totalizem valor igual ou superior parametrizado, no mes calendário fechado.*/


select count(*) as total from
(
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA = 'F'
  AND CD_NATUR_OPER = 1
  AND CD_TP_OPER   IN (26, 9)
  AND FL_CS_DESTORIG = 1
  AND NM_CID_ABERT_CTA IS NOT NULL
  AND SG_UF_ABERT_CTA IS NOT NULL
  AND CD_LOTE = 2014091801
  AND EXISTS
      (SELECT 1 FROM TB_CONTEUDO_LISTA_AUXILIAR 
	    WHERE CD_LISTA_AUXILIAR = 2
		  AND UPPER(LTRIM(RTRIM(T.NM_CID_ABERT_CTA + '-' + T.SG_UF_ABERT_CTA))) = UPPER(LTRIM(RTRIM(DS_CONTEUDO)))
	  )  
  AND EXISTS
      (SELECT 1 FROM TB_PERFIL_MES_CALENDARIO
	     WHERE CD_IDENTF_PERFIL = 2
		   AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
		   AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
		   AND CD_VARIAVEL_TERCEIRA = 1
	  	   and cd_ano_mes = CASE WHEN MONTH(T.DT_TRANS) < 10
                                 THEN CAST(YEAR(T.DT_TRANS) AS VARCHAR) + '0' + CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                                 ELSE CAST(YEAR(T.DT_TRANS) AS VARCHAR) +       CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                            END 	  		
		   AND VL_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VCargaTransf_PF' and cd_regra = 3 and cd_versao_sistema = 3)
       )
UNION
SELECT CD_TRANSACAO, CD_DOC_IDENTF_CLIE, CD_TP_IDENTF_CLIE 
FROM TB_TRANS_ANLSE T
WHERE CD_TP_PESSOA = 'J'
  AND CD_NATUR_OPER = 1
  AND CD_TP_OPER   IN (26, 9)
  AND FL_CS_DESTORIG = 1
  AND NM_CID_ABERT_CTA IS NOT NULL
  AND SG_UF_ABERT_CTA IS NOT NULL
  AND CD_LOTE = 2014091801
  AND EXISTS
      (SELECT 1 FROM TB_CONTEUDO_LISTA_AUXILIAR 
	    WHERE CD_LISTA_AUXILIAR = 2
		  AND UPPER(LTRIM(RTRIM(T.NM_CID_ABERT_CTA + '-' + T.SG_UF_ABERT_CTA))) = UPPER(LTRIM(RTRIM(DS_CONTEUDO)))
	  )  
  AND EXISTS
      (SELECT 1 FROM TB_PERFIL_MES_CALENDARIO
	     WHERE CD_IDENTF_PERFIL = 2
		   AND CD_VARIAVEL_PRIMEIRA = T.CD_DOC_IDENTF_CLIE
		   AND CD_VARIAVEL_SEGUNDA  = T.CD_TP_IDENTF_CLIE
		   AND CD_VARIAVEL_TERCEIRA = 1
	  	   and cd_ano_mes = CASE WHEN MONTH(T.DT_TRANS) < 10
                                 THEN CAST(YEAR(T.DT_TRANS) AS VARCHAR) + '0' + CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                                 ELSE CAST(YEAR(T.DT_TRANS) AS VARCHAR) +       CAST(MONTH(T.DT_TRANS) AS VARCHAR)
                             END 	  		
		   AND VL_TOTAL >= (select vl_param from tb_regra_parametro where nm_campo_param = 'pm_VCargaTransf_PJ' and cd_regra = 3 and cd_versao_sistema = 3)
       )
) s; 
select count(1) as total from tb_detlh_apontamento where cd_lote = 2014091801 and cd_regra = 3




	